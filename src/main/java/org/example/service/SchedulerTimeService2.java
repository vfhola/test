package org.example.service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SchedulerTimeService2 {

    private final SchedulerTimeService schedulerTimeService;

    @Scheduled(initialDelay = 0, fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
    public void scheduleQueue() {
        var model = new TimeModel().setCurrentTime(LocalDateTime.now().toString());
        SchedulerTimeService.BASE_QUEUE.add(model);
        log.info("Time add to queue. " + model);
    }
}
