package ru.zharov.admin.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.zharov.admin.dto.role.RoleDto;
import ru.zharov.admin.dto.role.RoleResponse;
import ru.zharov.admin.entity.Role;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDateTime creationDate;
    private List<RoleDto> roles;


}