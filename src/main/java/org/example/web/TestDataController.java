package org.example.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.core.TestDataModel;
import org.example.core.TestDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/time")
@RequiredArgsConstructor
@Tag(name = "time-controller")
@Slf4j
public class TestDataController {

    private final TestDataService testDataService;

    @GetMapping
    public List<TestDataModel> getActualDataBaseContent() {
        log.info("Request to get current database content.");

        var result = testDataService.getActualDataBaseContent();
        log.info("Response contains " + result.size() + " elements.");

        return result;
    }
}
