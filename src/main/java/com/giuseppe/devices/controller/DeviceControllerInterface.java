package com.giuseppe.devices.controller;

import com.giuseppe.devices.domain.DeviceState;
import com.giuseppe.devices.dto.CreateDeviceRequest;
import com.giuseppe.devices.dto.DeviceResponse;
import com.giuseppe.devices.dto.UpdateDeviceRequest;
import com.giuseppe.devices.exception.APIErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Devices", description = "Device management APIs.")
@RequestMapping("/api/v1/devices")
public interface DeviceControllerInterface {

    @Operation(
            description = "Creates a new device.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = DeviceResponse.class)))
            }
    )
    @PostMapping(produces = "application/json", consumes = "application/json")
    ResponseEntity<DeviceResponse> createDevice(
            @Parameter(description = "Device payload", required = true)
            @Valid @RequestBody CreateDeviceRequest createDeviceRequest
    );


    @Operation(
            description = "Update of a device.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = DeviceResponse.class)))
            }
    )
    @PutMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    ResponseEntity<DeviceResponse> updateDevice(
            @Parameter(description = "Device ID", required = true, in = ParameterIn.PATH) @PathVariable("id") String id,
            @Parameter(description = "Device payload", required = true) @Valid @RequestBody CreateDeviceRequest createDeviceRequest
    );

    @Operation(
            description = "Partial update of a device.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = DeviceResponse.class)))
            }
    )
    @PatchMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    ResponseEntity<DeviceResponse> partialUpdateDevice(
            @Parameter(description = "Device ID", required = true, in = ParameterIn.PATH) @PathVariable("id") String id,
            @Parameter(description = "Device payload", required = true) @RequestBody UpdateDeviceRequest updateDeviceRequest
    );


    @Operation(
            description = "Returns a device by ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = DeviceResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    @GetMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<DeviceResponse> getDevice(
            @Parameter(description = "Device ID", required = true, in = ParameterIn.PATH) @PathVariable("id") String id
    );


    @Operation(
            description = "Returns devices, optionally filtered.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = DeviceResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    @GetMapping(produces = "application/json")
    ResponseEntity<List<DeviceResponse>> listDevices(
            @Parameter(description = "Filter by brand", in = ParameterIn.QUERY) @RequestParam(value = "brand", required = false) String brand,
            @Parameter(description = "Filter by state", in = ParameterIn.QUERY) @RequestParam(value = "state", required = false) DeviceState state
    );


    @Operation(
            description = "Deletes a device by ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteDevice(
            @Parameter(description = "Device ID", required = true, in = ParameterIn.PATH) @PathVariable("id") String id
    );
}