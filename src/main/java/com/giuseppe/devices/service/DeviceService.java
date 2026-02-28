package com.giuseppe.devices.service;

import com.giuseppe.devices.domain.Device;
import com.giuseppe.devices.dto.DeviceRequest;
import com.giuseppe.devices.dto.DeviceResponse;
import com.giuseppe.devices.mapper.DeviceMapper;
import com.giuseppe.devices.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

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
        Optional<Device> deviceOptional = deviceRepository.findById(id);

        if (deviceOptional.isPresent()) {
            Device device = deviceOptional.get();
            device.setName(request.name());
            device.setBrand(request.brand());
            device.setState(request.state());

            Device updated = deviceRepository.save(device);
            return DeviceMapper.entityToResponse(updated);
        }

        return null;
    }

    public DeviceResponse getById(String id) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        return deviceOptional.map(DeviceMapper::entityToResponse).orElse(null);
    }

    // Not sure if it should work alone
    public DeviceResponse getByBrand(String id) {
        return null;
    }

    // Not sure if it should work alone
    public DeviceResponse getByState(String id) {
        return null;
    }

    // With get all I can probably manage getByBrand and getByState in one shot
    public List<DeviceResponse> getAll() {
        List<Device> byFilters = deviceRepository.findAll();
        return byFilters.stream().map(DeviceMapper::entityToResponse).toList();
    }

    public void delete(String id) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        deviceOptional.ifPresent(deviceRepository::delete);
    }
}
