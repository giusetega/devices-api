package com.giuseppe.devices.service;

import com.giuseppe.devices.dto.DeviceRequest;
import com.giuseppe.devices.dto.DeviceResponse;
import com.giuseppe.devices.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public DeviceResponse create(DeviceRequest request) {
        return null;
    }

    public DeviceResponse update(String id, DeviceRequest request) {
        return null;
    }

    public DeviceResponse getById(String id) {
        return null;
    }

    public DeviceResponse getByBrand(String id) {
        return null;
    }

    public DeviceResponse getByState(String id) {
        return null;
    }

    public List<DeviceResponse> getAll() {
        return null;
    }

    public void delete(String id) {
    }
}
