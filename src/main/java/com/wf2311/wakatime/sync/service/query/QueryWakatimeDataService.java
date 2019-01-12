package com.wf2311.wakatime.sync.service.query;

import com.wf2311.jfeng.time.DateHelper;
import com.wf2311.wakatime.sync.domain.vo.*;
import com.wf2311.wakatime.sync.entity.DayProjectEntity;
import com.wf2311.wakatime.sync.entity.DurationEntity;
import com.wf2311.wakatime.sync.repository.DurationRepository;
import com.wf2311.wakatime.sync.service.AbstractDaySummaryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 22:31.
 */
@Service
public class QueryWakatimeDataService extends AbstractDaySummaryService {
    @Resource
    private DurationRepository durationRepository;
    @Resource
    private MongoTemplate mongoTemplate;

    public List<DayDurationVo> selectDayDuration(String date) {
        return findDayDurationData(DateHelper.parse(date).toLocalDate());
    }

    private List<DayDurationVo> findDayDurationData(LocalDate day) {
        List<DurationEntity> list = durationRepository.queryByDay(day);
        if (list.isEmpty()) {
            return Collections.emptyList();
        }
        return list.stream().map(d -> {
            DayDurationVo v = new DayDurationVo();
            v.setDuration(d.getDuration());
            v.setProject(d.getProject());
            v.setStartTime(d.getStartTime());
            return v;
        }).collect(Collectors.toList());
    }

    public SummaryDataVo selectSummaries(String startDay, String endDay) {
        LocalDate start = DateHelper.parse(startDay).toLocalDate();
        LocalDate end = DateHelper.parse(endDay).toLocalDate();
        SummaryDataVo data = new SummaryDataVo();
        List<DayProjectEntity> projects = dayProjectRepository.queryByDay(start, end);
        List<DayProjectChartVo> chartProjects = projects.stream().map(p -> {
            DayProjectChartVo u = new DayProjectChartVo();
            u.setDay(p.getDay().toString());
            u.setSeconds(p.getTotalSeconds());
            u.setName(p.getName());
            return u;
        }).collect(Collectors.toList());
        data.setProjects(chartProjects);
        data.setEditors(selectDayTypeGroupSummaries("day_editor", start, end));
        data.setLanguages(selectDayTypeGroupSummaries("day_language", start, end));
        data.setOperatingSystems(selectDayTypeGroupSummaries("day_operating_system", start, end));
        return data;
    }

    private List<DayTypeGroupSummaryUnit> selectDayTypeGroupSummaries(String type, LocalDate start, LocalDate end) {
        Aggregation aggregation = newAggregation(TypeSummaryGroupSelect.matchOperation(start, end), TypeSummaryGroupSelect.groupOperation());
        AggregationResults<DayTypeGroupSummaryUnit> results = mongoTemplate.aggregate(aggregation, type, DayTypeGroupSummaryUnit.class);
        return results.getMappedResults();
    }

    public List<DaySumVo> selectRangeDurations(String start, String end, Boolean showAll) {
        if (showAll != null && showAll) {
            return selectRangeDurations(RangeDurationSelect.projectionOperation(), RangeDurationSelect.groupOperation(), RangeDurationSelect.sortOperation());
        }
        if (StringUtils.isEmpty(start) && StringUtils.isEmpty(end)) {
            return selectRangeDurations();
        }
        LocalDateTime s = DateHelper.parse(start);
        LocalDateTime e = StringUtils.isEmpty(end) ? LocalDateTime.now() : DateHelper.parse(end);
        if (s.isAfter(e)) {
            throw new IllegalArgumentException();
        }
        return selectRangeDurations(s.toLocalDate(), e.toLocalDate().plusDays(1));
    }

    private List<DaySumVo> selectRangeDurations(LocalDate startDay, LocalDate endDay) {
        return selectRangeDurations(RangeDurationSelect.matchOperation(startDay, endDay), RangeDurationSelect.projectionOperation(), RangeDurationSelect.groupOperation(), RangeDurationSelect.sortOperation());
    }

    private List<DaySumVo> selectRangeDurations() {
        LocalDateTime now = LocalDateTime.now();
        return selectRangeDurations(now.minusYears(1).toLocalDate(), now.toLocalDate());
    }

    private List<DaySumVo> selectRangeDurations(AggregationOperation... operations) {
        Aggregation aggregation = newAggregation(operations);
        AggregationResults<DaySumVo> results = mongoTemplate.aggregate(aggregation, "duration", DaySumVo.class);
        return results.getMappedResults();
    }

    private static class RangeDurationSelect {
        static MatchOperation matchOperation(LocalDate startDay, LocalDate endDay) {
            return match(Criteria.where("startTime")
                    .gte(DateHelper.toDate(startDay))
                    .lt(DateHelper.toDate(endDay))
            );
        }

        static ProjectionOperation projectionOperation() {
            return project()
                    .andExpression("year(startTime)").as("year")
                    .andExpression("month(startTime)").as("month")
                    .andExpression("dayOfMonth(startTime)").as("day")
                    .andExpression("duration").as("duration");
        }

        static GroupOperation groupOperation() {
            return group(
                    fields()
                            .and("year")
                            .and("month")
                            .and("day")
            ).sum("duration").as("total");
        }

        static SortOperation sortOperation() {
            return sort(by(asc("year"), asc("month"), asc("day")));
        }
    }

    private static class TypeSummaryGroupSelect {
        static MatchOperation matchOperation(LocalDate startDay, LocalDate endDay) {
            return match(Criteria.where("day")
                    .gte(DateHelper.toDate(startDay))
                    .lte(DateHelper.toDate(endDay))
            );
        }

        static GroupOperation groupOperation() {
            return group(
                    fields().and("name")
            ).sum("totalSeconds").as("seconds");
        }
    }


}
