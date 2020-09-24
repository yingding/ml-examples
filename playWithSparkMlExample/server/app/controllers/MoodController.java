package controllers;

import Models.MoodEntry;
import Models.MoodObject;
import Utilities.Authentication;
import Utilities.TimeUtil;
import akka.actor.ActorSystem;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.typesafe.config.Config;
import org.jongo.MongoCursor;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import scala.concurrent.duration.FiniteDuration;
import services.AppConfigService;
import services.DBService;
import tasks.MoodTasks;

import javax.inject.Singleton;
import javax.inject.Inject;

import java.util.Date;
import java.util.concurrent.TimeUnit;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;


/**
 * Created by yingding on 07.05.17.
 *
 * Dependency Injection with Guice in Play 2.6.x and Play 2.7.x
 *
 * https://github.com/alexanderjarvis/play-jongo/issues/58
 * https://www.playframework.com/documentation/2.7.x/JavaDependencyInjection
 * https://github.com/playframework/play-samples/blob/2.7.x/play-java-websocket-example/app/Module.java
 *
 */
@Singleton
public class MoodController extends Controller {
    // https://www.playframework.com/documentation/2.7.x/JavaLogging
    // use a class logger
    // private final Logger logger = LoggerFactory.getLogger(MoodController.class.getSimpleName());
    // private final Logger logger = LoggerFactory.getLogger(Application.class);

    private final Config appConf;
    private ActorSystem actorSystem;
    private DBService dbService;
    private MoodTasks moodTasks;

    @Inject
    public MoodController(AppConfigService appConfService, ActorSystem actorSystem, DBService dbService, MoodTasks moodTasks) {
        this.appConf = appConfService.getConfig();
        this.actorSystem = actorSystem;
        this.dbService = dbService;
        this.moodTasks = moodTasks;
    }

    // @BodyParser.Of(BodyParser.Json.class)
    public Result saveMoodInput(Http.Request request) {
        boolean succeed = true;
        boolean canBeSaved;
        // parse the body
        JsonNode json = request.body().asJson();
        if (json == null) {
            return badRequest("Expecting Json data");
        }
        String requestTcpSeed = json.findPath("seed").asText();
        if (Authentication.isAuthorizedSeed(appConf, requestTcpSeed)) {
            JsonNode moods = json.findPath("moods");
            for (JsonNode moodNode : moods) {
                MoodEntry mood = new MoodEntry(
                        moodNode.findPath("timestamp").asLong(),
                        moodNode.findPath("mood").asText()
                );
                canBeSaved = this.dbService.saveMood(mood);
                if (canBeSaved) {
                    Logger.info("Inserted " + mood.toString());
                } else {
                    Logger.info("Failed to insert " + mood.toString());
                    succeed = false;
                    break; // break out the for loop, must not always be breakded out.
                }
            }
        } else {
            return unauthorized("Unauthorized seed");
        }
        if (succeed) {
            // run a async task
            runAsyncTask();
            return ok("moods saved successfully");
        } else {
            return badRequest("moods has inapproperate structure, can not be all saved");
        }
    }

    /**
     * http://doc.akka.io/docs/akka/current/java/scheduler.html
     *
     * Handline JSON with Play.lib.Json
     * https://www.playframework.com/documentation/2.8.x/JavaJsonActions#:~:text=In%20Java%2C%20Play%20uses%20the,Json%20API.
     * @return http result
     */
    public Result getAllMoods(Http.Request request) {
        Logger.info("Get all Moods on " + TimeUtil.getDateStr(new Date()));
        // parse the body
        JsonNode json = request.body().asJson();
        if (json == null) {
            return badRequest("Expecting Json data");
        }
        String requestTcpSeed = json.findPath("seed").asText();
        if (Authentication.isAuthorizedSeed(appConf, requestTcpSeed)) {
            // fetch data from db and get the db cursor
            MongoCursor<MoodObject> cursor = this.dbService.findAllMoods();

            ObjectNode result = Json.newObject();
            // some how it is not possible to give a iterator or DB cursor to Json.toJson, which will result in a infinite recursion and only works with ArrayList now.
            result.set("moods", cursorMapper(cursor));
            return ok(result);
        } else {
            return unauthorized("Unauthorized seed");
        }
    }

    /**
     * this method wrap the cursor to ArrayNode
     * @param cursor MongoCusor
     * @return ArrayNode
     */
    private ArrayNode cursorMapper(MongoCursor cursor) {
        ArrayNode arrayNode = Json.newArray();
        while(cursor.hasNext()) {
            arrayNode.add(Json.toJson(cursor.next()));
        }
        return arrayNode;
    }

    private void runAsyncTask() {
        // The task shall be run in 1 seconds after the time this task is scheduled
        actorSystem.scheduler().scheduleOnce(FiniteDuration.create(1, TimeUnit.SECONDS), () -> {
            try {
                // in scheduled task, get a instance of task from injector
                // MoodTasks moodTasks = Play.current().injector().instanceOf(MoodTasks.class);

                // execute count task
                this.moodTasks.countAll();
            } catch (Exception e) {
                Logger.error("Error in {}: {}", "MoodTask", e.getMessage());
            }
        }, actorSystem.dispatcher());
    }
}
