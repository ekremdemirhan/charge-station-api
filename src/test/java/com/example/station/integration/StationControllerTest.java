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
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    public static final String dateTimeFormat = "uuuu-MM-dd'T'HH:mm:ss";

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
                .andExpect(jsonPath("$[0].startedAt", Matchers.containsString(toString(startDate))))
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
                .andExpect(jsonPath("$[0].startedAt", Matchers.containsString(toString(startDate))))
                .andExpect(jsonPath("$[0].status", Matchers.equalTo(StatusEnum.IN_CHARGE.name())))
                .andExpect(jsonPath("$[1].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$[1].stationId", Matchers.equalTo(STATION_ID)))
                .andExpect(jsonPath("$[1].startedAt", Matchers.containsString(toString(startDate))))
                .andExpect(jsonPath("$[1].status", Matchers.equalTo(StatusEnum.IN_CHARGE.name())));
    }

    @Test
    public void post_ReturnResponse200WithSavedData_WhenAValidRequestComes() throws Exception {

        final LocalDateTime startedTime = LocalDateTime.now().minusDays(1L);
        final ChargeSession chargeSession = new ChargeSession(STATION_ID, startedTime);
        given(sessionManagement.addSession(any())).willReturn(chargeSession);

        mockMvc.perform(post("/station/chargingSessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new TestInitializedChargeSession(STATION_ID, startedTime).toJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(chargeSession.getId().toString())))
                .andExpect(jsonPath("$.stationId", Matchers.equalTo(STATION_ID)))
                .andExpect(jsonPath("$.startedAt", Matchers.containsString(toString(startedTime))))
                .andExpect(jsonPath("$.status", Matchers.equalTo(StatusEnum.IN_CHARGE.name())));
    }

    @Test
    public void post_ReturnResponse400_WhenAnInvalidRequestComes() throws Exception {

        final LocalDateTime startedTime = LocalDateTime.now().minusDays(1L);
        final ChargeSession chargeSession = new ChargeSession(STATION_ID, startedTime);
        given(sessionManagement.addSession(any())).willReturn(chargeSession);

        final String properContent = new TestInitializedChargeSession(STATION_ID, startedTime).toJson();
        final String improperContent = properContent.replaceFirst("stationId", " stationId");

        mockMvc.perform(post("/station/chargingSessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(improperContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void post_ReturnResponse400_WhendStartTimeAtFutureComes() throws Exception {

        final LocalDateTime futureDate = LocalDateTime.now().plusDays(1L);

        mockMvc.perform(post("/station/chargingSessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new TestInitializedChargeSession(STATION_ID, futureDate).toJson()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void post_ReturnResponse400_WhenEmptyStationIdParameterComes() throws Exception {

        final LocalDateTime startedDate = LocalDateTime.now().minusDays(1L);

        mockMvc.perform(post("/station/chargingSessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new TestInitializedChargeSession("", startedDate).toJson()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void put_ReturnResponse200WithUpdatedData_WhenAValidIdParameterComes() throws Exception {

        final LocalDateTime startedDate = LocalDateTime.now().minusDays(1L);
        final ChargeSession chargeSession = new ChargeSession(STATION_ID, startedDate);
        chargeSession.finished();
        given(sessionManagement.updateSession(any())).willReturn(chargeSession);

        mockMvc.perform(put("/station/chargingSessions/" + chargeSession.getId().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(chargeSession.getId().toString())))
                .andExpect(jsonPath("$.stationId", Matchers.equalTo(STATION_ID)))
                .andExpect(jsonPath("$.startedAt", Matchers.containsString(toString(startedDate))))
                .andExpect(jsonPath("$.status", Matchers.equalTo(StatusEnum.FINISHED.name())));
    }

    @Test
    public void put_ReturnResponse200WithUpdatedData_WhenAnInvalidIdParameterComes() throws Exception {

        given(sessionManagement.updateSession(any())).willReturn(null);

        mockMvc.perform(put("/station/chargingSessions/invalidId")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private String toString(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(dateTimeFormat));
    }

    class TestInitializedChargeSession {

        private final String stationId;
        private final LocalDateTime startedAt;

        TestInitializedChargeSession(String stationId, LocalDateTime startedAt) {
            this.stationId = stationId;
            this.startedAt = startedAt;
        }

        String toJson() {
            return "{" + "\"stationId\":\"" + stationId + "\"," +
                    "\"startedAt\":\"" + startedAt.format(DateTimeFormatter.ofPattern(dateTimeFormat)) + "\"}";
        }
    }
}