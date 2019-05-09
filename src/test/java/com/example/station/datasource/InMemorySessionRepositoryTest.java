package com.example.station.datasource;

import com.example.station.model.ChargeSession;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author edemirhan
 * @since 9.05.2019 23:58
 */

public class InMemorySessionRepositoryTest {

    private static final String STATION_ID = "stationId";

    @Test
    public void sessionShouldBeSaved_WhenSessionIsDecent() {

        InMemorySessionRepository sessionRepository =  new InMemorySessionRepository();

        final ChargeSession chargeSession = sessionRepository.save(new ChargeSession(STATION_ID, LocalDateTime.now()));

        assertNotNull("ChargeSession should be saved successfully.", chargeSession);
    }

    @Test
    public void sessionShouldNotBeSaved_WhenSessionIsNull() {

        InMemorySessionRepository sessionRepository =  new InMemorySessionRepository();

        final ChargeSession chargeSession = sessionRepository.save(null);

        assertNull("ChargeSession<null> should not be saved.", chargeSession);
    }

    @Test
    public void sessionShouldBeReturned_WhenFindByItsId() {

        InMemorySessionRepository sessionRepository =  new InMemorySessionRepository();
        final ChargeSession expectedSession = sessionRepository.save(new ChargeSession(STATION_ID, LocalDateTime.now()));

        final ChargeSession actualSession = sessionRepository.findById(expectedSession.getId());

        assertNotNull("ChargeSession should be obtained successfully.", actualSession);
        assertEquals("ChargeSession<Id> should be matched.", expectedSession.getId(), actualSession.getId());
        assertEquals("ChargeSession<StationId> should be matched.", expectedSession.getStationId(), actualSession.getStationId());
        assertEquals("ChargeSession<StartedAt> should be matched.", expectedSession.getStartedAt(), actualSession.getStartedAt());
        assertEquals("ChargeSession<Status> should be matched.", expectedSession.getStatus(), actualSession.getStatus());
    }

    @Test
    public void sessionShouldReturnedAsNull_WhenFindByNull() {

        InMemorySessionRepository sessionRepository =  new InMemorySessionRepository();

        final ChargeSession actualSession = sessionRepository.findById(null);

        assertNull("ChargeSession should be null if finding by id<null>", actualSession);
    }

    @Test
    public void allSessionsShouldBeReturned_WhenGetAll() {

        InMemorySessionRepository sessionRepository =  new InMemorySessionRepository();
        final List<ChargeSession> chargeSessions = Arrays.asList(new ChargeSession(STATION_ID, LocalDateTime.now()),
                new ChargeSession(STATION_ID, LocalDateTime.now()));
        chargeSessions.forEach(sessionRepository::save);

        final Collection<ChargeSession> allSessions = sessionRepository.getAllSessions();
        assertEquals("Saved and gotten element size should be matched", chargeSessions.size(), allSessions.size());
    }

}