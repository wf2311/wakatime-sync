package com.wf2311.wakatime.sync.service.query;

import com.wf2311.jfeng.time.DateHelper;
import com.wf2311.wakatime.sync.domain.DayDurationUnit;
import com.wf2311.wakatime.sync.domain.ShowSummaryData;
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
import java.util.List;

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

    public List<DurationEntity> selectDayDuration(String date) {
        return findDayDurationData(DateHelper.parse(date).toLocalDate());
    }

    private List<DurationEntity> findDayDurationData(LocalDate day) {
        return durationRepository.queryByDay(day);
    }

    public ShowSummaryData selectSummaries(String startDay, String endDay) {
        LocalDate start = DateHelper.parse(startDay).toLocalDate();
        LocalDate end = DateHelper.parse(endDay).toLocalDate();
        ShowSummaryData data = new ShowSummaryData();
//        data.setCategories(dayCategoryRepository.queryByDay(start, end));
//        data.setDependencies(dayDependencyRepository.queryByDay(start, end));
//        data.setEditors(dayEditorRepository.queryByDay(start, end));
//        data.setEntities(dayEntityRepository.queryByDay(start, end));
//        data.setGrandTotal(dayGrandTotalRepository.queryByDay(start, end));
//        data.setLanguages(dayLanguageRepository.queryByDay(start, end));
//        data.setOperatingSystems(dayOperateSystemRepository.queryByDay(start, end));
        data.setProjects(dayProjectRepository.queryByDay(start, end));
        return data;
    }

    public List<DayDurationUnit> selectRangeDurations(String start, String end, Boolean showAll) {
        if (showAll != null && showAll) {
            return selectRangeDurations(projectionOperation(), groupOperation(), sortOperation());
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

    private List<DayDurationUnit> selectRangeDurations(LocalDate startDay, LocalDate endDay) {
        return selectRangeDurations(matchOperation(startDay, endDay), projectionOperation(), groupOperation(), sortOperation());
    }

    private List<DayDurationUnit> selectRangeDurations() {
        LocalDateTime now = LocalDateTime.now();
        return selectRangeDurations(now.minusYears(1).toLocalDate(), now.toLocalDate());
    }

    private List<DayDurationUnit> selectRangeDurations(AggregationOperation... operations) {
        Aggregation aggregation = newAggregation(operations);
        AggregationResults<DayDurationUnit> results = mongoTemplate.aggregate(aggregation, "duration", DayDurationUnit.class);
        return results.getMappedResults();
    }

    private MatchOperation matchOperation(LocalDate startDay, LocalDate endDay) {
        return match(Criteria.where("startTime")
                .gte(DateHelper.toDate(startDay))
                .lt(DateHelper.toDate(endDay))
        );
    }

    private ProjectionOperation projectionOperation() {
        return project()
                .andExpression("year(startTime)").as("year")
                .andExpression("month(startTime)").as("month")
                .andExpression("dayOfMonth(startTime)").as("day")
                .andExpression("duration").as("duration");
    }

    private GroupOperation groupOperation() {
        return group(
                fields()
                        .and("year")
                        .and("month")
                        .and("day")
        ).sum("duration").as("total");
    }

    private SortOperation sortOperation() {
        return sort(by(asc("year"), asc("month"), asc("day")));
    }
}
