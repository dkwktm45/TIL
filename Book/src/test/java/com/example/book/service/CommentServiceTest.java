package com.example.book.service;

import com.example.book.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

@SpringBootTest
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    void commentTest(){
        // 10개의 값을 넣는 메서드
        commentService.init();

        //commentRepository.findAll().forEach(System.out::println);

        // 13개의 comment의 값을 수정
        commentService.updateSomething();

        // 별도의 값을 하나 넣는 메서드
        System.out.println("별도의 값을 하나 넣는 메서드");
        commentService.insertSomething();
    }



}
