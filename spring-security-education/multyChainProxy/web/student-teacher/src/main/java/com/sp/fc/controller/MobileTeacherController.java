package com.sp.fc.controller;

import com.sp.fc.student.Student;
import com.sp.fc.student.StudentManager;
import com.sp.fc.teacher.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/teacher",produces = "application/json; charset=UTF8")
public class MobileTeacherController {
    @Autowired
    StudentManager studentManager;

    // @AuthenticationPrincipal 로 teacher로 로그인 된 사람만 온다.
    @PreAuthorize("hasAnyAuthority('ROLE_TEACHER')")
    @GetMapping(value = "/students",consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Student> students(@AuthenticationPrincipal Teacher teacher , Model model){
        // 학생 리스트를 넘겨준다.
        return studentManager.myStudentList(teacher.getId());
    }

}
