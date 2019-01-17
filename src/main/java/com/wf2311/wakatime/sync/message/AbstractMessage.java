package com.wf2311.wakatime.sync.message;

import com.wf2311.wakatime.sync.config.WakatimeProperties;
import com.wf2311.wakatime.sync.domain.vo.DayTypeGroupSummaryUnit;
import com.wf2311.wakatime.sync.domain.vo.SimpleDayDurationVo;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-17 22:12.
 */
public abstract class AbstractMessage {
    @Resource
    protected WakatimeProperties wakatimeProperties;

    private static final String TABLE_TR_FORMAT = "- %s: %s\n";

    public abstract void sendMessage(SimpleDayDurationVo info);

    protected static String getMessageTitle(LocalDate day) {
        return "Wakatime " + day.toString() + "信息";
    }

    protected static String formatMarkdownMessage(SimpleDayDurationVo vo) {
        if (CollectionUtils.isEmpty(vo.getProjects())) {
            return "无数据";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("### 总时长：").append(formatTime(vo.getTotalSeconds())).append("小时\n");
        if (!CollectionUtils.isEmpty(vo.getProjects())) {
            formatMarkdownTypeListPart(sb, "项目", vo.getProjects());
        }
        if (!CollectionUtils.isEmpty(vo.getLanguages())) {
            formatMarkdownTypeListPart(sb, "语言", vo.getLanguages());
        }
        if (!CollectionUtils.isEmpty(vo.getEditors())) {
            formatMarkdownTypeListPart(sb, "编辑器", vo.getEditors());
        }
        if (!CollectionUtils.isEmpty(vo.getActions())) {
            formatMarkdownTypeListPart(sb, "行为", vo.getActions());
        }
        if (!CollectionUtils.isEmpty(vo.getSystems())) {
            formatMarkdownTypeListPart(sb, "操作系统", vo.getSystems());
        }
        return sb.toString();
    }

    private static void formatMarkdownTypeListPart(StringBuilder sb, String title, List<DayTypeGroupSummaryUnit> list) {
        sb.append("### ").append(title).append("\n");
        list.stream().sorted(Comparator.comparingInt(DayTypeGroupSummaryUnit::getSeconds).reversed())
                .forEach(u -> sb.append(String.format(TABLE_TR_FORMAT, u.getId(), formatTime(u.getSeconds()))));
        sb.append("\n\n");
    }

    private static String formatTime(int seconds) {
        if (seconds < 60) {
            return seconds + "秒";
        }
        if (seconds < 3600) {
            return (seconds / 60) + "分钟";
        }
        return (seconds / 3600) + "小时" + ((seconds % 3600) / 60) + "分钟";
    }

}
