package com.giuseppe.devices.service;

import com.giuseppe.devices.domain.Device;
import com.giuseppe.devices.domain.DeviceState;
import com.giuseppe.devices.dto.CreateDeviceRequest;
import com.giuseppe.devices.dto.DeviceResponse;
import com.giuseppe.devices.dto.UpdateDeviceRequest;
import com.giuseppe.devices.exception.BusinessException;
import com.giuseppe.devices.exception.ResourceNotFoundException;
import com.giuseppe.devices.repository.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceService deviceService;

    @Test
    void createDeviceShouldSaveDevice() {
        CreateDeviceRequest request = new CreateDeviceRequest("Galaxy S23", "Samsung", DeviceState.INACTIVE);

        Device device = new Device();
        device.setId("123");
        device.setName("Galaxy S23");
        device.setBrand("Samsung");
        device.setState(DeviceState.INACTIVE);

        when(deviceRepository.save(any(Device.class))).thenReturn(device);

        DeviceResponse response = deviceService.createDevice(request);

        assertEquals("123", response.id());
        assertEquals("Galaxy S23", response.name());

        verify(deviceRepository).save(any(Device.class));
    }

    @Test
    void updateDeviceShouldThrowResourceNotFoundExceptionWhenDeviceIsNotFound() {
        when(deviceRepository.findById("1")).thenReturn(Optional.empty());

        CreateDeviceRequest request = new CreateDeviceRequest("Galaxy S23", "Samsung", DeviceState.INACTIVE);

        assertThrows(ResourceNotFoundException.class, () -> deviceService.updateDevice("1", request));
    }

    @Test
    void updateDeviceShouldThrowBusinessExceptionWhenChangingNameAndDeviceIsInUse() {
        Device device = new Device();
        device.setId("1");
        device.setName("Samsung");
        device.setBrand("Samsung");
        device.setState(DeviceState.IN_USE);

        when(deviceRepository.findById("1")).thenReturn(Optional.of(device));

        CreateDeviceRequest request = new CreateDeviceRequest("NewName", "Samsung", DeviceState.IN_USE);

        assertThrows(BusinessException.class, () -> deviceService.updateDevice("1", request));
    }

    @Test
    void updateDeviceShouldUpdateDeviceSuccessfully(){
        Device device = new Device();
        device.setId("1");
        device.setName("Galaxy S23");
        device.setBrand("Samsung");
        device.setState(DeviceState.AVAILABLE);

        when(deviceRepository.findById("1")).thenReturn(Optional.of(device));
        when(deviceRepository.save(any(Device.class))).thenReturn(device);

        CreateDeviceRequest request = new CreateDeviceRequest("NewName", "NewBrand", DeviceState.AVAILABLE);

        DeviceResponse deviceResponse = deviceService.updateDevice("1", request);

        assertEquals("NewName", deviceResponse.name());
        assertEquals("NewBrand", deviceResponse.brand());
        assertEquals(DeviceState.AVAILABLE, deviceResponse.state());

        verify(deviceRepository).save(any(Device.class));
    }

    @Test
    void partialUpdateDeviceDeviceShouldAllowStateChangeWhenInUse() {
        Device device = new Device();
        device.setId("1");
        device.setName("Samsung");
        device.setBrand("Samsung");
        device.setState(DeviceState.IN_USE);

        when(deviceRepository.findById("1")).thenReturn(Optional.of(device));
        when(deviceRepository.save(any(Device.class))).thenReturn(device);

        UpdateDeviceRequest request = new UpdateDeviceRequest(null, null, DeviceState.INACTIVE);

        DeviceResponse response = deviceService.partialUpdateDevice("1", request);

        assertEquals(DeviceState.INACTIVE, device.getState());
    }

    @Test
    void partialUpdateDeviceDeviceShouldThrowWhenChangingBrandWhileInUse() {
        Device device = new Device();
        device.setId("1");
        device.setName("Samsung");
        device.setBrand("Samsung");
        device.setState(DeviceState.IN_USE);

        when(deviceRepository.findById("1")).thenReturn(Optional.of(device));

        UpdateDeviceRequest request = new UpdateDeviceRequest(null, "Apple", null);

        assertThrows(BusinessException.class, () -> deviceService.partialUpdateDevice("1", request));
    }

    @Test
    void getDeviceShouldReturnDeviceResponse() {
        Device device = new Device();
        device.setId("1");
        device.setName("Samsung");
        device.setBrand("Samsung");
        device.setState(DeviceState.IN_USE);

        when(deviceRepository.findById("1")).thenReturn(Optional.of(device));

        DeviceResponse deviceResponse = deviceService.getDevice("1");

        assertEquals(device.getId(), deviceResponse.id());
        assertEquals(device.getName(), deviceResponse.name());
        assertEquals(device.getBrand(), deviceResponse.brand());
        assertEquals(device.getState(), deviceResponse.state());

        verify(deviceRepository).findById(any());
    }

    @Test
    void listDevicesShouldReturnADeviceList() {
        List<Device> devices = List.of(
                new Device("1", "Samsung S22", "Samsung", DeviceState.IN_USE, LocalDateTime.now()),
                new Device("2", "iPhone 14", "Apple", DeviceState.INACTIVE, LocalDateTime.now()),
                new Device("3", "Pixel 8", "Google", DeviceState.INACTIVE, LocalDateTime.now())
        );

        when(deviceRepository.findByFilters(null, null)).thenReturn(devices);

        List<DeviceResponse> result = deviceService.listDevices(null, null);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Samsung S22", result.getFirst().name());
        assertEquals(DeviceState.IN_USE, result.getFirst().state());

        verify(deviceRepository).findByFilters(null, null);
    }

    @Test
    void deleteDeviceShouldThrowBusinessExceptionWhenDeviceIsInUse() {
        Device device = new Device();
        device.setId("1");
        device.setName("Samsung");
        device.setBrand("Samsung");
        device.setState(DeviceState.IN_USE);

        when(deviceRepository.findById("1")).thenReturn(Optional.of(device));

        assertThrows(BusinessException.class, () -> deviceService.deleteDevice("1"));

        verify(deviceRepository, never()).delete(any(Device.class));
    }

    @Test
    void deleteDeviceShouldCompleteSuccessfullyWhenDeviceIsNotInUse() {
        Device device = new Device();
        device.setId("1");
        device.setName("Samsung");
        device.setBrand("Samsung");
        device.setState(DeviceState.INACTIVE);

        when(deviceRepository.findById("1")).thenReturn(Optional.of(device));

        deviceService.deleteDevice("1");

        verify(deviceRepository).delete(any(Device.class));
    }

    private Device availableDevice() {
        Device device = new Device();
        device.setId("1");
        device.setName("Galaxy S23");
        device.setBrand("Samsung");
        device.setState(DeviceState.AVAILABLE);
        device.setCreationTime(LocalDateTime.now());
        return device;
    }

    private Device inUseDevice() {
        Device device = new Device();
        device.setId("1");
        device.setName("Samsung");
        device.setBrand("Samsung");
        device.setState(DeviceState.IN_USE);
        device.setCreationTime(LocalDateTime.now());
        return device;
    }


}