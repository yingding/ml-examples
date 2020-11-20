package configs;

// package private keys for config package
final class ConfigKeys {
    static class SparkConfigKeys {
        static final String isActivatedKey = "spark.isActivated";
    }
    static class SparkMongoConfigKeys {
        static final String mongoHost = "spark.mongoHost";
        static final String mongoPort = "spark.mongoPort";
        static final String mongoUser = "spark.mongoUser";
        static final String mongoPW = "spark.mongoPW";
        static final String jarsPackages = "spark.jarsPackages";
    }
    static class SparkClusterConfigKeys {
        static final String sparkDriverPort= "spark.driver.port";
        static final String sparkDriverHost= "spark.driver.host";
        static final String sparkDriverBindAddress = "spark.driver.bindAddress";
        static final String sparkDriverBlockManagerPort= "spark.driver.blockManager.port";
    }
}
