package ru.zharov.admin.utils;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.zharov.admin.entity.Role;
import ru.zharov.admin.exception.NotFoundException;
import ru.zharov.admin.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleUtils {

    private final RoleRepository roleRepository;

    public List<Role> findAndCheckRoles(List<Long> roleIds) {

        List<Role> roles = new ArrayList<>();

        if (roleIds != null && !roleIds.isEmpty()) {
            // обычный .toList() возращает НЕизменяемый спиок из за чего будет падать ошибка на removeAll
            List<Long> roleIdsD = roleIds.stream().distinct().collect(Collectors.toList());
            roles = roleRepository.findAllById(roleIds);
            if (roleIdsD.size() != roles.size()) {
                roleIdsD.removeAll(roles.stream().map(Role::getId).toList());
                throw new NotFoundException("Роли с ID " + roleIdsD + " не найдена");
            }
        }

        return roles;

    }
}
