package com.example.book.repository;

import com.example.book.domain.Address;
import com.example.book.domain.Gender;
import com.example.book.domain.User;
import com.example.book.domain.UserHistory;
import org.springframework.data.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void crud(){

        userRepository.save(new User("david","david@naver.com"));

        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user.setEmail("martin-update@fastcampus.com");

        userRepository.save(user);

    }

    @Test
    void select(){

    }

    @Test
    void pagingAndSortingTest(){
        System.out.println("findByName withPage : " + userRepository.findByName("martin",PageRequest.of(1,1,Sort.by(Sort.Order.desc("id")))).getTotalElements());

    }

    @Test
    void enumTest(){
        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user.setGender(Gender.MALE);
        userRepository.save(user);

        userRepository.findAll().forEach(System.out::println);
        System.out.println(userRepository.findRaqRecord().get("gender"));
    }
    @Test
    void insertAndUpdateTest(){
        User user = new User();

        user.setName("martin");
        user.setEmail("martin@naver.com");
        userRepository.save(user);

        User user2 = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user2.setName("marrrrrrrrtin");
        userRepository.save(user2);
    }
    private Sort getSort(){
        return Sort.by(
                Sort.Order.desc("id")
        );
    }

    @Test
    void listener(){
        User user = new User();
        user.setEmail("dkwktm45@naver.com");
        user.setName("dkwktm45");

        userRepository.save(user);

        User user2 = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user2.setName("ddddddkwktm45");
        userRepository.save(user2);

        userRepository.deleteById(4L);
    }

    @Test
    void prePersistTest(){
        User user = new User();
        user.setEmail("dkwktm45@naver.com");
        user.setName("dkwktm45");
        //user.setCreateAt(LocalDateTime.now());
        //user.setUpdateAt(LocalDateTime.now());
        userRepository.save(user);

        User user2 = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user2.setName("ddddddkwktm45");
        userRepository.save(user2);
        System.out.println(userRepository.findByEmail("dkwktm45@naver.com"));
    }
    @Test
    void preupdateTest(){
        User user2 = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        System.out.println("as-is : " + user2);
        user2.setName("martin2");
        userRepository.save(user2);
        System.out.println("to-be : " + userRepository.findAll().get(0));
    }
    @Autowired
    UserHistoryRepository userHistoryRepository;
    @Autowired
    EntityManager entityManager;
    @Test
    void userHistoryTest(){
        try{
            User user =new User();
            user.setEmail("martin@naver.com");
            user.setName("martin");
            userRepository.save(user);
            user.setName("marrtin");

            userRepository.save(user);
            userHistoryRepository.findAll().forEach(System.out::println);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    @Test
    void userRelationtest(){
        User user = new User();

        user.setName("david");
        user.setEmail("david@naver.com");
        user.setGender(Gender.MALE);
        userRepository.save(user);

        user.setName("deniel");
        userRepository.save(user);

        user.setEmail("deniel@naver.com");
        userRepository.save(user);
        userHistoryRepository.findAll().forEach(System.out::println);

//        List<UserHistory> result = userHistoryRepository.findByUserId(
//                userRepository.findByEmail("deniel@naver.com")
//                        .getId()
//        );

        List<UserHistory> result = userRepository
                .findByEmail("deniel@naver.com")
                .getUserHistories();
        result.forEach(System.out::println);

        System.out.println("UserHistory.getUser() : "+ userHistoryRepository.findAll().get(0).getUser());
    }

    @Test
    void embedTest(){
        userRepository.findAll().forEach(System.out::println);

        User user = new User();
        user.setName("JIN");
        // address도 하나의 객체이므로 어렵게 생각하지 말고 생성자를 넣고 사용하자
        user.setHomeAddress(new Address("서울시","강남구","강남대로 364","12345"));
        user.setCompanyAddress(new Address("서울시","성동구","성수로 364","67845"));

        userRepository.save(user);

        User user1 = new User();
        user1.setName("josha");
        user1.setCompanyAddress(null);
        user1.setHomeAddress(null);

        userRepository.save(user1);

        User user2 = new User();
        user2.setName("JinYoung");
        user2.setCompanyAddress(new Address());
        user2.setHomeAddress(new Address());

        userRepository.save(user2);

        //entityManager.clear();

        userRepository.findAll().forEach(System.out::println);

        //userHistoryRepository.findAll().forEach(System.out::println);

        userRepository.findAllRowRecord().forEach(a-> System.out.println(a.values()));

        assertAll(
                () -> assertThat(userRepository.findById(7L).get().getHomeAddress()).isNull(),
                () -> assertThat(userRepository.findById(8L).get().getHomeAddress()).isInstanceOf(Address.class)
        );

    }
}