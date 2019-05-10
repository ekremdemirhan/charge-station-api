package com.example.station.service;

import com.example.station.datasource.SessionRepository;
import com.example.station.model.ChargeSession;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.Assert.*;
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

    @Test
    public void allSessionsAreGotten_WhenGetSessions() {

        sessionManagement.getSessions();

        verify(sessionRepository, times(1)).getAllSessions();
        verify(sessionRepository, only()).getAllSessions();
    }

    @Test
    public void updateSessionSuccessfully_WhenItsIdGiven() {

        final UUID id = UUID.randomUUID();
        final ChargeSession session = new ChargeSession(STATION_ID, LocalDateTime.now());
        when(sessionRepository.findById(id)).thenReturn(session);

        sessionManagement.updateSession(id);

        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    public void updateSessionReturnsNull_WhenNotAValidIdGiven() {

        final UUID id = UUID.randomUUID();
        when(sessionRepository.findById(id)).thenReturn(null);

        final ChargeSession returnedSession = sessionManagement.updateSession(id);
        assertNull("Returned value should be null ", returnedSession);

    }
}