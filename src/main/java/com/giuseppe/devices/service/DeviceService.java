package com.giuseppe.devices.service;

import com.giuseppe.devices.domain.Device;
import com.giuseppe.devices.domain.DeviceState;
import com.giuseppe.devices.dto.CreateDeviceRequest;
import com.giuseppe.devices.dto.DeviceResponse;
import com.giuseppe.devices.exception.BusinessException;
import com.giuseppe.devices.exception.ResourceNotFoundException;
import com.giuseppe.devices.mapper.DeviceMapper;
import com.giuseppe.devices.repository.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class DeviceService {

    private static final String DEVICE_NOT_FOUND = "Device not found";

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Transactional
    public DeviceResponse create(CreateDeviceRequest request) {
        Device device = DeviceMapper.requestToEntity(request);

        Device saved = deviceRepository.save(device);
        log.info("Device with id {} has been saved", saved.getId());

        return DeviceMapper.entityToResponse(saved);
    }

    @Transactional
    public DeviceResponse update(String id, CreateDeviceRequest request) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(DEVICE_NOT_FOUND));

        if (device.getState() == DeviceState.IN_USE) {

            if (request.brand() != null && !device.getName().equals(request.name())) {
                throw new BusinessException("Name cannot be updated when device is IN_USE");
            }

            if (request.brand() != null && !device.getBrand().equals(request.brand())) {
                throw new BusinessException("Brand cannot be updated when device is IN_USE");
            }
        }

        if (request.name() != null) {
            device.setName(request.name());
        }

        if (request.brand() != null) {
            device.setBrand(request.brand());
        }

        if (request.state() != null) {
            device.setState(request.state());
        }

        Device updated = deviceRepository.save(device);
        log.info("Device with id {} has been updated", id);

        return DeviceMapper.entityToResponse(updated);
    }

    @Transactional(readOnly = true)
    public DeviceResponse getById(String id) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(DEVICE_NOT_FOUND));
        log.info("Device with id {} has been fetched", device.getId());

        return DeviceMapper.entityToResponse(device);
    }

    @Transactional(readOnly = true)
    public List<DeviceResponse> findByFilters(String brand, DeviceState state) {
        List<Device> byFilters = deviceRepository.findByFilters(brand, state);
        return byFilters.stream().map(DeviceMapper::entityToResponse).toList();
    }

    @Transactional
    public void delete(String id) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(DEVICE_NOT_FOUND));

        if (device.getState() == DeviceState.IN_USE) {
            throw new BusinessException("Device with id " + device.getId() + " cannot be deleted because IN_USE");
        }

        deviceRepository.delete(device);
        log.info("Device with id {} has been deleted", id);
    }
}
