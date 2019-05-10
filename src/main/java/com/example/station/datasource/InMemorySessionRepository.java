package com.example.station.datasource;

import com.example.station.model.ChargeSession;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author edemirhan
 * @since 8.05.2019 00:03
 */

@Service
public class InMemorySessionRepository implements SessionRepository {

    private ConcurrentHashMap<UUID, ChargeSession> sessionMap = new ConcurrentHashMap<>();

    @Override
    public ChargeSession save(ChargeSession chargeSession) {

        if (chargeSession == null) {
            return null;
        }
        sessionMap.put(chargeSession.getId(), chargeSession);
        return chargeSession;
    }

    @Override
    public ChargeSession findById(UUID id) {

        if (id == null) {
            return null;
        }
        return sessionMap.get(id);
    }

    @Override
    public Collection<ChargeSession> getAllSessions() {

        return sessionMap.values();
    }
}
