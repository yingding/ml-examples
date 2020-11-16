package ml;

import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.ReadConfig;
import com.mongodb.spark.rdd.api.java.JavaMongoRDD;
import configs.SparkMongoConfig;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.bson.Document;
import play.Logger;

import java.util.HashMap;
import java.util.Map;

public class WordCountExample {
    public static final String TAG = WordCountExample.class.getSimpleName();
    public static final StringBuilder TAG_BUILDER = new StringBuilder(TAG);
    private SparkMongoConfig sparkMongoConfig;

    public WordCountExample(SparkMongoConfig sparkMongoConfig) {
        this.sparkMongoConfig = sparkMongoConfig;
    }

    // TODO: convert this static method to a Singleton?
    public static SparkConf getSparkConf() {
        /* Spark Shell config example
         * Reference: https://spark.apache.org/docs/latest/
         * spark-shell --conf spark.executor.memory=1G --conf spark.executor.cores=1 --master spark://spark-master:7077
         *
         * Reference: https://stackoverflow.com/questions/47177669/spark-session-configured-is-not-utilizing-the-complete-resources-please-suggest
         */
        SparkConf sparkConf = new SparkConf();
        sparkConf.set("spark.driver.memory", "1G"); // setting driver
        sparkConf.set("spark.driver.cores", "1");
        sparkConf.set("spark.driver.port", "7078"); // tell how worker can call back to driver
        /* set spark.driver.host, so that the spark container can call the driver host
         * since spark container runs in a bridge network call the gate way IP to connect to driver port
         * Find out gateway: "docker network inspect docker_spark-net",
         * where "deployment_spark-net" is the user-defined bridge network name
         *
         * Reference: https://stackoverflow.com/questions/48546124/what-is-linux-equivalent-of-host-docker-internal
         * On Windows/Mac use: sparkConf.set("spark.driver.host", "host.docker.internal");
         * On Linux use gateway ip: e.g. sparkConf.set("spark.driver.host", "172.18.0.1");
         * starting from 20.04 Linux need to add a --add-host flag to allow "host.docker.internal"
         */
        // for linux
        // sparkConf.set("spark.driver.host", "172.18.0.1"); // gateway of the custom docker bridge network of spark cluster
        // for mac/windows the gateway
        sparkConf.set("spark.driver.host", "host.docker.internal");
        // sparkConf.set("spark.blockManager.port", "10025") ?
        /* use spark.driver.bindAddress to set SPARK_LOCAL_IP
         * Refernce:
         * https://stackoverflow.com/questions/38429333/spark-submit-service-driver-could-not-bind-on-port-error/43235659#43235659
         */
        // sparkConf.set("spark.driver.bindAddress", "0.0.0.0"); // use en10 ip // 127.0.0.1 will resolve to my local interface en10
        sparkConf.set("spark.driver.bindAddress", "127.0.0.1"); // use en10 ip // 127.0.0.1 will resolve to my local interface en10
        sparkConf.set("spark.driver.blockManager.port", "10026"); // blockManager will be used to fetch the data from driver
        sparkConf.set("spark.executor.memory", "1G"); // setting executor
        sparkConf.set("spark.executor.cores", "1");
        sparkConf.set("spark.num.executors", "2");
        sparkConf.set("spark.default.parallelism", "2");
        sparkConf.set("spark.submit.deployMode","client"); // submitter launches the driver outside of the cluster
        // sparkConf.set("spark.submit.deployMode","cluster"); // use deploy mode cluster
        // client https://spark.apache.org/docs/latest/cluster-overview.html

        return sparkConf;
    }

    public SparkConf extendMongoConfig(SparkConf sparkConf) {
        String dbName = "mydb";
        String inputCollection = "moods";
        String outputCollection = "moods";
        /* Config: https://spark.apache.org/docs/latest/configuration.html
         * Advanced Dependency Mgmt: https://spark.apache.org/docs/latest/submitting-applications.html#advanced-dependency-management
         */
        /* sparkConf.set("spark.jars.repositories", "");
         * sparkConf.set("spark.jars.packages", sparkMongoConfig.getJarsPackages());
         * Deprecated:
         * https://stackoverflow.com/questions/62106554/why-does-spark-submit-ignore-the-package-that-i-include-as-part-of-the-configura
         * https://stackoverflow.com/questions/35762459/add-jar-to-standalone-pyspark
         */
        // sparkConf.set("spark.jars.repositories", "https://mvnrepository.com/");
        // sparkConf.set("spark.jars.packages", "org.mongodb.spark:mongo-spark-connector_2.12:3.0.0");
        sparkConf.set("spark.executor.extraClassPath","/spark/extra/target/");
        sparkConf.set("spark.mongodb.input.uri", genMongoURI(dbName, inputCollection));
        sparkConf.set("spark.mongodb.output.uri", genMongoURI(dbName, outputCollection));
        sparkConf.set("spark.mongodb.input.partitionerOptions", "MongoSamplePartitioner");
        sparkConf.set("spark.mongodb.input.partitionerOptions.partitionKey", "_id.timestamp");
        return sparkConf;
    }

