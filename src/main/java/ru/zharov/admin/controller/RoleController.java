package ru.zharov.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.zharov.admin.dto.role.RoleDto;
import ru.zharov.admin.dto.role.RoleResponse;
import ru.zharov.admin.dto.user.UserAllRequest;
import ru.zharov.admin.dto.user.UserAllResponse;
import ru.zharov.admin.dto.user.UserResponse;
import ru.zharov.admin.service.RoleService;
import ru.zharov.admin.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public List<RoleDto> findAll() {
        return roleService.findAll();
    }


}
