package org.example.core;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TestDataService {

    private final TestDataRepository testDataRepository;

    @Transactional(readOnly = true)
    public List<TestDataModel> getActualDataBaseContent() {
        return testDataRepository.findAll();
    }

    @Transactional
    public void batchSave(LinkedList<TestDataModel> models) {
        testDataRepository.saveAll(models);
    }



    @Transactional(readOnly = true)
    public List<TestDataModel> getActualDataBaseContent(Connection connection) {
        return testDataRepository.findAll();
    }

    @Transactional
    public void batchSave(Connection connection, LinkedList<TestDataModel> models) {
        testDataRepository.saveAll(models);
    }
}
