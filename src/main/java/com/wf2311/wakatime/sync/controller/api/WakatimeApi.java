package com.wf2311.wakatime.sync.controller.api;

import com.wf2311.wakatime.sync.domain.DayDurationUnit;
import com.wf2311.wakatime.sync.domain.base.JsonResult;
import com.wf2311.wakatime.sync.entity.DurationEntity;
import com.wf2311.wakatime.sync.service.query.QueryWakatimeDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 22:31.
 */
@RestController
@RequestMapping("/api/v1")
public class WakatimeApi {
    @Resource
    private QueryWakatimeDataService queryWakatimeDataService;

    @GetMapping("/durations")
    public JsonResult<List<DurationEntity>> durations(String date) {
        return JsonResult.ok(queryWakatimeDataService.selectDayDuration(date));
    }

    @GetMapping("/summaries")
    public JsonResult summaries(String start, String end) {
        return JsonResult.ok(queryWakatimeDataService.selectSummaries(start, end));
    }

    @GetMapping("/range/durations")
    public JsonResult<List<DayDurationUnit>> rangeDurations(String start, String end, Boolean showAll) {
        return JsonResult.ok(queryWakatimeDataService.selectRangeDurations(start, end,showAll));
    }
}
