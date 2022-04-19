package com.example.book.repository;

import com.example.book.domain.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @Transactional
    void reviewTest(){
        // query 방법
        //List<Review> reviewList = reviewRepository.findAllByFetchJoin();

        // entitygraph
        //List<Review> reviewList = reviewRepository.findAllByEntityGraph();

        // entitygraph2
        List<Review> reviewList = reviewRepository.findAll();

        //System.out.println(reviewList);

        //System.out.println("전체를 가져왔습니다.");
        //System.out.println(reviewList.get(0).getComments());

        //System.out.println("첫번째 리뷰의 코멘트를 가져왔습니다.");

        //System.out.println(reviewList.get(1).getComments());

        //System.out.println("두번째 리뷰의 코멘트를 가져왔습니다.");

        reviewList.forEach(System.out::println);
    }


}
