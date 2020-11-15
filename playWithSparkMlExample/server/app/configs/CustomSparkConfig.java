package configs;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import play.Logger;
import services.AppConfigService;
import javax.inject.Inject;

public class CustomSparkConfig {
    private static final String TAG = CustomSparkConfig.class.getSimpleName();
    private final Config appConf;
    private boolean isActivated;

    /* Get class variables */
    public boolean isActivated() {
        return isActivated;
    }

    /* Construction */
    @Inject
    public CustomSparkConfig(AppConfigService appConfigService) {
        this.appConf = appConfigService.getConfig();
        init();
    }

    private void init() {
        initIsActivated();
    }

    private void initIsActivated() {
        if (this.appConf != null) {
            try {
                this.isActivated = this.appConf.getBoolean(ConfigKeys
                        .SparkConfigKeys.isActivatedKey);
            } catch (ConfigException.Missing e) {
                Logger.error("{} config key {} doesn't exist!", TAG, ConfigKeys.SparkConfigKeys.isActivatedKey);
                initDefaultIsActivated();
            } catch (ConfigException.WrongType e) {
                Logger.error("{} config key {} is not a Boolean Type", TAG, ConfigKeys.SparkConfigKeys.isActivatedKey);
                initDefaultIsActivated();
            }
        } else {
            initDefaultIsActivated();
        }
    }

    private void initDefaultIsActivated() {
        this.isActivated = false;
    }
}
