package org.example.service;

import java.util.LinkedList;
import org.example.MockIntegrationTest;
import org.example.core.TestDataModel;
import org.example.core.TestDataService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TestDataServiceTest extends MockIntegrationTest {

    @Autowired
    private TestDataService service;

    @BeforeEach
    void tearDown() {
        testDataRepository.deleteAll();
    }

    @Test
    @DisplayName("Send a batch to DB and receive it back in ascending order.")
    void checkRepositoryBaseFunction() {
        var models = getModels();

        service.batchSave(models);
        var result = service.getActualDataBaseContent();

        Assertions.assertEquals(models, result);
    }

    private LinkedList<TestDataModel> getModels() {
        LinkedList<TestDataModel> models = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            models.add(new TestDataModel().setCurrentDateTime(String.valueOf(i)));
        }

        return models;
    }
}
