package org.example.service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {

    public final static ConcurrentLinkedQueue<DataModel> BASE_QUEUE = new ConcurrentLinkedQueue<>();

    private final DataService dataService;

    @Scheduled(initialDelay = 0, fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
    public void scheduleQueue() {
        var model = new DataModel().setCurrentDateTime(LocalDateTime.now().toString());
        BASE_QUEUE.add(model);
        log.info("Time add to queue. " + model);
    }

    @Scheduled(initialDelay = 100, fixedDelay = 1000)
    public void scheduleRecord() {
        Queue<DataModel> models = new LinkedList<>();
        while (BASE_QUEUE.iterator().hasNext()) {
            models.add(BASE_QUEUE.poll());
        }
//        добавить обработку при сбое на сохранении
        dataService.batchSave(models);
        log.info("Time saved. " + models);
    }
}
