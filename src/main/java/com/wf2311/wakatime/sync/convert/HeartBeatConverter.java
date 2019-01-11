package com.wf2311.wakatime.sync.convert;

import com.wf2311.wakatime.sync.domain.HeartBeat;
import com.wf2311.wakatime.sync.entity.HeartBeatEntity;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 16:23.
 */
public class HeartBeatConverter {
    private List<HeartBeat> data;
    private List<HeartBeatEntity> heartBeats = new ArrayList<>();
    private LocalDateTime now;

    public static HeartBeatConverter of(List<HeartBeat> data) {
        return new HeartBeatConverter(data);
    }

    public List<HeartBeatEntity> getHeartBeats() {
        return heartBeats;
    }

    private HeartBeatConverter(List<HeartBeat> data) {
        this.data = data;
        this.now = LocalDateTime.now();
        convert();
    }

    private void convert() {
        if (!CollectionUtils.isEmpty(data)) {
            this.heartBeats = data.stream().map(this::convert).collect(Collectors.toList());
        }
    }

    private HeartBeatEntity convert(HeartBeat s) {
        HeartBeatEntity t = new HeartBeatEntity();
        String simpleName = EntityConvertHelper.getSimpleName(s.getEntity());
        t.setSimpleName(simpleName);
        t.setName(s.getEntity());
        t.setTime(EntityConvertHelper.doubleToDate(s.getTimestamp()));
        t.setType(s.getType());
        t.setUuid(s.getUuid());
        t.setCreatedTime(now);
        return t;
    }
}
