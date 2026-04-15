package ru.zharov.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "role", schema = "admin")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_gen")
    @SequenceGenerator(name = "role_id_gen", sequenceName = "s_role_id", schema = "admin", allocationSize = 1)
    @Column(name = "role_id", nullable = false)
    private Long id;

    @Column(name = "role_name", nullable = false)
    private String name;

    @Column(name = "role_description")
    private String description;

    @ToString.Exclude
    @OneToMany(mappedBy = "role")
    private List<UserRoleLink> userRoleLinks = new ArrayList<>();

}