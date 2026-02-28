package com.giuseppe.devices.service;

import com.giuseppe.devices.domain.Device;
import com.giuseppe.devices.domain.DeviceState;
import com.giuseppe.devices.dto.DeviceRequest;
import com.giuseppe.devices.dto.DeviceResponse;
import com.giuseppe.devices.exception.ResourceNotFoundException;
import com.giuseppe.devices.mapper.DeviceMapper;
import com.giuseppe.devices.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    private static final String DEVICE_NOT_FOUND = "Device not found";

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public DeviceResponse create(DeviceRequest request) {
        Device device = DeviceMapper.requestToEntity(request);
        Device saved = deviceRepository.save(device);
        return DeviceMapper.entityToResponse(saved);
    }

    public DeviceResponse update(String id, DeviceRequest request) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(DEVICE_NOT_FOUND));

        device.setName(request.name());
        device.setBrand(request.brand());
        device.setState(request.state());

        Device updated = deviceRepository.save(device);

        return DeviceMapper.entityToResponse(updated);
    }

    public DeviceResponse getById(String id) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(DEVICE_NOT_FOUND));
        return DeviceMapper.entityToResponse(device);
    }


    // With get all I can probably manage getByBrand and getByState in one shot
    public List<DeviceResponse> findByFilters(String brand, DeviceState state) {
        List<Device> byFilters = deviceRepository.findByFilters(brand, state);
        return byFilters.stream().map(DeviceMapper::entityToResponse).toList();
    }

    public void delete(String id) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(DEVICE_NOT_FOUND));
        deviceRepository.delete(device);
    }
}
