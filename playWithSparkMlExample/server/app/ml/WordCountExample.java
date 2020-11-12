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
                .master("spark://localhost:7077") // spark-master will be resolved by DNS
                //.master("spark://spark-master:7077")
                .config("spark.driver.memory", "1G") // setting driver
                .config("spark.driver.cores", "1")
                .config("spark.driver.port", "7078") // tell how worker can call back to driver
                /* set spark.driver.host, so that the spark container can call the driver host
                 * since spark container runs in a bridge network call the gate way IP to connect to driver port
                 * Find out gateway: "docker network inspect deployment_spark-net",
                 * where "deployment_spark-net" is the user-defined bridge network name
                 */
                .config("spark.driver.host", "172.20.0.1") // gateway of the custom docker bridge network of spark cluster
                /* use spark.driver.bindAddress to set SPARK_LOCAL_IP
                 * Refernce:
                 * https://stackoverflow.com/questions/38429333/spark-submit-service-driver-could-not-bind-on-port-error/43235659#43235659
                 */
                .config("spark.driver.bindAddress", "127.0.0.1") // use en10 ip // 127.0.0.1 will resolve to my local interface en10
                // .config("spark.blockManager.port", "10025") ?
                .config("spark.driver.blockManager.port", "10026") // blockManager will be used to fetch the data from driver
                .config("spark.executor.memory", "2G") // setting executor
                .config("spark.executor.cores", "2")
                .config("spark.num.executors", "2")
                .config("spark.default.parallelism", "2")
                // .config("spark.cores.max", "6") // max available spark core
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
        Dataset<Row> myRange =  spark.range(10000).toDF("number");
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
