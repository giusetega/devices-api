package com.giuseppe.devices.controller;

import com.giuseppe.devices.domain.DeviceState;
import com.giuseppe.devices.dto.CreateDeviceRequest;
import com.giuseppe.devices.dto.DeviceResponse;
import com.giuseppe.devices.dto.UpdateDeviceRequest;
import com.giuseppe.devices.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeviceController implements DeviceControllerInterface{

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public ResponseEntity<DeviceResponse> createDevice(CreateDeviceRequest createDeviceRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(deviceService.create(createDeviceRequest));
    }

    @Override
    public ResponseEntity<DeviceResponse> updateDevice(String id, CreateDeviceRequest createDeviceRequest) {
        DeviceResponse response = deviceService.update(id, createDeviceRequest);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<DeviceResponse> partialUpdateDevice(String id, UpdateDeviceRequest updateDeviceRequest) {
        DeviceResponse response = deviceService.partialUpdate(id, updateDeviceRequest);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<DeviceResponse> getDevice(String id) {
        DeviceResponse deviceResponse = deviceService.getById(id);
        return ResponseEntity.ok().body(deviceResponse);
    }

    @Override
    public ResponseEntity<List<DeviceResponse>> listDevices(String brand, DeviceState state) {
        List<DeviceResponse> deviceResponseList = deviceService.findByFilters(brand, state);
        return ResponseEntity.ok().body(deviceResponseList);
    }

    @Override
    public ResponseEntity<Void> deleteDevice(String id) {
        deviceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}