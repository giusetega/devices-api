package com.giuseppe.devices.dto;

import com.giuseppe.devices.domain.DeviceState;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record DeviceResponse(
        @Schema(example = "a1b2c3d4-e5f6-7890-1234-567890abcdef", description = "UUID of the device returned.")
        String id,

        @Schema(example = "iPhone 15", description = "Name of the device.")
        String name,

        @Schema(example = "Galaxy S23", description = "Brand of the device.")
        String brand,

        @Schema(example = "AVAILABLE", description = "Actual state of the device.")
        DeviceState state,

        @Schema(example = "2025-10-12T09:15:23", description = "Timestamp when the device was added.")
        LocalDateTime creationTime
){}