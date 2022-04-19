package com.sp.fc.user.service;

import com.sp.fc.user.domain.School;
import com.sp.fc.user.repository.SchoolRepostiory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepostiory schoolRepostiory;

    public School save(School school){
        if(school.getSchoolId() == null){
            school.setCreated(LocalDateTime.now());
        }
        school.setUpdated(LocalDateTime.now());
        return schoolRepostiory.save(school);
    }

    public Optional<School> updateName(Long id, String name){
        return schoolRepostiory.findById(id).map(school -> {
            school.setName(name);
            schoolRepostiory.save(school);
            return school;
        });
    }

    public List<String> cities(){
        return schoolRepostiory.getCities();
    }

    public List<School> findAllByCity(String city) {
        return schoolRepostiory.findAllByCity(city);
    }
}
