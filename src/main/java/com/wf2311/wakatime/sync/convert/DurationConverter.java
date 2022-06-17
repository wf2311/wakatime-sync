package com.wf2311.wakatime.sync.convert;

import com.wf2311.wakatime.sync.domain.Duration;
import com.wf2311.wakatime.sync.entity.DurationEntity;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 16:30.
 */
public class DurationConverter {
    private List<Duration> data;
    private List<DurationEntity> durations = new ArrayList<>();
    private LocalDateTime now;

    public static DurationConverter of(List<Duration> data) {
        return new DurationConverter(data);
    }

    public List<DurationEntity> getDurations() {
        return durations;
    }

    private DurationConverter(List<Duration> data) {
        this.data = data;
        this.now = LocalDateTime.now();
        convert();
    }

    private void convert() {
        if (!CollectionUtils.isEmpty(data)) {
            this.durations = data.stream().map(this::convert).collect(Collectors.toList());
        }
    }

    private DurationEntity convert(Duration s) {
        return EntityMappers.INSTANCE.convert(s, now);
    }
}
