package com.wf2311.wakatime.sync.spider;

import com.wf2311.wakatime.sync.config.WakatimeProperties;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.net.SocketHttpConnectionProvider;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-09 16:11.
 */
@Slf4j
public class JoddHttpClient {

    public static String get(String url, Map<String, String> params) {
        SocketHttpConnectionProvider s = new SocketHttpConnectionProvider();
        HttpResponse response = null;

        try {
            response = HttpRequest.get(url)
                    .query("api_key", WakatimeProperties.SECRET_API_KEY)
                    .trustAllCerts(true)
                    .query(params)
                    .timeout(1000 * 10)
                    .send();
            return response
                    .bodyText();
        } catch (Exception e) {
            if (response != null) {
                response.close();
            }
            try {
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException e1) {
            }
            log.error(e.getMessage());
            log.error("try again ...");
            return get(url, params);
        } finally {
            if (response != null) {
                response.close();
            }
        }

    }
}
