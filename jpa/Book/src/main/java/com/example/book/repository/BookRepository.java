package com.example.book.repository;

import com.example.book.domain.Book;
import com.example.book.repository.dt.BookNameAndCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface BookRepository extends JpaRepository<Book,Long> {

    @Modifying
    @Query(value = "update book set category='none'",nativeQuery = true)
    void update();



    List<Book> findByCategoryIsNullAndDeletedFalse();

    List<Book> findByCategoryIsNullAndNameEqualsAndCreatedAtGreaterThanEqualAndUpdatedAtGreaterThanEqual(String name, LocalDateTime createdAt, LocalDateTime updatedAt);

    @Query(value = "select b from Book b" +
             " where name = :name and createdAt >= :createdAt and updatedAt >= :updatedAt and category is null")
    List<Book> findByNameRecent(
            @Param("name") String name
            ,@Param("createdAt")LocalDateTime createdAt
            ,@Param("updatedAt")LocalDateTime updatedAt);

    @Query(value = "select new com.example.book.repository.dt.BookNameAndCategory( b.name, b.category )" +
            " from Book b ")
    List<BookNameAndCategory> findBookNameAndCategory();


    @Query(value = "select new com.example.book.repository.dt.BookNameAndCategory( b.name, b.category )" +
            " from Book b ")
    Page<BookNameAndCategory> findBookNameAndCategory(Pageable pageable);


    @Query(value = "select * from book",nativeQuery = true)
    List<Book> findAllCustom();


    @Transactional
    @Modifying
    @Query(value = "update book set category = 'IT전문서'",nativeQuery = true)
    int updateCategories();

    @Query(value = "select * from book order by id desc limit 1",nativeQuery = true)
    Map<String,Object> findRawRecored();

}
