package com.wf2311.wakatime.sync.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 13:38.
 */
@Configuration
@ConfigurationProperties(prefix = "wakatime")
public class WakatimeProperties {

    public static String SECRET_API_KEY;
    private String secretApiKey;
    private String ftqqKey;
    private String dingdingKey;

    private LocalDate startDay = LocalDate.now();

    public void setStartDay(String startDay) {
        if (startDay != null) {
            try {
                this.startDay = LocalDate.parse(startDay);
            } catch (Exception e) {
            }
        }
    }

    public LocalDate getStartDate() {
        return startDay;
    }

    public String getSecretApiKey() {
        return secretApiKey;
    }

    public void setSecretApiKey(String secretApiKey) {
        this.secretApiKey = secretApiKey;
        SECRET_API_KEY = secretApiKey;
    }

    public String getFtqqKey() {
        return ftqqKey;
    }

    public void setFtqqKey(String ftqqKey) {
        this.ftqqKey = ftqqKey;
    }

    public String getDingdingKey() {
        return dingdingKey;
    }

    public void setDingdingKey(String dingdingKey) {
        this.dingdingKey = dingdingKey;
    }
}
