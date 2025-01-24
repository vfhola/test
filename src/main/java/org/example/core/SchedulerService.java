package org.example.core;

import static java.lang.Thread.sleep;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {

    public final static ConcurrentLinkedQueue<TestDataModel> BASE_QUEUE = new ConcurrentLinkedQueue<>();
    public final static LinkedList<TestDataModel> TEMPORAL = new LinkedList<>();

    private final TestDataService testDataService;

    @Scheduled(cron = "${scheduler.queue.cron}")
    public void scheduleQueue() {
        var model = new TestDataModel().setCurrentDateTime(LocalDateTime.now().toString());
        BASE_QUEUE.add(model);
        log.info("Time add to queue. " + model);
    }


    @SneakyThrows
    @Scheduled(initialDelay = 0, fixedRate = 1000)
    public void scheduleRecord() {
        LinkedList<TestDataModel> models = TEMPORAL.isEmpty() ? generateNewBatch() : TEMPORAL;

        if (!models.isEmpty()) {
            try {
                testDataService.batchSave(models);

                log.info("Time saved. " + models);
                if (!TEMPORAL.isEmpty()) {
                    log.info("Get database connection.");
                    TEMPORAL.clear();
                }
            } catch (Exception e) {
                log.error("Problem with saving batch. " + e.getMessage());
                TEMPORAL.addAll(models);
                sleep(5000);
            }
        }
    }

    private LinkedList<TestDataModel> generateNewBatch() {
        LinkedList<TestDataModel> models = new LinkedList<>();
        while (BASE_QUEUE.iterator().hasNext()) {
            models.add(BASE_QUEUE.poll());
        }

        return models;
    }
}
