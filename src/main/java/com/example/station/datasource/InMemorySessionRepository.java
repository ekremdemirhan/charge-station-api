package com.example.station.datasource;

import com.example.station.model.ChargeSession;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author edemirhan
 * @since 8.05.2019 00:03
 */

@Service
public class InMemorySessionRepository implements SessionRepository {

    private ConcurrentHashMap<UUID, ChargeSession> sessionMap = new ConcurrentHashMap<>();

    public void fillMap() {

        for(int i = 0; i < 100; i++) {
            final ChargeSession chargeSession = new ChargeSession(String.valueOf(new Random().nextInt(11111)), LocalDateTime.now());
            sessionMap.put(chargeSession.getId(), chargeSession);
        }
    }

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
