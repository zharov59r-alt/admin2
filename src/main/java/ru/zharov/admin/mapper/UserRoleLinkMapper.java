package ru.zharov.admin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.zharov.admin.entity.Role;
import ru.zharov.admin.entity.User;
import ru.zharov.admin.entity.UserRoleLink;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserRoleLinkMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "role", source = "role")
    UserRoleLink toUserRoleLink(User user, Role role);

    default List<UserRoleLink> toUserRoleLinkList(User user, List<Role> roles) {
        if (roles == null) {
            return Collections.emptyList();
        }
        return roles.stream()
                .map(role -> toUserRoleLink(user, role))
                .toList();
    }

}
