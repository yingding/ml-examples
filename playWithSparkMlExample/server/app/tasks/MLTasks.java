package tasks;

import com.typesafe.config.Config;
import configs.SparkConfig;
import controllers.idl.SparkInfo;
import play.Logger;
import services.AppConfigService;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class MLTasks {
    private static final String TAG = MLTasks.class.getSimpleName();
    private SparkConfig sparkConfig;

    @Inject
    public MLTasks(SparkConfig sparkConfig) {
        this.sparkConfig = sparkConfig;
    }

    public SparkInfo getSparkInfo() {
        return new SparkInfo(sparkConfig.isActivated());
    }

    public void execSpark() {
        Logger.info("spark task is {}active!", sparkConfig.isActivated() ? "": "NOT ");
    }




}
