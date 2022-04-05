package com.example.book.domain;

import com.example.book.domain.converter.BookStatusConverter;
import com.example.book.repository.dt.BookStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Data
@DynamicUpdate
@Where(clause = "deleted = false")
public class Book extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 숫자가 증가
    private Long id;

    private String category;

    //private Long publisherId;

    private String name;

    private Long authorId;

    @OneToOne(mappedBy = "book")
    @ToString.Exclude
    private BookReviewInfo bookReviewInfo;

    @OneToMany
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    private List<Review> reviewList = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.REMOVE,CascadeType.PERSIST,CascadeType.MERGE})
    @ToString.Exclude
    private Publisher publisher;

//    @ManyToMany

    @OneToMany
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    private List<BookAndAuthor> bookAndAuthors = new ArrayList<>();

    public void addBookAndAuthors(BookAndAuthor... bookAndAuthors){
        Collections.addAll(this.bookAndAuthors,bookAndAuthors);
    }

    // 일종의 삭제 Flag
    private boolean deleted;

    //@Convert(converter = BookStatusConverter.class)
    private BookStatus status; // 판매상태


}
