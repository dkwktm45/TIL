package com.example.book.repository;

import com.example.book.domain.Book;
import com.example.book.domain.BookReviewInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
class BookReviewInfoRepositoryTest {
    @Autowired
    BookReviewInfoRepository bookReviewInfoRepository;

    @Autowired
    BookRepository bookRepository;

    @Test
    void crudTest(){
        BookReviewInfo bookReviewInfo = new BookReviewInfo();
        //bookReviewInfo.setBookId(1L);
        bookReviewInfo.setBook(givenBook());
        bookReviewInfo.setAverageReviewScore(4.5f);
        bookReviewInfo.setReviewCount(2);

        bookReviewInfoRepository.save(bookReviewInfo);
        System.out.println(">>> " + bookReviewInfoRepository.findAll());
    }

    @Test
    void crudTest2(){
        givenBook();
        givenBookReviewInfo();

        Book result = bookReviewInfoRepository
                .findById(1L)
                .orElseThrow(RuntimeException::new)
                .getBook();

        System.out.println(">>> " + result);

        BookReviewInfo result2 = bookRepository
                .findById(7L)
                .orElseThrow(RuntimeException::new)
                .getBookReviewInfo();
        System.out.println(">>> " + result2);
    }
    private Book givenBook(){
        Book book = new Book();
        book.setName("jpa 강의");
        book.setAuthorId(1L);
        //book.setPublisherId(1L);
        System.out.println(">>> " + bookRepository.findAll());

        return bookRepository.save(book);
    }
    private void givenBookReviewInfo(){
        BookReviewInfo bookReviewInfo = new BookReviewInfo();
        bookReviewInfo.setBook(givenBook());
        bookReviewInfo.setAverageReviewScore(4.5f);
        bookReviewInfo.setReviewCount(2);

        bookReviewInfoRepository.save(bookReviewInfo);

        System.out.println(">>> " + bookReviewInfoRepository.findAll());
    }
}