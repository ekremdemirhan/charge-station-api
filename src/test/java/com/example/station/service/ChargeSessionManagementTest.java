package com.example.station.service;

import com.example.station.datasource.SessionRepository;
import com.example.station.model.ChargeSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

/**
 * @author edemirhan
 * @since 10.05.2019 00:52
 */

@RunWith(MockitoJUnitRunner.Silent.class)
public class ChargeSessionManagementTest {

    private static String STATION_ID = "stationId";

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private ChargeSessionManagement sessionManagement;

    @Test
    public void sessionJustSavedToRepository_WhenAddSession() {

        final ChargeSession chargeSession = new ChargeSession(STATION_ID, LocalDateTime.now());

        sessionManagement.addSession(chargeSession);

        verify(sessionRepository, times(1)).save(chargeSession);
        verify(sessionRepository, only()).save(chargeSession);
    }
}