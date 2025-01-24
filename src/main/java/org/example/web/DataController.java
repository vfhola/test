package org.example.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.DataModel;
import org.example.service.DataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/time")
@RequiredArgsConstructor
@Tag(name = "time-controller")
@Slf4j
public class DataController {

    private final DataService dataService;

    @GetMapping
    public List<DataModel> getActualDataBaseContent() {
        log.info("Request to get current database content.");

        var result = dataService.getActualDataBaseContent();
        log.info("Response contains " + result.size() + " elements.");

        return result;
    }
}
