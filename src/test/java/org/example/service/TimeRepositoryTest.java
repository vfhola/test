package org.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.MockIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TimeRepositoryTest extends MockIntegrationTest {

    @Autowired
    TimeRepository repository;

    @Test
    void findAll_isOk() {
//        var result = repository.findAll();
//
//        assertEquals(10, result.size());
    }

}