package com.wf2311.wakatime.sync.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-11 00:04.
 */
@Controller
@RequestMapping
public class PageController {
    @GetMapping({"/dashboard", "/"})
    public String dashboard() {
        return "dashboard";
    }
}
