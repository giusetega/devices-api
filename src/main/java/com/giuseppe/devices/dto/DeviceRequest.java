package com.giuseppe.devices.dto;

import com.giuseppe.devices.domain.DeviceState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record DeviceRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Brand is required")
        String brand,

        @NotNull(message = "State is required")
        DeviceState state
) {
}
