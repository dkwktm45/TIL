package com.example.book.repository;

import com.example.book.domain.User;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

// jpa에서 지원을 해줌
public interface UserRepository extends JpaRepository<User, Long> {
    Set<User> findByName(String name);

    User findByEmail(String email);

    User getByEmail(String email);

    User readByEmail(String email);

    User searchByEmail(String email);

    User streamByEmail(String email);

    User findUserByEmail(String email);

    User findSomethingByEmail(String email);

    // 1 이라고 표시한 이유는 martin을 사용할 건데
    List<User> findFirstByName(String name);


    List<User> findByEmailAndName(String email, String name);
    List<User> findByEmailOrName(String email, String name);


    List<User> findByIdBetween(Long id,Long id2);

    List<User> findByIdGreaterThanEqualAndIdLessThanEqual(Long id, Long id2);

    List<User> findByIdIsNotNull();

//    List<User> findByAddressIsNotEmpty();

    List<User> findByNameIn(List<String> name);


    List<User> findByNameStartingWith(String name);
    List<User> findByNameEndingWith(String name);
    List<User> findByNameContains(String name);

    List<User> findByNameLike(String name);

    List<User> findTop1ByName(String name);

    List<User> findTop1ByNameOrderByIdDesc(String name);

    List<User> findFirstByNameOrderByIdDescEmailAsc(String name);

    List<User> findFirstByName(String name, Sort sort);

    Page<User> findByName(String name, Pageable page);

    @Query(value = "select * from user limit 1;",nativeQuery = true)
    Map<String , Object> findRaqRecord();

    @Query(value = "select * from user",nativeQuery = true)
    List<Map<String,Object>> findAllRowRecord();
}
