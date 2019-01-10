package com.wf2311.wakatime.sync.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 13:38.
 */
@Configuration
@ConfigurationProperties(prefix = "wakatime")
public class WakatimeProperties {

    public static String SECRET_API_KEY;
    private String secretApiKey;

    public String getSecretApiKey() {
        return secretApiKey;
    }

    public void setSecretApiKey(String secretApiKey) {
        this.secretApiKey = secretApiKey;
        SECRET_API_KEY = secretApiKey;
    }
}
