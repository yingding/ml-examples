package controllers;

import akka.actor.ActorSystem;
import akka.japi.Pair;
import com.fasterxml.jackson.databind.JsonNode;
import com.typesafe.config.Config;
import controllers.idl.SparkInfo;
import play.Logger;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import scala.concurrent.duration.FiniteDuration;
import services.AppConfigService;
import tasks.MLTasks;
import utilities.ControllerHelper;
import utilities.TimeUtil;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static play.mvc.Results.ok;

@Singleton
public class MLController {
    private static final String TAG = MLController.class.getSimpleName();
    private ActorSystem actorSystem;
    private MLTasks mlTasks;
    private final Config appConfig;

    @Inject
    public MLController(AppConfigService appConfigService, ActorSystem actorSystem, MLTasks mlTasks) {
        this.actorSystem = actorSystem;
        this.mlTasks = mlTasks;
        this.appConfig = appConfigService.getConfig();
    }

    public Result getMLInfos(Http.Request request) {
        /* check condition */
        Pair<Result, JsonNode> pair = ControllerHelper.checkRequestCondition(appConfig, request);
        if (pair.first() != null) {
            return pair.first();
        }
        // JsonNode bodyJson = pair.second();

        Logger.info("{} ; {}: getMLInfos called!" , TimeUtil.getDateTimeStr(new Date()),TAG);

        /* execution block */
        // https://www.playframework.com/documentation/2.8.x/JavaJsonActions
        SparkInfo sparkInfo = mlTasks.getSparkInfo();
        // mlTasks.execSparkTasks();
        runAsyncMLTasks();
        return ok(Json.toJson(sparkInfo));
    }

    private void runAsyncMLTasks() {
        // The task shall be run in 1 seconds after the time this task is scheduled
        actorSystem.scheduler().scheduleOnce(FiniteDuration.create(1, TimeUnit.SECONDS), () -> {
            try {
                // in scheduled task, get a instance of task from injector
                // MoodTasks moodTasks = Play.current().injector().instanceOf(MoodTasks.class);

                // execute count task
                mlTasks.execSparkTasks();
            } catch (Exception e) {
                Logger.error("Error in {}: {}", "mlTasks", e.getMessage());
            }
        }, actorSystem.dispatcher());
    }

}
