package com.example.book.service;

import com.example.book.domain.Book;
import com.example.book.repository.AuthorRepository;
import com.example.book.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceTest {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;



    @Test
    void transactionTest(){

        try {
            bookService.Spread();
            //bookService.put();
        }catch (RuntimeException e){
            System.out.println(">>>> " + e.getMessage());

        }
        System.out.println("books : "+ bookRepository.findAll());
        System.out.println("authors : "+ authorRepository.findAll());

    }
    @Test
    void isolationTest(){
        Book book = new Book();
        book.setName("jiiiin 강의");

        bookRepository.save(book);
        try {
            bookService.get(1L);
        }catch (RuntimeException e){
            System.out.println(">>>>" + e.getMessage());
        }
        System.out.println(">>>> " + bookRepository.findAll());
    }

    @Test
    void converErrorTest(){

        bookService.getAll();
        bookRepository.findAll().forEach(System.out::println);

    }
}