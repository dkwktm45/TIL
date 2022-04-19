package com.example.book.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BookReviewInfo extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 숫자가 증가
    private Long id;

    //private Long bookId;

    // @OneToOne --> 1 : 1로 연관관계를 맺겠다!
    @OneToOne(optional = false)
    private Book book;
    private float averageReviewScore;

    private int reviewCount;
}
