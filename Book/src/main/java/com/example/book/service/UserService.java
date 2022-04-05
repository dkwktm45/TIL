package com.example.book.service;

import com.example.book.domain.User;
import com.example.book.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void put(){
        User user = new User();
        user.setName("newUser");
        user.setEmail("newUser@naver.com");
        // 1. 첫 번째 방법
        //userRepository.save(user);

        entityManager.persist(user);
        //entityManager.detach(user);
        user.setName("newUserAfterPersist");

        entityManager.merge(user);

        User user1 = userRepository.findById(1L).get();
        entityManager.remove(user1);
        user1.setName("marrrtin");
        entityManager.merge(user1);
    }

}
