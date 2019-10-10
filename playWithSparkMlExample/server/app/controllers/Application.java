package controllers;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 */
@Singleton
public class Application extends Controller {

//    public Result index() {
//        return redirect("/");
//    }

    public Result index(String data) {
        // Logger.info(data);
        return redirect("/");
    }

//
//    public Result show(String data) {
//        Logger.info(data);
//        return ok("Your data is: " + data).as("text/html");
//        // return redirect("/");
//        // return redirect(controllers.routes.Assets.at(path="/public", file="index.html")).as("text/html");
//    }
}
