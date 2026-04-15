package ru.zharov.admin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.zharov.admin.dto.role.RoleDto;
import ru.zharov.admin.dto.role.RoleResponse;
import ru.zharov.admin.dto.user.UserResponse;
import ru.zharov.admin.entity.Role;
import ru.zharov.admin.entity.User;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {
    RoleDto toRoleDto(Role role);
    List<RoleDto> toRoleDtoList(List<Role> role);

    Role toRole(RoleDto role);
    List<Role> toRoleList(List<RoleDto> role);

}
