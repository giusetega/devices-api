package com.giuseppe.devices.repository;

import com.giuseppe.devices.domain.Device;
import com.giuseppe.devices.domain.DeviceState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, String> {

    @Query("""
                SELECT d FROM Device d
                WHERE (:brand IS NULL OR d.brand = :brand)
                AND (:state IS NULL OR d.state = :state)
            """)
    List<Device> findByFilters(
            @Param("brand") String brand,
            @Param("state") DeviceState state
    );

}