    public SparkConf extendMongoConfig2(SparkConf sparkConf) {
        sparkConf.set("spark.mongodb.input.uri", genMongoURI("mydb","myCollection"));
        sparkConf.set("spark.mongodb.output.uri", genMongoURI("mydb","myCollection"));
        return sparkConf;
    }

    private String genMongoURI(String dbName, String collection) {
        if (sparkMongoConfig != null) {
            return  String.format("mongodb://%s:%s@%s:%s/%s.%s?authSource=%s",
                    sparkMongoConfig.getUser(), sparkMongoConfig.getPw(),
                    sparkMongoConfig.getHost(), sparkMongoConfig.getPort(),
                    dbName, collection, dbName);
        } else {
            return "";
        }
    }

    public SparkSession getSparkSession(StringBuilder sb) {
        // return getSparkSessionBuilder(TAG_BUILDER).getOrCreate();
        // return extendMongoConfig(getSparkSessionBuilder(TAG_BUILDER)).getOrCreate();
        return SparkSession
                .builder()
                .appName(sb.toString()) // name of the app
                .master("spark://localhost:7077") // spark-master will be resolved by DNS
                //.master("spark://spark-master:7077")
                .config(extendMongoConfig(getSparkConf()))
                //.config(extendMongoConfig2(getSparkConf()))
                // .config(getSparkConf())
                .getOrCreate();
    }

    /**
     * Example of load mongodb data from spark worker
     * Reference:
     * https://docs.mongodb.com/spark-connector/master/java-api
     * @return no. of moods in MongoDB collection
     */
    public long getMoodsFromSpark() {
        return 0;
    }

    public long getSimpleNumberCount() {
        /* Open SparkSession and SparkContext */
        SparkSession spark = getSparkSession(TAG_BUILDER);

        // make a DataFrame with column name number
        Dataset<Row> myRange =  spark.range(10000).toDF("number");
        Dataset<Row> dividedBy2 = myRange.where("number % 2 = 0");
        long myCount = dividedBy2.count();

        /* Close SparkSession and SparkContext */
        // SparkSession RPC
        spark.stop();

        return myCount;

    }

    public long getCount2() {
        /* Open SparkSession and SparkContext */
        SparkSession spark = getSparkSession(TAG_BUILDER);

        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
        JavaMongoRDD<Document> rdd = MongoSpark.load(jsc);
        long count = -1;
        count = rdd.count();

        // close JavaSparkContext
        jsc.close();
        // SparkSession RPC
        spark.stop();
        return count;
    }

    public long getCount() {
        /* Open SparkSession and SparkContext */
        SparkSession spark = getSparkSession(TAG_BUILDER);
        // Create a JavaSparkContext from a SparkSession's SparkContext object

        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

        // Create a custom ReadConfgi
        Map<String, String> readOverrides = new HashMap<>();
        readOverrides.put("collection", "moods");
        readOverrides.put("database", "mydb");
        readOverrides.put("partitionerOptions", "MongoSamplePartitioner");
        readOverrides.put("partitionerOptions.partitionKey", "_id.timestamp");
        // readOverrides.put("partitionerOptions.partitionKey", "_id.timestamp");
        // https://docs.mongodb.com/spark-connector/master/configuration#Input-configuration
        ReadConfig readConfig = ReadConfig.create(jsc).withOptions(readOverrides);

        /* Code Block */
        // get RDD from mongo collection
        // JavaMongoRDD<Document> rdd = MongoSpark.load(jsc, readConfig);
        long myCount = 0;
        try {
            // https://stackoverflow.com/questions/59320324/classnotfoundexception-com-mongodb-spark-rdd-partitioner-mongopartition
            JavaMongoRDD<Document> rdd = MongoSpark.load(jsc, readConfig);
            if (rdd != null && !rdd.isEmpty()) {
                myCount = rdd.count();
            }
            /* Close SparkSession and SparkContext */
        } catch (NoSuchFieldError error) {
            error.printStackTrace();
            Logger.error("WordCountExample {}", error.getMessage());
        }
        // close JavaSparkContext
        jsc.close();

        // SparkSession RPC
        spark.stop();

        return myCount;
    }
}
