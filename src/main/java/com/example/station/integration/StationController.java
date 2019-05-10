package com.example.station.integration;

import com.example.station.model.ChargeSession;
import com.example.station.service.SessionManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

/**
 * @author edemirhan
 * @since 7.05.2019 23:14
 */

@RestController
@RequestMapping(path = "/station")
public class StationController {

    private final SessionManagement sessionManagement;

    @Autowired
    public StationController(SessionManagement sessionManagement) {
        this.sessionManagement = sessionManagement;
    }

    @PostMapping(value = "/chargingSessions")
    public ResponseEntity createSession(@RequestBody InitializedChargeSession initializedSession) {

        validateNewSession(initializedSession);
        final ChargeSession chargeSession = sessionManagement.addSession(new ChargeSession(initializedSession.getStationId(), initializedSession.getStartedAt()));
        return new ResponseEntity<>(chargeSession, HttpStatus.OK);
    }

    private void validateNewSession(@RequestBody InitializedChargeSession initializedSession) {

        if (initializedSession.getStartedAt() == null ||
                initializedSession.getStartedAt().isAfter(LocalDateTime.now()) ||
                initializedSession.getStationId() == null ||
                initializedSession.getStationId().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A valid stationId and a start date should be given ");
        }
    }

    @PutMapping(value = "/chargingSessions/{id}")
    public ResponseEntity endSession(@PathVariable UUID id) {

        final ChargeSession chargeSession = sessionManagement.updateSession(id);
        if (chargeSession == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A valid id should be given ");
        }

        return new ResponseEntity<>(chargeSession, HttpStatus.OK);
    }

    @GetMapping(value = "/chargingSessions")
    public ResponseEntity getAllSessions() {

        final Collection<ChargeSession> sessions = sessionManagement.getSessions();
        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }
}
