package com.example.station.service;

import com.example.station.model.ChargeSession;

import java.util.Collection;
import java.util.UUID;

/**
 * @author edemirhan
 * @since 10.05.2019 00:41
 */
public interface SessionManagement {

    ChargeSession addSession(ChargeSession chargeSession);
    ChargeSession updateSession(UUID id);
    Collection<ChargeSession> getSessions();
}
