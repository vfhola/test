package org.example.service;

import static java.lang.Thread.sleep;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.awaitility.Duration;
import org.example.MockIntegrationTest;
import org.example.core.SchedulerService;
import org.example.core.TestDataModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;

@TestMethodOrder(OrderAnnotation.class)
class SchedulerServiceTest extends MockIntegrationTest {

    private static EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @MockitoSpyBean
    private SchedulerService service;

    @BeforeAll
    static void beforeAll() throws Exception {
        environmentVariables.set("TEST_CRON", "*/1 * * * * *");
        environmentVariables.setup();
    }

    @AfterAll
    static void afterAll() throws Exception {
        environmentVariables.teardown();
    }

    @BeforeEach
    void beforeEach() {
        testDataRepository.deleteAll();
    }

    @Test
    @Order(1)
    void whenWaitFiveSecond_thenScheduleQueueIsCalledAtLeastFiveTimes() {
        await()
                .atMost(Duration.FIVE_SECONDS)
                .untilAsserted(() -> verify(service, atLeast(4)).scheduleQueue());
    }

    @Test
    @Order(2)
    void whenWaitFiveSecond_thenScheduleRecordIsCalledAtLeastFourTimes() {
        await()
                .atMost(Duration.FIVE_SECONDS)
                .untilAsserted(() -> verify(service, atLeast(4)).scheduleRecord());
    }

    @Test
    @Order(3)
    @DisplayName("Normal work with connection to DB. Records are send every second.")
    void normalWorkWithConnectToDB() throws InterruptedException {
        sleep(10000);

        assertFindAllResultIsCorrect(testDataRepository.findAll());
    }

    private void assertFindAllResultIsCorrect(List<TestDataModel> result) {
        var modelMap = result.stream().collect(Collectors.toMap(TestDataModel::getId, Function.identity()));
        var size = result.size();
        var minId = result.get(0).getId();
        var maxId = result.get(size - 1).getId();

        Assertions.assertTrue(size > 1, "Result is " + result);
        modelMap.keySet().forEach(id -> {
            var time = getTime(modelMap.get(id));
            var seconds = time.getSecond();
            if (id > minId) {
                var beforeTime = getTime(modelMap.get(id - 1));
                Assertions.assertTrue(time.isAfter(beforeTime), "Compare time is after.");
                Assertions.assertTrue(List.of(1, -59).contains(seconds - beforeTime.getSecond()),
                        "Compare seconds with past record. " + getMessage(modelMap.get(id), modelMap.get(id - 1)));
            }
            if (id < maxId) {
                var afterTime = getTime(modelMap.get(id + 1));
                Assertions.assertTrue(time.isBefore(afterTime), "Compare time is before.");
                Assertions.assertTrue(List.of(1, -59).contains(afterTime.getSecond() - seconds),
                        "Compare seconds with next record. " + getMessage(modelMap.get(id), modelMap.get(id + 1)));
            }
        });
    }

    private LocalDateTime getTime(TestDataModel testDataModel) {
        return LocalDateTime.parse(testDataModel.getCurrentDateTime(), DateTimeFormatter.ISO_DATE_TIME);
    }

    private String getMessage(TestDataModel commonTestDataModel, TestDataModel compareTestDataModel) {
        return String.format("Common %s. Next %s.", commonTestDataModel, compareTestDataModel);
    }
}
