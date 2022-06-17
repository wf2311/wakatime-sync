package com.wf2311.wakatime.sync.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalDate;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 13:38.
 */
@ConfigurationProperties(prefix = "wakatime")
public class WakatimeProperties {

    public static String SECRET_API_KEY;
    public static String PROXY_URL = null;

    private String secretApiKey;
    private String ftqqKey;
    private String dingdingKey;
    private Boolean fillNoDataDay;
    private String proxyUrl;

    private String startDay;


    public final void setStartDay(String startDay) {
        if (startDay != null) {
            try {
                this.startDay = startDay;
            } catch (Exception ignored) {
            }
        }
    }

    public LocalDate getStartDate() {
        return LocalDate.parse(startDay);
    }

    public String getSecretApiKey() {
        return secretApiKey;
    }

    public void setSecretApiKey(String secretApiKey) {
        this.secretApiKey = secretApiKey;
        SECRET_API_KEY = secretApiKey;
    }

    public void setProxyUrl(String proxyUrl) {
        this.proxyUrl = proxyUrl;
        PROXY_URL = proxyUrl;
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

    public Boolean getFillNoDataDay() {
        return fillNoDataDay;
    }

    public void setFillNoDataDay(Boolean fillNoDataDay) {
        this.fillNoDataDay = fillNoDataDay;
    }
}
