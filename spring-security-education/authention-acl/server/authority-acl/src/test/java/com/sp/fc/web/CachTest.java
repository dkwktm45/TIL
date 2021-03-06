package com.sp.fc.web;

import com.sp.fc.web.paper.Paper;
import com.sp.fc.web.paper.PaperRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = AuthorityACLApplication.class)
public class CachTest {

    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private CacheManager cacheManager;

    Optional<Paper> getPaper(Long id){
        // cache 매니저로 부터 papers 라는 이름에서 id에 해당하는 객체를 리턴해준다.
        return Optional.ofNullable(cacheManager.getCache("papers").get(id,Paper.class));
    }

    @DisplayName("1. 조회한 Paper 는 캐시에 등록된다.")
    @Test
    void test_(){
        Paper paper1 = Paper.builder().id(1L).title("paper1").build();
        paperRepository.save(paper1);


        paperRepository.findById(1L);
        assertTrue(getPaper(1L).isPresent());
        System.out.println("캐시 : "+ getPaper(1L).isPresent());
        System.out.println("성공 : " + paperRepository.findById(1L));
    }

}
