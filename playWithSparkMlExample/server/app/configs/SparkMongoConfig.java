package configs;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import play.Logger;
import services.AppConfigService;

import javax.inject.Inject;

/**
 * Config info for spark mongo connector
 */
public class SparkMongoConfig extends AbstractBaseConfig {
    private static final String TAG = SparkMongoConfig.class.getSimpleName();
    private String host;
    private String port;
    private String user;
    private String pw;
    // private String jarsPackages;

    @Inject
    public SparkMongoConfig(AppConfigService appConfigService) {
        init(appConfigService.getConfig());
    }

    @Override
    protected void init(Config config) {
        if (config != null) {
            try {
                host = config.getString(ConfigKeys.SparkMongoConfigKeys.mongoHost);
                port = config.getString(ConfigKeys.SparkMongoConfigKeys.mongoPort);
                user = config.getString(ConfigKeys.SparkMongoConfigKeys.mongoUser);
                pw = config.getString(ConfigKeys.SparkMongoConfigKeys.mongoPW);
                // jarsPackages = config.getString(ConfigKeys.SparkMongoConfigKeys.jarsPackages);
            } catch (ConfigException.Missing e) {
                Logger.error("{} config key doesn't exist! \n{}", TAG, e.getMessage() == null ? "": e.getMessage());
                initDefault();
            } catch (ConfigException.WrongType e) {
                Logger.error("{} config key has wrong type! \n{}", TAG, e.getMessage() == null ? "": e.getMessage());
                initDefault();
            }
        } else {
            initDefault();
        }
    }

    @Override
    protected void initDefault() {
        host = "127.0.0.1";
        port = "27017";
        user = "";
        pw = "";
        // jarsPackages = "";
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPw() {
        return pw;
    }

//    public String getJarsPackages() {
//        return jarsPackages;
//    }
}
