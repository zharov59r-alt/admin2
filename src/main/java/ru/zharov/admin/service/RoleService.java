package ru.zharov.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zharov.admin.dto.role.RoleDto;
import ru.zharov.admin.dto.role.RoleResponse;
import ru.zharov.admin.dto.user.UserAllRequest;
import ru.zharov.admin.dto.user.UserAllResponse;
import ru.zharov.admin.dto.user.UserResponse;
import ru.zharov.admin.entity.User;
import ru.zharov.admin.exception.NotFoundException;
import ru.zharov.admin.mapper.RoleMapper;
import ru.zharov.admin.mapper.UserMapper;
import ru.zharov.admin.repository.RoleRepository;
import ru.zharov.admin.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Transactional(readOnly = true)
    public List<RoleDto> findAll() {
        return roleMapper.toRoleDtoList(roleRepository.findAll());
    }

}
