package com.wf2311.wakatime.sync.service;

import com.wf2311.wakatime.sync.entity.Time;
import com.wf2311.wakatime.sync.repository.TimeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/5/9 09:38.
 */
@Service
public class TimeService {
    @Resource
    private TimeRepository timeRepository;
    @Resource
    private TransService transService;

    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.READ_UNCOMMITTED)
    public Time insert() throws InterruptedException, ExecutionException {
        int exists = timeRepository.findAllByDay(LocalDate.now()).size();
        Time time = new Time();
        time.setDay(LocalDate.now());
        time.setTime(LocalDateTime.now());
        timeRepository.save(time);
        transService.printAdd_new_c(exists);
        transService.printAdd_new_unc(exists);
        transService.printAdd_support_c(exists);
        transService.printAdd_support_unc(exists);
        transService.printAsync_new_c(exists);
        transService.printAsync_new_unc(exists);
        transService.printAsync_support_c(exists);
        transService.printAsync_support_unc(exists);

        TimeUnit.SECONDS.sleep(5);
        return time;
    }
}
