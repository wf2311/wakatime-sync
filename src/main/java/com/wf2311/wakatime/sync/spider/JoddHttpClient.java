package com.wf2311.wakatime.sync.spider;

import com.wf2311.wakatime.sync.config.WakatimeProperties;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import jodd.http.net.SocketHttpConnectionProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-09 16:11.
 */
@Slf4j
public class JoddHttpClient {
    private final static String PROXY_URL_PATTERN = "(?<protocol>[\\S]+)(://)(?<url>[\\S]+)(:)(?<port>[\\d]+)";

    private static ProxyInfo getProxy(String proxy) {
        Pattern r = Pattern.compile(PROXY_URL_PATTERN);
        Matcher m = r.matcher(proxy);
        String protocol = null;
        String url = null;
        int port = 80;
        while (m.find()) {
            protocol = m.group("protocol");
            url = m.group("url");
            port = Integer.parseInt(m.group("port"));
        }
        String errorMesage = "illegal proxy url :" + proxy;
        if (protocol == null) {
            throw new IllegalArgumentException(errorMesage);
        }
        String address = url;
        switch (protocol.toLowerCase()) {
            case "http":
            case "https":
                return ProxyInfo.httpProxy(address, port, null, null);
            case "socks4":
                return ProxyInfo.socks4Proxy(address, port, null);
            case "socks5":
                return ProxyInfo.socks5Proxy(address, port, null, null);
            default:
                throw new IllegalArgumentException(errorMesage);
        }

    }

    public static String get(String url, Map<String, String> params) {
        SocketHttpConnectionProvider s = new SocketHttpConnectionProvider();
        HttpResponse response = null;

        try {
            HttpRequest request = HttpRequest.get(url)
                    .query("api_key", WakatimeProperties.SECRET_API_KEY)
                    .trustAllCerts(true)
                    .query(params)
                    .timeout(1000 * 30);
            if (StringUtils.isNotEmpty(WakatimeProperties.PROXY_URL)) {
                SocketHttpConnectionProvider sp = new SocketHttpConnectionProvider();
                sp.useProxy(getProxy(WakatimeProperties.PROXY_URL));
                request.withConnectionProvider(sp);
            }
            response = request
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
