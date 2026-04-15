package ru.zharov.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.zharov.admin.dto.role.RoleDto;
import ru.zharov.admin.dto.role.RoleResponse;
import ru.zharov.admin.entity.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {

    @Query("""
            SELECT r
            FROM UserRoleLink url
            join Role r on r.id = url.role.id
            WHERE url.user.id = :userId
        """
    )
    List<Role> findAllByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT r
            FROM Role r
            WHERE r.id in :roleIds
        """
    )
    List<RoleDto> findAllByRoleIds(@Param("roleIds") List<Long> roleIds);

}
