package com.wf2311.wakatime.sync.service;

import com.wf2311.wakatime.sync.entity.Time;
import com.wf2311.wakatime.sync.repository.TimeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/5/9 09:41.
 */
@Service
public class TransService {
    @Resource
    private TimeRepository timeRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public void printAdd_new_unc(int exists) {
        _printAdd("printAdd_new_unc",exists);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void printAdd_new_c(int exists) {
        _printAdd("printAdd_new_c",exists);
    }

    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.READ_UNCOMMITTED)
    public void printAdd_support_unc(int exists) {
        _printAdd("printAdd_support_unc",exists);
    }

    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.READ_UNCOMMITTED)
    public void printAdd_support_c(int exists) {
        _printAdd("printAdd_support_c",exists);
    }

    private void _printAdd(String method, int exists) {
        List<Time> allByDay = timeRepository.findAllByDay(LocalDate.now());
        int add = allByDay.size() - exists;
        System.out.println(method + "\t:add num=" + add);
    }

    public void printAsync_new_unc(int exists) throws ExecutionException, InterruptedException {
        CompletableFuture.runAsync(() -> {
            new Runnable() {
                @Override
                @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
                public void run() {
                    _printAdd("printAsync_new_unc", exists);
                }
            };
        }).get();
    }

    public void printAsync_new_c(int exists) throws ExecutionException, InterruptedException {
        CompletableFuture.runAsync(() -> new Runnable() {
            @Override
            @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
            public void run() {
                _printAdd("printAsync_new_c", exists);
            }
        }).get();
    }

    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.READ_UNCOMMITTED)
    public void printAsync_support_unc(int exists) {
        CompletableFuture.runAsync(() -> _printAdd("printAsync_support_unc",exists));
    }

    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public void printAsync_support_c(int exists) {
        CompletableFuture.runAsync(() -> {
            _printAdd("printAsync_support_c",exists);
        });
    }
}
