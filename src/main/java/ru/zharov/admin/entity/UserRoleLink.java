package ru.zharov.admin.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "user_role_link", schema = "admin")
public class UserRoleLink {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_link_id_gen")
    @SequenceGenerator(name = "user_role_link_id_gen", sequenceName = "s_user_role_link_id", schema = "admin", allocationSize = 1)
    @Column(name = "user_role_link_id", nullable = false)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;


    public String toString() {
        return "UserRoleLink(id=" + this.getId() + ", " +
                "user=" + String.valueOf(this.getUser().getId()) + ", " +
                "role=" + String.valueOf(this.getRole().getId()) + ")";
    }
}