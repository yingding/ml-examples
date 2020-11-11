package ml;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

public class WordCountExample {
    public static final String TAG = WordCountExample.class.getSimpleName();

    // Singleton ?
    public static SparkSession getSparkSession() {
        /* Spark Shell config example
         * Reference: https://spark.apache.org/docs/latest/
         * spark-shell --conf spark.executor.memory=1G --conf spark.executor.cores=1 --master spark://spark-master:7077
         */
        return SparkSession
                .builder()
                .appName(TAG) // name of the app
                .config("spark.executor.memory", "1G")
                .config("spark.executor.cores", "1")
                .master("spark://spark-master:7077")
                .getOrCreate();
    }

    public void execute() {
        /* Open SparkSession and SparkContext */
        SparkSession spark = getSparkSession();
        // Create a JavaSparkContext from a SparkSession's SparkContext object
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

        // JavaRDD<String> textFile =

        /* Close SparkSession and SparkContext */
        // close JavaSparkContext
        jsc.close();
        // SparkSession RPC
        spark.stop();
    }
}
