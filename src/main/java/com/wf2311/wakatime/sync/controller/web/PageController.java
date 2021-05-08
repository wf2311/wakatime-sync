package com.wf2311.wakatime.sync.controller.web;

import com.wf2311.wakatime.sync.config.WakatimeProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-11 00:04.
 */
@Controller
@RequestMapping
public class PageController {
    @Resource
    private WakatimeProperties wakatimeProperties;

    @GetMapping({"/dashboard", "/"})
    public String dashboard(Model model) {
        model.addAttribute("startDay",wakatimeProperties.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return "dashboard";
    }
}
