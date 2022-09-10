package com.example.stomp.repository;

import org.springframework.data.jpa.repository.JpaRepository;


public interface AlarmUserRepo extends JpaRepository<com.example.stomp.dto.AlarmUser, Long> {
}
