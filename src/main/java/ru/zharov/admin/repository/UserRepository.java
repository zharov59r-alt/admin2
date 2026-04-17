package ru.zharov.admin.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.zharov.admin.dto.user.UserAllResponse;
import ru.zharov.admin.dto.user.UserAllResponse2;
import ru.zharov.admin.entity.User;
import ru.zharov.admin.exception.NotFoundException;
import ru.zharov.admin.exception.ValidationException;

import java.lang.annotation.Native;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    default User findByIdOrThrow(Long userId) {
        if (userId == null)
            throw new ValidationException("ID пользователя не задан");
        return findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID: " + userId + " не найден"));
    }

    @Query("""
            select new ru.zharov.admin.dto.user.UserAllResponse(
                    u.id,
                    u.email,
                    u.firstName,
                    u.lastName,
                    u.middleName,
                    u.creationDate
                        )
            from   User u 
            where  fts(u.searchTextVector, :searchText) 
            and    (       :roleIds is null 
                    or     :roleIds is not null 
                    and    exists ( 
                        select 1 
                        from   UserRoleLink url 
                        where  url.user = u 
                        and    url.role.id in :roleIds))
    """)
    List<UserAllResponse> findAllByNameAndRole(@Param("searchText") String searchText,
                                               @Param("roleIds") List<Long> roleIds,
                                                Pageable pageable);

    @Query("select u.id from User u where u.email = :email")
    Optional<Long> findByEmail(@Param("email") String email);

    /*
    данный подход делает join и выводит все поля в плоской таблицу, таким образом данные user дублируются
    @EntityGraph(attributePaths = {"userRoleLinks", "userRoleLinks.role"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT u FROM User u WHERE u.id = :userId")
    Optional<User> findByUserId(@Param("userId") Long userId);
    */

    @NativeQuery("""
            select u.user_id,
                    u.user_email,
                    u.user_first_name,
                    u.user_last_name,
                    u.user_middle_name,
                    u.creation_date,
                    (select jsonb_agg(
                        jsonb_build_object(
                            'role_id', r.role_id,
                            'role_name', r.role_name
                            )
                        )::text
                    from   admin.user_role_link url 
                    join    admin.role r on r.role_id = url.role_id
                    where  url.user_id = u.user_id 
                    ) roles
            from   admin.user u
            where  (    :searchText = 'null' 
                    or  :searchText != 'null' 
                    AND u.search_text_vector @@ plainto_tsquery(:searchText)
                    )
            and    (       :roleIds is null 
                    or     :roleIds is not null 
                    and    exists ( 
                        select 
                        from   admin.user_role_link url 
                        where  url.user_id = u.user_id 
                        and    url.role_id = any(cast(:roleIds as bigint[]))
                        )
                    )
    """)
    List<UserAllResponse2> findAllByNameAndRoleNative(@Param("searchText") String searchText,
                                                      @Param("roleIds") String roleIds);


}
