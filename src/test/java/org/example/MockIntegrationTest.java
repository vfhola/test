package org.example;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@ActiveProfiles("it")
@SpringBootTest
@AutoConfigureMockMvc
//@Sql(scripts = "/scripts\\add_values_to_time_table.sql", executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
//@Sql(scripts = "/scripts\\clean_time_table.sql", executionPhase = ExecutionPhase.AFTER_TEST_CLASS)
public class MockIntegrationTest {

}
