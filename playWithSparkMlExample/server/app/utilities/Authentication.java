package utilities;

import com.typesafe.config.Config;

public final class Authentication {
    public static boolean isAuthorizedSeed(Config appConf, String requestSeed) {
        String systemSecret = appConf.getString("tcp.seed");
        return requestSeed.equals(systemSecret);
    }
}
