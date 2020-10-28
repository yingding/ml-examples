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
import services.AppConfigService;
import tasks.MLTasks;
import utilities.ControllerHelper;
import utilities.TimeUtil;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;

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
        return ok(Json.toJson(sparkInfo));
    }

}
