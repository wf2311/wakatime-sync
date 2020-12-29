package com.wf2311.wakatime.sync;

import com.wf2311.wakatime.sync.service.TimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/5/9 10:36.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TranslationTest {

    @Resource
    private TimeService timeService;

    @Test
    public void test() throws InterruptedException, ExecutionException {
        timeService.insert();
    }
}
