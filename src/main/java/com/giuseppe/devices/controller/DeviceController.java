package com.giuseppe.devices.controller;

import com.giuseppe.devices.domain.DeviceState;
import com.giuseppe.devices.dto.CreateDeviceRequest;
import com.giuseppe.devices.dto.DeviceResponse;
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

//    @PostMapping
//    public ResponseEntity<DeviceResponse> create(@Valid @RequestBody DeviceRequest request) {
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(deviceService.create(request));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<DeviceResponse> update(
//            @PathVariable String id,
//            @Valid @RequestBody DeviceRequest request) {
//        DeviceResponse response = deviceService.update(id, request);
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/{id}")
//    public DeviceResponse get(@PathVariable String id) {
//        return deviceService.getById(id);
//    }
//
//    @GetMapping
//    public List<DeviceResponse> getAll(
//            @RequestParam(required = false) String brand,
//            @RequestParam(required = false) DeviceState state) {
//        return deviceService.findByFilters(brand, state);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable String id) {
//        deviceService.delete(id);
//        return ResponseEntity.noContent().build();
//    }

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