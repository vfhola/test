package org.example.service;

import jakarta.transaction.Transactional;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SchedulerTimeService {

    public final static ConcurrentLinkedQueue<TimeModel> BASE_QUEUE = new ConcurrentLinkedQueue();

    private final TimeService timeService;
    private final DataSource dataSource;

    @Scheduled(initialDelay = 100, fixedDelay = 1000)
    public void scheduleRecord() throws SQLException {
        try {
            dataSource.getConnection();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return;
        }

        while (BASE_QUEUE.iterator().hasNext()) {
            var model = BASE_QUEUE.poll();
            log.info("Time poll from queue. " + model);
            timeService.save(model);
            log.info("Time saved. " + model);
        }
    }
}
