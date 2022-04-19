package com.example.book.repository;

import com.example.book.domain.Book;
import com.example.book.domain.Publisher;
import com.example.book.domain.Review;
import com.example.book.domain.User;
import com.example.book.repository.dt.BookStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void queryTest(){
        bookRepository.findAll().forEach(System.out::println);
        System.out.println("findByCategoryIsNullAndNameEqualsAndCreatedAtGreaterThanEqualAndUpdatedAtGreaterThanEqual : "
        + bookRepository.findByCategoryIsNullAndNameEqualsAndCreatedAtGreaterThanEqualAndUpdatedAtGreaterThanEqual(
                "JPA", LocalDateTime.now().minusDays(1L),
                LocalDateTime.now().minusDays(1L)
        ));

        System.out.println("findByNameRecently : " + bookRepository.findByNameRecent("JPA",
                LocalDateTime.now().minusDays(1L),LocalDateTime.now().minusDays(1L)));


        bookRepository.findBookNameAndCategory().forEach(b -> {
            System.out.println(b.getName() + " : " + b.getCategory());
        });

        bookRepository.findBookNameAndCategory(PageRequest.of(1,1)).
                forEach(bookNameAndCategory ->
                        System.out.println(bookNameAndCategory.getName() + ":" + bookNameAndCategory.getCategory()));
    }

    @Test
    void nativeQueryTest(){
        bookRepository.findAll().forEach(System.out::println);

        bookRepository.findAllCustom().forEach(System.out::println);

        List<Book> books = bookRepository.findAll();
        for (Book book:books){
            book.setCategory("IT전문서");
        }
        bookRepository.saveAll(books);
        System.out.println(bookRepository.findAll());

        System.out.println(bookRepository.updateCategories());
    }

    @Test
    void converterTest(){
        bookRepository.findAll().forEach(System.out::println);

        Book book = new Book();
        book.setName("또다른 IT서적");
        book.setStatus(new BookStatus(200));

        bookRepository.save(book);

        System.out.println(bookRepository.findRawRecored().values());

        bookRepository.findAll().forEach(System.out::println);

    }


    @Test
    void bookRemoveTest(){
        bookRepository.deleteById(1L);

        System.out.println("books : " + bookRepository.findAll());
        System.out.println("publisher : " + publisherRepository.findAll());

        bookRepository.findAll()
                .forEach(book -> System.out.println(book.getPublisher()));
    }

    @Test
    void bookCascadeTest(){
        Book book = new Book();
        book.setName("JPA Cascade");

        Publisher publisher = new Publisher();
        publisher.setName("패스트캠퍼스");

        book.setPublisher(publisher);

        bookRepository.save(book);

        System.out.println("books : " + bookRepository.findAll());
        System.out.println("publisher : " + publisherRepository.findAll());

        Book book1 = bookRepository.findById(1L).get();
        book1.getPublisher().setName("슬로우캠퍼스");

        bookRepository.save(book1);

        System.out.println("publisher : " + publisherRepository.findAll());

        Book book2 = bookRepository.findById(1L).get();
        //bookRepository.delete(book2);
        Book book3 = bookRepository.findById(1L).get();
        book3.setPublisher(null);

        bookRepository.save(book3);

        System.out.println("books : " + bookRepository.findAll());
        System.out.println("publisher : " + publisherRepository.findAll());
        System.out.println("book3-publisher : "+ bookRepository.findById(1L).get().getPublisher());
    }

    @Test
    void softDelete(){
        bookRepository.findAll().forEach(System.out::println);
        System.out.println(bookRepository.findById(3L));

        bookRepository.findByCategoryIsNullAndDeletedFalse().forEach(System.out::println);
    }

    @Test
    @Transactional
    void bookRelationTest(){
        givenBookAndReview();

        User user = userRepository.findByEmail("martin@fastcampus.com");

        System.out.println("Review : " + user.getReviewList());
        System.out.println("book : "+ user.getReviewList().get(0).getBook());
        System.out.println("Publisher : " + user.getReviewList().get(0).getBook().getPublisher());
    }
    private void givenBookAndReview(){
        givenReview(givenUser(),givenBook(givenPublisher()));
    }
    private Publisher givenPublisher(){
        Publisher publisher = new Publisher();
        publisher.setName("패스트캠퍼스");
        return publisherRepository.save(publisher);
    }
    private Book givenBook(Publisher publisher){
        Book book = new Book();
        book.setName("JPA 초격차 패키지");
        book.setPublisher(publisher);
        return bookRepository.save(book);
    }
    private User givenUser(){
        return userRepository.findByEmail("martin@fastcampus.com");
    }
    private void givenReview(User user , Book book){
        Review review = new Review();
        review.setTitle("내 인생을 바꾼 책");
        review.setContent("너무너무 재미있고, 즐거운 책");
        review.setScore(5.0f);
        review.setUser(user);
        review.setBook(book);

        reviewRepository.save(review);

    }

    @Test
    void bookTest(){
        Book book = new Book();
        book.setName("jpa 패키지");
        book.setAuthorId(1L);
        //book.setPublisherId(1L);

        bookRepository.save(book);
        System.out.println(bookRepository.findAll());
    }

    @Test
    void converErrorTest(){
    }

}