package com.example.station.integration;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author edemirhan
 * @since 7.05.2019 23:31
 */

@Getter
public class InitializedChargeSession {

    private String stationId;
    private LocalDateTime startedAt;
}
