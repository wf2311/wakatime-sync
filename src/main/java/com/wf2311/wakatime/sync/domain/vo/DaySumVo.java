package com.wf2311.wakatime.sync.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 23:45.
 */
//@SqlResultSetMapping(name = "daySumVo", classes = {
//        @ConstructorResult(targetClass = DaySumVo.class,
//                columns = {@ColumnResult(name = "date", type = LocalDate.class), @ColumnResult(name = "total", type = Integer.class)})
//})
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DaySumVo implements Serializable {
//    @Id
    private LocalDate date;
    private Integer total;
}
