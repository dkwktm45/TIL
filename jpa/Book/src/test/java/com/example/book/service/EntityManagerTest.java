package com.example.book.service;

import com.example.book.domain.User;
import com.example.book.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class EntityManagerTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;
    @Test
    void entityManagerTest(){
        System.out.println(entityManager.
                createQuery("select u from User u").
                getResultList());
    }
    @Test
    void cashFindTest(){
//        System.out.println(userRepository.findByEmail("martin@fastcampus.com"));
//        System.out.println(userRepository.findByEmail("martin@fastcampus.com"));
//        System.out.println(userRepository.findByEmail("martin@fastcampus.com"));
//        System.out.println(userRepository.findById(2L).get());
//        System.out.println(userRepository.findById(2L).get());
//        System.out.println(userRepository.findById(2L).get());

        userRepository.deleteById(1L);
    }

    @Test
    void casheFindTest2(){
        User user = userRepository.findById(1L).get();
        user.setName("marrrtin");
        userRepository.save(user);
        System.out.println("-----------------------------------------");

        user.setEmail("marrrtin@fastcampus.com");
        userRepository.save(user);
        // jpql query
        System.out.println(userRepository.findAll());
    }
}
