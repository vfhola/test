package org.example.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.example.exception.GlobalExceptionHandler;
import org.example.core.TestDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class TestDataControllerTest {

    private static final String PATH_PREFIX = "/time";

    private MockMvc mvc;

    @Mock
    private TestDataService service;

    @InjectMocks
    private TestDataController controller;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getActualDataBaseContent_GetRequest_ReturnResponseWithStatus200() throws Exception {
        mvc.perform(get(PATH_PREFIX))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getActualDataBaseContent_GetRequest_ReturnResponseWithStatus500() throws Exception {
        when(service.getActualDataBaseContent()).thenThrow(NullPointerException.class);

        mvc.perform(get(PATH_PREFIX))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }
}