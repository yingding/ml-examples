package configs;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import play.Logger;
import services.AppConfigService;

import javax.inject.Inject;

/* Config Object class for spark cluster driver setting
 * loaded from this App's config file
 */
public class SparkDriverConfig extends AbstractBaseConfig {
    private static final String TAG = SparkDriverConfig.class.getSimpleName();
    private String driverPort;
    private String driverHost;
    private String driverBindAddress;
    private String driverBlockManagerPort;

    @Inject
    public SparkDriverConfig(AppConfigService appConfigService) {
        init(appConfigService.getConfig());
    }

    @Override
    protected void init(Config config) {
        if (config != null) {
            try {
                driverHost = config.getString(ConfigKeys.SparkClusterConfigKeys.sparkDriverHost);
                driverPort = config.getString(ConfigKeys.SparkClusterConfigKeys.sparkDriverPort);
                driverBindAddress = config.getString(ConfigKeys.SparkClusterConfigKeys.sparkDriverBindAddress);
                driverBlockManagerPort = config.getString(ConfigKeys.SparkClusterConfigKeys.sparkDriverBlockManagerPort);
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
        driverPort = "7077";
        driverHost = "localhost";
        driverBindAddress = "127.0.0.1";
        driverBindAddress = "10026";
    }

    public String getDriverPort() {
        return driverPort;
    }

    public String getDriverHost() {
        return driverHost;
    }

    public String getDriverBindAddress() {
        return driverBindAddress;
    }

    public String getDriverBlockManagerPort() {
        return driverBlockManagerPort;
    }
}
