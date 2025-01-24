package org.example.service;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TimeService {

    private final TimeRepository timeRepository;

    @Transactional(readOnly = true)
    public List<TimeModel> getActualDataBaseContent() {
        return timeRepository.findAll();
    }

    @Transactional
    public void save(TimeModel model) {
        timeRepository.save(model);
    }
}
