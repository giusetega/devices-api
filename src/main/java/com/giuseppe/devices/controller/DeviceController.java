package com.giuseppe.devices.controller;

import com.giuseppe.devices.domain.DeviceState;
import com.giuseppe.devices.dto.DeviceRequest;
import com.giuseppe.devices.dto.DeviceResponse;
import com.giuseppe.devices.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    public ResponseEntity<DeviceResponse> create(@Valid @RequestBody DeviceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(deviceService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceResponse> update(
            @PathVariable String id,
            @Valid @RequestBody DeviceRequest request) {
        DeviceResponse response = deviceService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public DeviceResponse get(@PathVariable String id) {
        return deviceService.getById(id);
    }

    @GetMapping
    public List<DeviceResponse> getAll(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) DeviceState state) {
        return deviceService.findByFilters(brand, state);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        deviceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}