package ru.zharov.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.zharov.admin.dto.user.*;
import ru.zharov.admin.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/all")
    public List<UserAllResponse> findAll(@RequestBody(required = false) UserAllRequest request) {
        return userService.findAllByNameAndRole(request);
    }

    @PostMapping("/allnative")
    public List<UserAllResponse2> findAllNative(@RequestBody(required = false) UserAllRequest request) {
        return userService.findAllByNameAndRoleNative(request);
    }

    @GetMapping("/{userId}")
    public UserResponse findById(@PathVariable Long userId) {
        return userService.findById(userId);
    }

    @PostMapping
    public UserResponse save(@Valid @RequestBody CreateUserRequest request) {
        return userService.save(request);
    }

    @PutMapping
    public UserResponse update(@Valid @RequestBody UpdateUserRequest request) {
        return userService.update(request);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable("userId") Long userId) {
        userService.delete(userId);
    }

}
