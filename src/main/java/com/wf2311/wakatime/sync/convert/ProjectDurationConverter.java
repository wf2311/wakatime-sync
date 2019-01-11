package com.wf2311.wakatime.sync.convert;

import com.wf2311.wakatime.sync.domain.ProjectDuration;
import com.wf2311.wakatime.sync.entity.ProjectDurationEntity;
import com.wf2311.wakatime.sync.spider.JsonParser;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 16:38.
 */
public class ProjectDurationConverter {
    private List<ProjectDuration> data;
    private List<ProjectDurationEntity> durations = new ArrayList<>();
    private LocalDateTime now;

    public static ProjectDurationConverter of(List<ProjectDuration> data) {
        return new ProjectDurationConverter(data);
    }

    private ProjectDurationConverter(List<ProjectDuration> data) {
        this.data = data;
        this.now = LocalDateTime.now();
        convert();
    }

    public List<ProjectDurationEntity> getDurations() {
        return durations;
    }

    private void convert() {
        if (!CollectionUtils.isEmpty(data)) {
            this.durations = data.stream().map(this::convert).collect(Collectors.toList());
        }
    }

    private ProjectDurationEntity convert(ProjectDuration s) {
        ProjectDurationEntity duration = new ProjectDurationEntity();
        BeanUtils.copyProperties(s, duration, "dependencies");
        duration.setDependencies(JsonParser.parseDependencies(s.getDependencies()));
        duration.setStartTime(EntityConvertHelper.doubleToDate(s.getSecond()));
        duration.setEndTime(EntityConvertHelper.doubleToDate(s.getSecond() + s.getDuration()));
        duration.setCreatedTime(now);
        return duration;
    }
}
