package com.sp.fc.controller;

import com.sp.fc.student.StudentManager;
import com.sp.fc.teacher.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    StudentManager studentManager;

    // @AuthenticationPrincipal 로 teacher로 로그인 된 사람만 온다.
    @PreAuthorize("hasAnyAuthority('ROLE_TEACHER')")
    @GetMapping("/main")
    public String main(@AuthenticationPrincipal Teacher teacher , Model model){
        // 학생 리스트를 넘겨준다.
        model.addAttribute("studentList",studentManager.myStudentList(teacher.getId()));
        return "TeacherMain";
    }


}
