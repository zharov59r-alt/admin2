package ru.zharov.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.zharov.admin.entity.User;
import ru.zharov.admin.entity.UserRoleLink;

import java.util.List;


public interface UserRoleLinkRepository extends JpaRepository<UserRoleLink,Long> {

    @Query("select url from UserRoleLink url where url.user = :user")
    List<UserRoleLink> findByUser(@Param("user") User user);

    @Modifying
    @Query("delete from UserRoleLink url where url.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

}
