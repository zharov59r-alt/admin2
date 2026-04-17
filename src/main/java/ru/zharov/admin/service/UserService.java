package ru.zharov.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zharov.admin.dto.role.RoleDto;
import ru.zharov.admin.dto.role.RoleResponse;
import ru.zharov.admin.dto.user.*;
import ru.zharov.admin.entity.Role;
import ru.zharov.admin.entity.User;
import ru.zharov.admin.entity.UserRoleLink;
import ru.zharov.admin.exception.NotFoundException;
import ru.zharov.admin.mapper.RoleMapper;
import ru.zharov.admin.mapper.UserMapper;
import ru.zharov.admin.mapper.UserRoleLinkMapper;
import ru.zharov.admin.repository.RoleRepository;
import ru.zharov.admin.repository.UserRepository;
import ru.zharov.admin.repository.UserRoleLinkRepository;
import ru.zharov.admin.utils.RoleUtils;
import ru.zharov.admin.utils.UserRoleLinkUtils;
import ru.zharov.admin.validation.UserValidation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleLinkRepository userRoleLinkRepository;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final UserRoleLinkMapper userRoleLinkMapper;
    private final RoleUtils roleUtils;
    private final UserValidation userValidation;
    private final UserRoleLinkUtils userRoleLinkUtils;


    @Transactional(readOnly = true)
    public List<UserAllResponse> findAllByNameAndRole(UserAllRequest request) {
        log.debug("findAllByNameAndRole");
        return userRepository.findAllByNameAndRole(
                request.getSearchText(),
                request.getRoles(),
                PageRequest.of(request.getPageNumber(), request.getPageSize(), Sort.by("id")));
    }

    @Transactional(readOnly = true)
    public List<UserAllResponse2> findAllByNameAndRoleNative(UserAllRequest request) {

        String roles = null;
        if (request.getRoles() != null)
            roles = request.getRoles().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",", "{", "}"));

        return userRepository.findAllByNameAndRoleNative(
                request.getSearchText(),
                roles
        );
    }

    @Transactional(readOnly = true)
    public UserResponse findById(Long userId) {

        User user = userRepository.findByIdOrThrow(userId);

        List<RoleDto> roles = roleMapper.toRoleDtoList(
                roleRepository.findAllByUserId(userId)
        );

        return userMapper.toUserResponse(user, roles);
    }

    public UserResponse save(CreateUserRequest request) {
        log.debug("save begin");
        List<Role> roles = roleUtils.findAndCheckRoles(request.getRoles());

        User user = userMapper.toUser(request);
        userValidation.check(user);
        userRepository.save(user);

        if (!roles.isEmpty()) {
            userRoleLinkRepository.saveAll(
                userRoleLinkMapper.toUserRoleLinkList(user, roles)
            );
        }

        return userMapper.toUserResponse(user, roleMapper.toRoleDtoList(roles));
    }

    public UserResponse update(UpdateUserRequest request) {

        log.debug("update begin");

        User user = userRepository.findByIdOrThrow(request.getId());
        user = userMapper.toUser(user, request);

        userValidation.check(user);

        List<Role> roles = roleUtils.findAndCheckRoles(request.getRoles());

        userRepository.save(user);
        userRoleLinkUtils.merge(user, roles);

        return userMapper.toUserResponse(user, roleMapper.toRoleDtoList(roles));

    }

    public void delete(Long userId) {

        log.debug("delete begin");

        if (!userRepository.existsById(userId))
            throw new NotFoundException("Пользователь с ID: " + userId + " не найден");

        userRoleLinkRepository.deleteByUserId(userId);
        userRepository.deleteById(userId);

    }

}
