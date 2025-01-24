package org.example.service;

import java.util.List;
import java.util.Queue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataService {

    private final DataRepository dataRepository;

    @Transactional(readOnly = true)
    public List<DataModel> getActualDataBaseContent() {
        return dataRepository.findAll();
    }

    @Transactional
    public void batchSave(Queue<DataModel> models) {
        dataRepository.saveAll(models);
    }
}
