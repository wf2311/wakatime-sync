package com.wf2311.wakatime.sync.domain.day;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wf2311.wakatime.sync.domain.base.BaseWakatimeData;
import lombok.Data;

/**
 * <pre>
 *     {
 *           "digital": "13:14:46",
 *           "hours": 13,
 *           "machine_name_id": "63e18c93-7a55-4a4c-8d2b-f58a5f29a470",
 *           "minutes": 14,
 *           "name": "Unknown Hostname",
 *           "percent": 99.82,
 *           "seconds": 46,
 *           "text": "13 hrs 14 mins",
 *           "total_seconds": 47686.381000
 *         }
 * </pre>
 *
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/9/27 19:04.
 */
@Data
public class DayMachine extends BaseWakatimeData {
    @JsonProperty("machine_name_id")
    private String machineNameId;
}
