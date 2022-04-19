package com.example.book.service;

import com.example.book.domain.Author;
import com.example.book.domain.Book;
import com.example.book.repository.AuthorRepository;
import com.example.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    // final를 통해 생성자를 만들 수 있다 --> 요즘 트랜드
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final EntityManager entityManager;
    private final AuthorService authorService;

        // try-catch를 묶지 않으면 오류가 전파되지 않도록 임시 방편
//        try{
//            authorService.putAuthor();
//        }catch (RuntimeException e){
//        }

    @Transactional(propagation = Propagation.NESTED)
    void Spread() {
        Book book = new Book();
        book.setName("JPA 시작 구건");

        bookRepository.save(book);

        try{
            authorService.putAuthor();
        }catch (RuntimeException e){
        }
        throw new RuntimeException("wqd");
    }


    //@Transactional(isolation = Isolation.SERIALIZABLE)
    public void get(Long id){
        System.out.println(">>> : " + bookRepository.findById(id));
        System.out.println(">>> : " + bookRepository.findAll());
        entityManager.clear();
        System.out.println(">>> : " + bookRepository.findById(id));
        System.out.println(">>> : " + bookRepository.findAll());

        bookRepository.update();

        entityManager.clear();
        System.out.println("name을 바꾸는 구간-------------------");
        //Book book = bookRepository.findById(id).get();
        //book.setName("바뀔까?");
        //bookRepository.save(book);
        throw new RuntimeException("asdasd");
    }
    @Transactional
    public List<Book> getAll(){
        List<Book> books = bookRepository.findAll();
        books.forEach(System.out::println);

        return books;
    }

}
