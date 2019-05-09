package com.example.station.model;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * @author edemirhan
 * @since 7.05.2019 22:23
 */
public class ChargeSessionTest {

    private static final String DUMMY_STATION_ID = "stationId";

    @Test
    public void anIdShouldBeAssigned_WhenSessionCreated() {

        final ChargeSession chargeSession = new ChargeSession(DUMMY_STATION_ID, LocalDateTime.now());

        assertNotNull("When session is created, ID should be assigned as UUID", chargeSession.getId());
    }

    @Test
    public void statusShouldBeInCharge_WhenSessionCreated() {

        final ChargeSession chargeSession = new ChargeSession(DUMMY_STATION_ID, LocalDateTime.now());

        assertEquals("When session is created, status should be assigned as IN CHARGE", StatusEnum.IN_CHARGE, chargeSession.getStatus());
    }

    @Test
    public void statusShouldBeFinished_WhenSessionFinished() {

        final ChargeSession chargeSession = new ChargeSession(DUMMY_STATION_ID, LocalDateTime.now());

        chargeSession.finished();

        assertEquals("When session is over, status should be assigned as FINISHED", StatusEnum.FINISHED, chargeSession.getStatus());
    }
}