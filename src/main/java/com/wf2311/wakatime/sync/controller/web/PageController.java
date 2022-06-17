package com.wf2311.wakatime.sync.controller.web;

import com.wf2311.wakatime.sync.config.WakatimeProperties;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.format.DateTimeFormatter;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-11 00:04.
 */
@Path("/")
@Produces(MediaType.TEXT_HTML)
public class PageController {
    @Inject
    WakatimeProperties wakatimeProperties;
    @Location("dashboard.html")
    Template dashboard;

    @GET
    public TemplateInstance get() {
        return dashboard.data("startDay", wakatimeProperties.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
