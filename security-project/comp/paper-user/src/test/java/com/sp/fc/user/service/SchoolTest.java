package com.sp.fc.user.service;

import com.sp.fc.user.domain.School;
import com.sp.fc.user.repository.SchoolRepostiory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

// DataJpaTest 를 선언하게 되면 DB 데이터 소스를 inMemory 방식으로 만든다.

@DataJpaTest
public class SchoolTest {

    // 학교 이름을 수정한다.
    // 지역 목록을 가져온다.
    //

    @Autowired
    private SchoolRepostiory schoolRepostiory;

    private SchoolService schoolService;
    private SchoolTestHelper schoolTestHelper;
    School school;

    @BeforeEach
    void before(){
        this.schoolRepostiory.deleteAll();
        this.schoolService = new SchoolService(schoolRepostiory);
        this.schoolTestHelper = new SchoolTestHelper(this.schoolService);
        school = this.schoolTestHelper.createSchool("테스트 학교","서울");

    }

    // 학교를 생성한다.
    @DisplayName("1. 학교를 생성한다.")
    @Test
    void test_1(){
        List<School> list = schoolRepostiory.findAll();
        SchoolTestHelper.assertSchool(list.get(0),"테스트 학교","서울");
    }

    @DisplayName("2. 학교 이름을 수정한다.")
    @Test
    void test_2(){

        schoolService.updateName(school.getSchoolId(), "테스트2 학교");
        List<School> list = schoolRepostiory.findAll();
        SchoolTestHelper.assertSchool(list.get(0),"테스트2 학교","서울");
    }

    @DisplayName("3. 학교의 지역 이름 가져오기")
    @Test
    void test_3(){

        List<String> list = schoolService.cities();
        assertEquals(1,list.size());
        assertEquals("서울",list.get(0));

        schoolTestHelper.createSchool("부산 학교","부산");
        list = schoolService.cities();
        assertEquals(2,list.size());

    }

    @DisplayName("4. 지역으로 학교 목록을 가져온다.")
    @Test
    void test_4(){

        List<School> list = schoolService.findAllByCity("서울");
        assertEquals(1,list.size());
        schoolTestHelper.createSchool("부산2 학교","서울");
        list = schoolService.findAllByCity("서울");
        assertEquals(2,list.size());
    }
}
