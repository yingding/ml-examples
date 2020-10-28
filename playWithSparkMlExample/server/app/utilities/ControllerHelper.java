package utilities;

import akka.japi.Pair;
import com.fasterxml.jackson.databind.JsonNode;
import com.typesafe.config.Config;
import controllers.keys.ResultKeys;
import play.mvc.Http;
import play.mvc.Result;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.unauthorized;
import static controllers.keys.InterfaceKeys.AuthorizationKeys.Seedkey;

/**
 * Collection of useful method for a controller
 */
public class ControllerHelper {
    public static JsonNode getJsonFromRequestBody(Http.Request request) {
        return request.body().asJson();
    }

    /*
     * this method checks the precondition for further access.
     * if it returns null, can be further executed,
     * otherwise the Result shall be returned
     */
    public static Pair<Result, JsonNode> checkRequestCondition(Config appConfig, Http.Request request) {
        /* check body condition , can be move to a filter combinded with action for all incoming request */
        JsonNode json = getJsonFromRequestBody(request);
        // early stop
        if (json == null || json.isEmpty()) {
            return new Pair<>(badRequest(ResultKeys.BadRequestKeys.ExpectingJsonDataKey), null);
        }
        String requestTcpSeed = json.findPath(Seedkey).asText();
        if (!Authentication.isAuthorizedSeed(appConfig, requestTcpSeed)) {
            return new Pair<>(unauthorized(ResultKeys.UnauthorizedKeys.UnauthorizedSeedKey), null);
        }
        return new Pair<>(null, json);
    }

}
