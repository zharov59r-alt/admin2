package ru.zharov.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "user", schema = "admin")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_gen")
    @SequenceGenerator(name = "user_id_gen", sequenceName = "s_user_id", schema = "admin", allocationSize = 1)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "user_email", nullable = false, length = Integer.MAX_VALUE)
    private String email;

    @Column(name = "user_password", nullable = false, length = Integer.MAX_VALUE)
    private String password;

    @Column(name = "user_first_name", nullable = false, length = Integer.MAX_VALUE)
    private String firstName;

    @Column(name = "user_last_name", nullable = false, length = Integer.MAX_VALUE)
    private String lastName;

    @Column(name = "user_middle_name", length = Integer.MAX_VALUE)
    private String middleName;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    @BatchSize(size = 10)
    private List<UserRoleLink> userRoleLinks = new ArrayList<>();

    @Column(name = "search_text_vector", columnDefinition = "text", insertable = false, updatable = false)
    private String searchTextVector;


}