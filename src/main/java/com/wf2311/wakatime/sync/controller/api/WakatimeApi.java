package com.wf2311.wakatime.sync.controller.api;

import com.wf2311.wakatime.sync.domain.base.JsonResult;
import com.wf2311.wakatime.sync.domain.vo.DayDurationVo;
import com.wf2311.wakatime.sync.domain.vo.DaySumVo;
import com.wf2311.wakatime.sync.domain.vo.SummaryDataVo;
import com.wf2311.wakatime.sync.service.query.QueryWakatimeDataService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameters;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 22:31.
 */
@Produces(APPLICATION_JSON)
@Path("/api/v1")
public class WakatimeApi {
    @Inject
    QueryWakatimeDataService queryWakatimeDataService;

    @GET
    @Path("/durations")
    @Operation(summary = "获取指定日期的时间段数据", description = "获取指定日期的时间段数据")
    @Parameter(name = "date", description = "日期", required = true, example = "2019-01-01")
    public JsonResult<List<DayDurationVo>> durations(@QueryParam("date") String date) {
        return JsonResult.ok(queryWakatimeDataService.selectDayDuration(date));
    }

    @GET
    @Path("/summaries")
    @Operation(summary = "获取指定日期的汇总数据", description = "获取指定日期的汇总数据")
    @Parameters({
            @Parameter(name = "start", description = "开始日期", required = true, example = "2019-01-01"),
            @Parameter(name = "end", description = "结束日期", required = true, example = "2019-01-01"),
    })

    public JsonResult<SummaryDataVo> summaries(@QueryParam("start") String start, @QueryParam("end") String end) {
        return JsonResult.ok(queryWakatimeDataService.selectSummaries(start, end));
    }

    @GET
    @Path("/range/durations")
    @Operation(summary = "获取指定日期范围的时间段数据", description = "获取指定日期范围的时间段数据")
    @Parameters({
            @Parameter(name = "start", description = "开始日期", required = true, example = "2019-01-01"),
            @Parameter(name = "end", description = "结束日期", required = true, example = "2019-01-01"),
            @Parameter(name = "showAll", description = "是否查询所有时间段的数据"),
    })
    public JsonResult<List<DaySumVo>> rangeDurations(@QueryParam("start") String start, @QueryParam("end") String end, @QueryParam("showAll") Boolean showAll) {
        return JsonResult.ok(queryWakatimeDataService.selectRangeDurations(start, end, showAll));
    }
}
