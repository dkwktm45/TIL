package com.example.book.repository;

import com.example.book.domain.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Test
    void commentTest(){
        Comment comment = new Comment();
        comment.setComment("별로..ㅎ");
        //comment.setCommentedAt(LocalDateTime.now());

        commentRepository.saveAndFlush(comment);

        entityManager.clear();

        //commentRepository.findAll().forEach(System.out::println);

        //System.out.println(commentRepository.findById(3L).get());

        System.out.println(comment);

        commentRepository.findAll().forEach(System.out::println);
    }
}