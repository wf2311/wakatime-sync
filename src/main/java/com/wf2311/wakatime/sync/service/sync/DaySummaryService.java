package com.wf2311.wakatime.sync.service.sync;

import com.wf2311.wakatime.sync.convert.DaySummaryConverter;
import com.wf2311.wakatime.sync.domain.DaySummary;
import com.wf2311.wakatime.sync.domain.day.DayGrandTotal;
import com.wf2311.wakatime.sync.entity.*;
import com.wf2311.wakatime.sync.repository.DaySummaryQueryHandler;
import com.wf2311.wakatime.sync.service.AbstractDaySummaryService;
import com.wf2311.wakatime.sync.spider.WakaTimeDataSpider;
import com.wf2311.wakatime.sync.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:33.
 */
@Service
public class DaySummaryService extends AbstractDaySummaryService {

    /**
     * 同步某天的summary数据
     */
    public void sync(LocalDate day) {
        Integer localSeconds = 0;
        List<DayGrandTotalEntity> dayGrandTotals = dayGrandTotalRepository.queryByDay(day);
        if (!CollectionUtils.isEmpty(dayGrandTotals)) {
            localSeconds = dayGrandTotals.get(0).getTotalSeconds();
        }
        DaySummary summary = WakaTimeDataSpider.summary(day);
        DayGrandTotal grandTotal = summary.getGrandTotal();
        if (grandTotal != null && localSeconds.equals(grandTotal.getTotalSeconds())) {
            CommonUtil.syncLog().info(String.format("%s的统计数据已是最新，无需同步。", day));
            return;
        }
        insert(summary);
    }

    @Transactional(rollbackFor = Exception.class)
    public void insert(DaySummary summary) {
        if (summary == null) {
            return;
        }
        DaySummaryConverter converter = DaySummaryConverter.of(summary);
        DayGrandTotalEntity grandTotal = converter.getGrandTotal();
        if (grandTotal == null) {
            CommonUtil.syncLog().info(summary.getDate() + "没有数据");
            return;
        }
        LocalDate day = summary.getDate();
        deleteDataIfNotNull(day, dayGrandTotalRepository);
        dayGrandTotalRepository.save(grandTotal);
        saveDayData(day, converter.getCategories(), DayCategoryEntity.class);
        saveDayData(day, converter.getDependencies(), DayDependencyEntity.class);
        saveDayData(day, converter.getEditors(), DayEditorEntity.class);
        saveDayData(day, converter.getEntities(), DayEntityEntity.class);
        saveDayData(day, converter.getLanguages(), DayLanguageEntity.class);
        saveDayData(day, converter.getOperateSystems(), DayOperateSystemEntity.class);
        saveDayData(day, converter.getProjects(), DayProjectEntity.class);
    }

    private <T> void saveDayData(LocalDate day, List<T> list, Class<? extends BaseDayEntity> clazz) {
        if (!CollectionUtils.isEmpty(list)) {
            DaySummaryQueryHandler handler = dayRepositoryMap.get(clazz);
            deleteDataIfNotNull(day, handler);
            handler.saveAll(list);
            CommonUtil.syncLog().info(String.format("%s：%d条", clazz.getSimpleName(), list.size()));
        }
    }

    private void deleteDataIfNotNull(LocalDate day, DaySummaryQueryHandler handler) {
        long existCount = handler.countByDay(day);
        if (existCount > 0) {
            handler.deleteByDay(day);
        }
    }
}
