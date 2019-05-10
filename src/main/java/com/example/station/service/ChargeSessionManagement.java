package com.example.station.service;

import com.example.station.datasource.SessionRepository;
import com.example.station.model.ChargeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

/**
 * @author edemirhan
 * @since 10.05.2019 00:42
 */

@Service
public class ChargeSessionManagement implements SessionManagement {

    private final SessionRepository sessionRepository;

    @Autowired
    public ChargeSessionManagement(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public ChargeSession addSession(ChargeSession chargeSession) {

        return sessionRepository.save(chargeSession);
    }

    @Override
    public ChargeSession updateSession(UUID id) {

        final ChargeSession chargeSession = sessionRepository.findById(id);
        if(chargeSession == null) {
            return null;
        }
        chargeSession.finished();
        return sessionRepository.save(chargeSession);
    }

    @Override
    public Collection<ChargeSession> getSessions() {

        return sessionRepository.getAllSessions();
    }
}
