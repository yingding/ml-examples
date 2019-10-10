package daemons;

import akka.actor.ActorSystem;
import akka.actor.Cancellable;
import com.typesafe.config.Config;
import play.Logger;
import play.inject.ApplicationLifecycle;
import scala.concurrent.duration.FiniteDuration;

import javax.inject.Inject;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Yingding Wang on 07.05.17
 */

public abstract class TemplateDaemon {
    protected Cancellable handle;
    protected String tag;
    protected long delayInterval;
    protected long executionIntervalInSec;
    protected boolean isActivated;

    /* methods must be implemented */
    abstract String getKey();
    abstract String getDelayIntervalKey();
    abstract String getExecutionIntervalKey();
    abstract String getIsActivatedKey();

    protected ActorSystem actorSystem;

/*    protected TemplateDaemon() {
        throw new UnsupportedOperationException("Default Constructor not allowed in " + this.getClass().getCanonicalName());
    }
    */
    public TemplateDaemon() {

    }

    //</editor-fold>
    public TemplateDaemon(ActorSystem actor, ApplicationLifecycle lifecycle, final Config appConf) {
        //TODOs, codes to extract your config
        init(actor, lifecycle, appConf);
        setup(templateDaemon);
    }

    @Inject
    protected void init(ActorSystem actor, ApplicationLifecycle lifecycle, final Config appConf) {
        actorSystem = actor;
        tag = getKey();
        try {
            delayInterval = appConf.getLong(getDelayIntervalKey());
            executionIntervalInSec = appConf.getLong(getExecutionIntervalKey());
            isActivated = appConf.getBoolean(getIsActivatedKey());
        } catch (Exception e) {
            // set the defaults
            delayInterval = 60;
            executionIntervalInSec = 600; // every ten minutes
            isActivated = false;
            Logger.error("Config Data of {} can not be loaded /n {}", tag, e.getMessage());
        }

        lifecycle.addStopHook(() -> {
            shutdown();
            return CompletableFuture.completedFuture(null);
        });
    }

    /**
     * @param daemon
     */
    public void setup(Runnable daemon) {
        if (isActivated) {
            final FiniteDuration delay = FiniteDuration.create(delayInterval, TimeUnit.SECONDS);
            final FiniteDuration freq = FiniteDuration.create(executionIntervalInSec, TimeUnit.SECONDS);
            Logger.info("{} is executed every {} seconds", tag, executionIntervalInSec);

            handle = actorSystem.scheduler().schedule(delay, freq, daemon, actorSystem.dispatcher());
        } else {
            Logger.info("{} is deactivated", tag);
        }
    }

    final Runnable templateDaemon = () -> {
        try {
            final long now = new Date().getTime();
            Logger.info("{} executed at {}", tag, now);
        } catch (Exception e) {
            Logger.error("Error in {} {}", tag, e.getMessage());
        }
    };

    public void shutdown() {
        if (handle != null && !handle.isCancelled()) {
            handle.cancel();
            handle = null;
            Logger.info("{} is shut down.", tag);
        }
    }
}
