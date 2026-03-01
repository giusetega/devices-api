package com.giuseppe.devices.mapper;

import com.giuseppe.devices.domain.Device;
import com.giuseppe.devices.dto.CreateDeviceRequest;
import com.giuseppe.devices.dto.DeviceResponse;

import java.time.LocalDateTime;

public class DeviceMapper {

    private DeviceMapper() {
    }

    public static Device requestToEntity(CreateDeviceRequest request) {
        return new Device(
                request.name(),
                request.brand(),
                request.state(),
                LocalDateTime.now()
        );
    }

    public static DeviceResponse entityToResponse(Device device) {
        return new DeviceResponse(
                device.getId(),
                device.getName(),
                device.getBrand(),
                device.getState(),
                device.getCreationTime()
        );
    }
}
