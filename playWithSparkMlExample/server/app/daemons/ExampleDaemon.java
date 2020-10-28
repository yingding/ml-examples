package daemons;

import utilities.TimeUtil;
import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import play.Logger;
import play.inject.ApplicationLifecycle;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;

@Singleton
public class ExampleDaemon extends TemplateDaemon {
    String getKey() {
        return ExampleDaemon.class.getCanonicalName();
    }
    String getDelayIntervalKey() {
        return "exampleDaemon.delay";
    }
    String getExecutionIntervalKey() {
        return "exampleDaemon.executionInterval";
    }
    String getIsActivatedKey() {
        return "exampleDaemon.isActivated";
    }

    public ExampleDaemon() {
        super();
    }

    @Inject
    public ExampleDaemon(ActorSystem actor, ApplicationLifecycle lifecycle, final Config appConf) {
        super.init(actor, lifecycle, appConf);
        setup(exampleDaemon);
    }

    final Runnable exampleDaemon = () -> {
        try {
            /* getTime() return a utc timestamp
             * final long now = new Date().getTime();
             */
            final String now = TimeUtil.getDateTimeStr(new Date());
            Logger.info("{} executed at {}", tag, now);
        } catch (Exception e) {
            Logger.error("Error in {} {}", tag, e.getMessage());
        }
    };
}
