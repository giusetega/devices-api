package com.giuseppe.devices.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giuseppe.devices.domain.DeviceState;
import com.giuseppe.devices.dto.CreateDeviceRequest;
import com.giuseppe.devices.dto.DeviceResponse;
import com.giuseppe.devices.service.DeviceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeviceController.class)
class DeviceControllerTest {

    private static final String BASE_PATH = "/api/v1/devices";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DeviceService deviceService;


    @Test
    void createDeviceShouldReturnCreated() throws Exception {
        CreateDeviceRequest request = new CreateDeviceRequest("Galaxy S23", "Samsung", DeviceState.INACTIVE);

        DeviceResponse response = new DeviceResponse("1", "Galaxy S23", "Samsung", DeviceState.INACTIVE, LocalDateTime.now());

        when(deviceService.createDevice(any())).thenReturn(response);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Galaxy S23"));
    }

    @Test
    void listDevicesShouldReturnList() throws Exception {
        List<DeviceResponse> responses = List.of(
                new DeviceResponse("1", "Galaxy S23", "Samsung", DeviceState.IN_USE, LocalDateTime.now()),
                new DeviceResponse("2", "iPhone 5", "Apple", DeviceState.INACTIVE, LocalDateTime.now())
        );

        when(deviceService.listDevices(null, null)).thenReturn(responses);

        mockMvc.perform(get(BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void deleteDeviceShouldSucceed() throws Exception {
        doNothing().when(deviceService).deleteDevice("1");

        mockMvc.perform(delete(BASE_PATH + "/1"))
                .andExpect(status().isNoContent());

        verify(deviceService).deleteDevice("1");
    }
}