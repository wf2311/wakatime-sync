package com.wf2311.wakatime.sync.service.sync;

import com.wf2311.wakatime.sync.convert.HeartBeatConverter;
import com.wf2311.wakatime.sync.domain.HeartBeat;
import com.wf2311.wakatime.sync.entity.HeartBeatEntity;
import com.wf2311.wakatime.sync.repository.HeartBeatRepository;
import com.wf2311.wakatime.sync.spider.WakaTimeDataSpider;
import com.wf2311.wakatime.sync.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:33.
 */
@Service
public class HeartBeatService {
    @Resource
    private HeartBeatRepository heartBeatRepository;
    /**
     * 同步某一段时间(以天为单位)内的数据
     */
    public void sync(LocalDate startDay, LocalDate endDay) {
        while (startDay.isBefore(endDay)) {
            sync(startDay);
            startDay = startDay.plusDays(1);
        }
    }

    private void deleteDataIfNotNull(LocalDate day) {
        long existCount = heartBeatRepository.countByDay(day);
        if (existCount > 0) {
            heartBeatRepository.deleteByDay(day);
        }
    }

    /**
     * 同步某天的数据
     */
    @Transactional(rollbackFor = Exception.class)
    public int sync(LocalDate day) {
        long local = heartBeatRepository.countByDay(day);
        List<HeartBeat> data = WakaTimeDataSpider.heartbeat(day);
        int remote = data != null ? data.size() : 0;
        if (remote <= local) {
            CommonUtil.syncLog().info(String.format("%s的心跳数据已是最新，无需同步。", day));
            return 0;
        }
        List<HeartBeatEntity> heartBeats = HeartBeatConverter.of(data).getHeartBeats();
        if (!CollectionUtils.isEmpty(heartBeats)) {
            deleteDataIfNotNull(day);
            heartBeatRepository.saveAll(heartBeats);
        }
        int num = (int) (remote - local);
        CommonUtil.syncLog().info(String.format("%s的心跳数据同步完毕，新增%d条记录。", day, num));
        return num;
    }
}
