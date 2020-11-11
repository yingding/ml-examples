package ml;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class WordCountExample {
    public static final String TAG = WordCountExample.class.getSimpleName();
    public static final StringBuilder TAG_BUILDER = new StringBuilder(TAG);

    // TODO: convert this static method to a Singleton?
    public static SparkSession getSparkSession(StringBuilder sb) {
        /* Spark Shell config example
         * Reference: https://spark.apache.org/docs/latest/
         * spark-shell --conf spark.executor.memory=1G --conf spark.executor.cores=1 --master spark://spark-master:7077
         *
         * Reference: https://stackoverflow.com/questions/47177669/spark-session-configured-is-not-utilizing-the-complete-resources-please-suggest
         */
        return SparkSession
                .builder()
                .appName(sb.toString()) // name of the app
                .config("spark.driver.memory", "1G")
                .config("spark.driver.cores", "1")
                .config("spark.executor.memory", "2G")
                .config("spark.executor.cores", "2")
                .config("spark.num.executors", "2")
                .config("spark.default.parallelism", "2")
                .master("spark://localhost:7077") // spark-master will be resolved by DNS ""spark://spark-master:7077""
                .getOrCreate();
    }

    public long getCount() {
        /* Open SparkSession and SparkContext */
        SparkSession spark = getSparkSession(TAG_BUILDER);
        // Create a JavaSparkContext from a SparkSession's SparkContext object
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

        /* Code Block */
        // JavaRDD<String> textFile =

        // make a DataFrame with column name number
        Dataset<Row> myRange =  spark.range(100).toDF("number");
        Dataset<Row> dividedBy2 = myRange.where("number % 2 = 0");
        long myCount = dividedBy2.count();

        /* Close SparkSession and SparkContext */
        // close JavaSparkContext
        jsc.close();
        // SparkSession RPC
        spark.stop();

        return myCount;
    }
}
