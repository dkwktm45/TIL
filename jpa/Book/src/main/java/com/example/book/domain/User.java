package com.example.book.domain;

import com.example.book.domain.listener.UserEntityListener;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor // 생성자 생성
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
//@Table(name = "user",indexes={@Index(columnList = "name")},uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@EntityListeners(value = {UserEntityListener.class})
public class User extends BaseEntity{
    @Id // pk설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 숫자가 증가
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city",column = @Column(name = "home_city")),
            @AttributeOverride(name = "district",column = @Column(name = "home_district")),
            @AttributeOverride(name = "detail",column = @Column(name = "home_address_detail")),
            @AttributeOverride(name = "zipCode",column = @Column(name = "home_zip_code"))
    })
    private Address homeAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city",column = @Column(name = "company_city")),
            @AttributeOverride(name = "district",column = @Column(name = "company_district")),
            @AttributeOverride(name = "detail",column = @Column(name = "company_address_detail")),
            @AttributeOverride(name = "zipCode",column = @Column(name = "company_zip_code"))
    })
    private Address companyAddress;


    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false,updatable = false)
    @ToString.Exclude
    private List<UserHistory> userHistories = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private List<Review> reviewList = new ArrayList<>();

}
