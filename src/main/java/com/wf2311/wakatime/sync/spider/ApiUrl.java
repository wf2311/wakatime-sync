package com.wf2311.wakatime.sync.spider;

/**
 * wakatime api url信息
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-09 16:08.
 */
public interface ApiUrl {
    String BASE_URL = "https://wakatime.com/api/v1/";

    String SECRET_API_KEY = "9c336332-c5e7-46bb-8434-521b48441181";


    String OAUTH_URL = "https://wakatime.com/oauth/token";

    /**
     * 当前用户信息
     */
    String USER_INFO = BASE_URL + "users/current";

    /**
     * 持续时间
     */
    String DURATION = BASE_URL + "users/current/durations";

    /**
     * 心跳
     */
    String HEART_BEATS = BASE_URL + "users/current/heartbeats";

    /**
     * 项目
     */
    String PROJECTS = BASE_URL + "users/current/projects";

    String SUMMARY = BASE_URL + "users/current/summaries";

    String PROJECT_DETAIL = BASE_URL + "users/current/projects/";
}
