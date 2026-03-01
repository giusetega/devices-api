package com.giuseppe.devices.dto;

import com.giuseppe.devices.domain.DeviceState;
import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateDeviceRequest(
        @Schema(example = "Galaxy S23", description = "Name of the device.")
        String name,

        @Schema(example = "Samsung", description = "Brand of the device.")
        String brand,

        @Schema(example = "INACTIVE", description = "State of the device.")
        DeviceState state
) {}