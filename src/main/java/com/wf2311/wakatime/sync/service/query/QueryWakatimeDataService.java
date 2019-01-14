package com.wf2311.wakatime.sync.service.query;

import com.wf2311.jfeng.time.DateHelper;
import com.wf2311.wakatime.sync.domain.vo.*;
import com.wf2311.wakatime.sync.entity.DayProjectEntity;
import com.wf2311.wakatime.sync.entity.DurationEntity;
import com.wf2311.wakatime.sync.repository.DurationRepository;
import com.wf2311.wakatime.sync.service.AbstractDaySummaryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 22:31.
 */
@Service
public class QueryWakatimeDataService extends AbstractDaySummaryService {
    @Resource
    private DurationRepository durationRepository;
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
        if (start == null || end == null || start.isAfter(end)) {
            throw new IllegalArgumentException();
        }
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
        data.setCategories(selectDayTypeGroupSummaries("day_category", start, end));
        data.setLanguages(selectDayTypeGroupSummaries("day_language", start, end));
        data.setOperatingSystems(selectDayTypeGroupSummaries("day_operating_system", start, end));
        return data;
    }

    private List<DayTypeGroupSummaryUnit> selectDayTypeGroupSummaries(String type, LocalDate start, LocalDate end) {
        String sql = "select name,sum(total_seconds) as total from " + type + " where day >=:start and day <=:end group by name";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("start", start);
        parameters.addValue("end", end);
        return namedParameterJdbcTemplate.query(sql, parameters, new DayTypeGroupSummaryUnitMapper());
    }

    class DayTypeGroupSummaryUnitMapper implements RowMapper<DayTypeGroupSummaryUnit> {
        @Override
        public DayTypeGroupSummaryUnit mapRow(ResultSet resultSet, int i) throws SQLException {
            DayTypeGroupSummaryUnit unit = new DayTypeGroupSummaryUnit();
            unit.setId(resultSet.getString("name"));
            unit.setSeconds(resultSet.getInt("total"));
            return unit;
        }
    }

    public List<DaySumVo> selectRangeDurations(String start, String end, Boolean showAll) {
        String sql = "select day,sum(total_seconds) as total from day_project";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        if (showAll == null || !showAll) {
            LocalDateTime s = DateHelper.parse(start);
            LocalDateTime e = StringUtils.isEmpty(end) ? LocalDateTime.now() : DateHelper.parse(end);
            if (s.isAfter(e)) {
                throw new IllegalArgumentException();
            }
            sql += " where day >=:start and day <=:end";
            parameters.addValue("start", start);
            parameters.addValue("end", end);
        }
        sql += " group by day";
        return namedParameterJdbcTemplate.query(sql, parameters, new DaySumMapper());
    }
    class DaySumMapper implements RowMapper<DaySumVo> {
        @Override
        public DaySumVo mapRow(ResultSet resultSet, int i) throws SQLException {
            DaySumVo unit = new DaySumVo();
            unit.setDate(resultSet.getTimestamp("day").toLocalDateTime().toLocalDate());
            unit.setTotal(resultSet.getInt("total"));
            return unit;
        }
    }
}
