package configs;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import play.Logger;

import java.util.concurrent.Callable;

abstract class AbstractBaseConfig {
    protected abstract void init(Config config);
    protected abstract void initDefault();
}
