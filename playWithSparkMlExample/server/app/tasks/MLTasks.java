package tasks;

import configs.CustomSparkConfig;
import configs.SparkMongoConfig;
import controllers.idl.SparkInfo;
import ml.WordCountExample;
import play.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class MLTasks {
    private static final String TAG = MLTasks.class.getSimpleName();
    private CustomSparkConfig customSparkConfig;
    private SparkMongoConfig sparkMongoConfig;

    @Inject
    public MLTasks(CustomSparkConfig customSparkConfig, SparkMongoConfig sparkMongoConfig) {
        this.customSparkConfig = customSparkConfig;
        this.sparkMongoConfig = sparkMongoConfig;
    }

    public SparkInfo getSparkInfo() {
        return new SparkInfo(customSparkConfig.isActivated());
    }

    public void execSparkTasks() {
        Logger.info("spark task is {}active!", customSparkConfig.isActivated() ? "": "NOT ");
        if (customSparkConfig.isActivated()) {
            // execute word counting example
            execWordCountExample();
        }
    }

    private void execWordCountExample() {
        WordCountExample wordCountExample = new WordCountExample(sparkMongoConfig);
        Logger.info("Begin counting words...");
        // long count = wordCountExample.getCount2();
        long count = wordCountExample.getCount();
        // long count = wordCountExample.getSimpleNumberCount();
        Logger.info("count: {}", count);
    }

}
