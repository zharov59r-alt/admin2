package ru.zharov.admin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.zharov.admin.dto.role.RoleDto;
import ru.zharov.admin.dto.role.RoleResponse;
import ru.zharov.admin.dto.user.CreateUserRequest;
import ru.zharov.admin.dto.user.UpdateUserRequest;
import ru.zharov.admin.dto.user.UserResponse;
import ru.zharov.admin.entity.User;
import java.time.LocalDateTime;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserResponse toUserResponse(User user, List<RoleDto> roles);

    @Mapping(target = "creationDate", expression = "java(LocalDateTime.now())")
    User toUser(CreateUserRequest createUserRequest);

    default User toUser(User user, UpdateUserRequest updateUserRequest) {
        user.setEmail(updateUserRequest.getEmail());
        user.setPassword(updateUserRequest.getPassword());
        user.setFirstName(updateUserRequest.getFirstName());
        user.setLastName(updateUserRequest.getLastName());
        user.setMiddleName(updateUserRequest.getMiddleName());
        return user;
    };
}
