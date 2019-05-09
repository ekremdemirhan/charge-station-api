package com.example.station.integration;

import com.example.station.model.ChargeSession;
import com.example.station.model.StatusEnum;
import com.example.station.service.SessionManagement;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author edemirhan
 * @since 9.05.2019 20:45
 */

@WebMvcTest(StationController.class)
@RunWith(SpringRunner.class)
public class StationControllerTest {

    private static final String STATION_ID = "stationId";

    @MockBean
    private SessionManagement sessionManagement;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void get_ReturnEmptyResponse_WhenThereIsNoSession() throws Exception {

        given(sessionManagement.getSessions()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/station/chargingSessions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(sessionManagement.getSessions().size())));
    }

    @Test
    public void get_ReturnResponseWithData_WhenThereIsASession() throws Exception {

        final LocalDateTime startDate = LocalDateTime.now();
        given(sessionManagement.getSessions()).willReturn(Collections.singletonList(new ChargeSession(STATION_ID, startDate)));

        mockMvc.perform(get("/station/chargingSessions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(sessionManagement.getSessions().size())))
                .andExpect(jsonPath("$[0].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$[0].stationId", Matchers.equalTo(STATION_ID)))
                .andExpect(jsonPath("$[0].startedAt", Matchers.equalTo(startDate.toString())))
                .andExpect(jsonPath("$[0].status", Matchers.equalTo(StatusEnum.IN_CHARGE.name())));
    }

    @Test
    public void get_ReturnResponseWithData_WhenThereAreFewSessions() throws Exception {

        final LocalDateTime startDate = LocalDateTime.now();
        given(sessionManagement.getSessions()).willReturn(Arrays.asList(new ChargeSession(STATION_ID, startDate), new ChargeSession(STATION_ID, startDate)));

        mockMvc.perform(get("/station/chargingSessions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(sessionManagement.getSessions().size())))
                .andExpect(jsonPath("$[0].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$[0].stationId", Matchers.equalTo(STATION_ID)))
                .andExpect(jsonPath("$[0].startedAt", Matchers.equalTo(startDate.toString())))
                .andExpect(jsonPath("$[0].status", Matchers.equalTo(StatusEnum.IN_CHARGE.name())))
                .andExpect(jsonPath("$[1].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$[1].stationId", Matchers.equalTo(STATION_ID)))
                .andExpect(jsonPath("$[1].startedAt", Matchers.equalTo(startDate.toString())))
                .andExpect(jsonPath("$[1].status", Matchers.equalTo(StatusEnum.IN_CHARGE.name())));
    }
}