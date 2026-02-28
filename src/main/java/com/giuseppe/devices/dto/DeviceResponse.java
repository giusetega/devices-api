package com.giuseppe.devices.dto;

import com.giuseppe.devices.domain.DeviceState;

import java.time.LocalDateTime;

public record DeviceResponse(
        String id,
        String name,
        String brand,
        DeviceState state,
        LocalDateTime creationTime
){}
