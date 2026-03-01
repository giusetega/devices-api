package com.giuseppe.devices.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String name;

    @Column
    private String brand;

    @Enumerated(EnumType.STRING)
    @Column
    private DeviceState state;

    @Column(updatable = false)
    private LocalDateTime creationTime;

    public Device(String name, String brand, DeviceState state, LocalDateTime creationTime) {
        this.name = name;
        this.brand = brand;
        this.state = state;
        this.creationTime = creationTime;
    }

}
