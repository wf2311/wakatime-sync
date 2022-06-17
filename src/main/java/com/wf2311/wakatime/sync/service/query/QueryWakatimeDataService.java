package com.wf2311.wakatime.sync.service.query;

import com.wf2311.jfeng.time.DateHelper;
import com.wf2311.wakatime.sync.config.WakatimeProperties;
import com.wf2311.wakatime.sync.domain.vo.*;
import com.wf2311.wakatime.sync.entity.DayProjectEntity;
import com.wf2311.wakatime.sync.entity.DurationEntity;
import com.wf2311.wakatime.sync.repository.DurationRepository;
import com.wf2311.wakatime.sync.service.AbstractDaySummaryService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LocalDateType;
import org.hibernate.type.StringType;
import org.springframework.util.CollectionUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 22:31.
 */
@ApplicationScoped
public class QueryWakatimeDataService extends AbstractDaySummaryService {
    @Inject
    private DurationRepository durationRepository;
    @Inject
    EntityManager entityManager;
    @Inject
    private WakatimeProperties wakatimeProperties;

    private void assertTimeInRange(LocalDate day) {
        if (day == null || day.isBefore(wakatimeProperties.getStartDate()) || day.isAfter(LocalDate.now().minusDays(1))) {
            throw new IllegalArgumentException();
        }
    }

    public List<DayDurationVo> selectDayDuration(String date) {
        LocalDate day = DateHelper.parse(date).toLocalDate();
        assertTimeInRange(day);
        return findDayDurationData(day);
    }

    public SimpleDayDurationVo findSimpleDayDurationInfo(LocalDate day) {
        assertTimeInRange(day);
        SimpleDayDurationVo vo = new SimpleDayDurationVo();
        vo.setProjects(selectDayTypeGroupSummaries("day_project", day, day));
        vo.setDay(day);
        if (CollectionUtils.isEmpty(vo.getProjects())) {
            return vo;
        }
        vo.setEditors(selectDayTypeGroupSummaries("day_editor", day, day));
        vo.setActions(selectDayTypeGroupSummaries("day_category", day, day));
        vo.setLanguages(selectDayTypeGroupSummaries("day_language", day, day));
        vo.setSystems(selectDayTypeGroupSummaries("day_operating_system", day, day));
        vo.setTotalSeconds(vo.getProjects().stream().mapToInt(DayTypeGroupSummaryUnit::getSeconds).sum());
        return vo;
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
        if (start == null || end == null || start.isAfter(end)) {
            throw new IllegalArgumentException();
        }
        assertTimeInRange(start);
        assertTimeInRange(end);

        SummaryDataVo data = new SummaryDataVo();
        List<DayProjectEntity> projects = dayProjectRepository.queryByDay(start, end);
        if (wakatimeProperties.getFillNoDataDay()) {
            fillNoDataDay(start, end, projects);
        }
        List<DayProjectChartVo> chartProjects = projects.stream()
                .sorted(Comparator.comparing(DayProjectEntity::getDay))
                .map(p -> {
                    DayProjectChartVo u = new DayProjectChartVo();
                    u.setDay(p.getDay().toString());
                    u.setSeconds(p.getTotalSeconds());
                    u.setName(p.getName());
                    return u;
                }).collect(Collectors.toList());
        data.setProjects(chartProjects);
        data.setEditors(selectDayTypeGroupSummaries("day_editor", start, end));
        data.setCategories(selectDayTypeGroupSummaries("day_category", start, end));
        data.setLanguages(selectDayTypeGroupSummaries("day_language", start, end));
        data.setOperatingSystems(selectDayTypeGroupSummaries("day_operating_system", start, end));
        return data;
    }

    private void fillNoDataDay(LocalDate start, LocalDate end, List<DayProjectEntity> projects) {
        int expectDays = start.until(end).getDays();
        if (expectDays == projects.size()) {
            return;
        }
        Set<LocalDate> exists = projects.stream().map(DayProjectEntity::getDay).collect(Collectors.toSet());
        List<DayProjectEntity> lack = IntStream.rangeClosed(0, expectDays).mapToObj(start::plusDays)
                .filter(d -> !exists.contains(d))
                .map(d -> {
                    DayProjectEntity e = new DayProjectEntity();
                    e.setDay(d);
                    e.setTotalSeconds(0);
                    e.setName("");
                    return e;
                })
                .collect(Collectors.toList());
        projects.addAll(lack);

    }

    /**
     * @param type
     * @param start
     * @param end
     * @return
     * @see https://stackoverflow.com/questions/13012584/jpa-how-to-convert-a-native-query-result-set-to-pojo-class-collection
     */
    private List<DayTypeGroupSummaryUnit> selectDayTypeGroupSummaries(String type, LocalDate start, LocalDate end) {
        String sql = "select name as id,sum(total_seconds) as seconds from " + type + " where day >=:start and day <=:end group by name";
        Query query = entityManager.createNativeQuery(sql)
                .setParameter("start", start)
                .setParameter("end", end)
                ;
        return query.unwrap(NativeQueryImpl.class)
                .setResultTransformer(Transformers.aliasToBean(DayTypeGroupSummaryUnit.class))
                .addScalar("id", StringType.INSTANCE)
                .addScalar("seconds", IntegerType.INSTANCE)
                .getResultList();
    }

    public List<DaySumVo> selectRangeDurations(String start, String end, Boolean showAll) {
        String sql = "select day as date,sum(total_seconds) as total from day_project";
        Query query;
        if (showAll == null || !showAll) {
            LocalDateTime s = DateHelper.parse(start);
            LocalDateTime e = StringUtils.isEmpty(end) ? LocalDate.now().atStartOfDay().minusSeconds(1) : DateHelper.parse(end);
            if (s.isAfter(e)) {
                throw new IllegalArgumentException();
            }
//            assertTimeInRange(s.toLocalDate());
            assertTimeInRange(e.toLocalDate());
            sql += " where day >=:start and day <=:end group by day";
            query = entityManager.createNativeQuery(sql)
                    .setParameter("start", s)
                    .setParameter("end", e);
        } else {
            sql += " where day >=:start group by day";
            query = entityManager.createNativeQuery(sql)
                    .setParameter("start", wakatimeProperties.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        }
        return query.unwrap(NativeQueryImpl.class)
                .setResultTransformer(Transformers.aliasToBean(DaySumVo.class))
                .addScalar("date", LocalDateType.INSTANCE)
                .addScalar("total", IntegerType.INSTANCE)
                .getResultList();
    }
}
