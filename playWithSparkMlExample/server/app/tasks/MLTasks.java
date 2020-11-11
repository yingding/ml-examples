package tasks;

import configs.SparkConfig;
import controllers.idl.SparkInfo;
import ml.WordCountExample;
import play.Logger;

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

    public void execSparkTasks() {
        Logger.info("spark task is {}active!", sparkConfig.isActivated() ? "": "NOT ");
        if (sparkConfig.isActivated()) {
            // execute word counting example
            execWordCountExample();
        }
    }

    private void execWordCountExample() {
        WordCountExample wordCountExample = new WordCountExample();
        Logger.info("Begin counting words...");
        long count = wordCountExample.getCount();
        Logger.info("count: ", count);
    }


}
