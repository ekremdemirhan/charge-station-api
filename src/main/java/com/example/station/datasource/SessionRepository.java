package com.example.station.datasource;

import com.example.station.model.ChargeSession;

import java.util.Collection;
import java.util.UUID;

/**
 * @author edemirhan
 * @since 10.05.2019 00:15
 */
public interface SessionRepository {

    ChargeSession save(ChargeSession chargeSession);
    ChargeSession findById(UUID id);
    Collection<ChargeSession> getAllSessions();
}
