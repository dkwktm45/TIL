package com.example.stomp.repository;

import com.example.stomp.dto.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepo extends JpaRepository<Alarm, Long> {

}
