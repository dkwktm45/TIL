package com.sp.fc.user.repository;

import com.sp.fc.user.domain.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SchoolRepostiory extends JpaRepository<School, Long> {

    @Query("select distinct(city) from School")
    List<String> getCities();

    List<School> findAllByCity(String city);
}
