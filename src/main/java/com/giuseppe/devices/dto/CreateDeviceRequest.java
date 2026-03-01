package com.giuseppe.devices.dto;

import com.giuseppe.devices.domain.DeviceState;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record CreateDeviceRequest(

        @Schema(example = "Galaxy S23", description = "Name of the device.")
        @NotBlank(message = "Name is required")
        String name,

        @Schema(example = "Samsung", description = "Brand of the device.")
        @NotBlank(message = "Brand is required")
        String brand,

        @Schema(example = "INACTIVE", description = "State of the device.")
        @NotNull(message = "State is required")
        DeviceState state
) {
}