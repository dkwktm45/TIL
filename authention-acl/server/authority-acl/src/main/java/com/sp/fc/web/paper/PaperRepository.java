package com.sp.fc.web.paper;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaperRepository extends JpaRepository<Paper, Long> {

    // cache 는 기본적으로 AOP 를 사용하기 때문에
    // 아이디가 key 가 되는 hashmap 형태가 만들어 지면서 cache 가 만들어진다.
    // 그렇기에 내가 사용할 papers 라는 cache 를 등록을 해주어야 하며, default 로 id로 지정이 된다.
    //@Cacheable(value = "papers")
    Optional<Paper> findById(Long id);
}
