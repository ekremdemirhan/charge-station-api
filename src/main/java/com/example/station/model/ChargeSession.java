package com.example.station.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author edemirhan
 * @since 7.05.2019 22:15
 */

@Getter
@RequiredArgsConstructor
public class ChargeSession {

    private final UUID id = UUID.randomUUID();
    private final String stationId;
    private final LocalDateTime startedAt;
    private StatusEnum status = StatusEnum.IN_CHARGE;

    public void finished() {

        this.status = StatusEnum.FINISHED;
    }
}
