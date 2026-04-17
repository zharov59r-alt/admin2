package ru.zharov.admin.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.zharov.admin.entity.Role;
import ru.zharov.admin.entity.User;
import ru.zharov.admin.entity.UserRoleLink;
import ru.zharov.admin.mapper.UserRoleLinkMapper;
import ru.zharov.admin.repository.UserRoleLinkRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRoleLinkUtils {

    private final UserRoleLinkRepository userRoleLinkRepository;
    private final UserRoleLinkMapper userRoleLinkMapper;

    public void merge(User user, List<Role> roles) {

        log.debug("UserRoleLinkUtils begin");
        log.debug("roles {}", roles);

        // если новый список ролей пустой, то просто чистим все и выходим
        if (roles.isEmpty()) {
            log.debug("remove all");
            userRoleLinkRepository.deleteByUserId(user.getId());
        } else {

            List<UserRoleLink> currentUserRoleLink = userRoleLinkRepository.findByUser(user);
            log.debug("currentUserRoleLink {}", currentUserRoleLink);

            // если список существующих ролей пуст, то просто все инсертим
            if (currentUserRoleLink.isEmpty()) {
                log.debug("save all");
                userRoleLinkRepository.saveAll(
                        userRoleLinkMapper.toUserRoleLinkList(user, roles)
                );

            } else {

                log.debug("merge");

                // ищем и удаляем лишние роли
                List<Long> removeRoles = currentUserRoleLink.stream()
                        .map(url -> url.getRole().getId())
                        .filter(roleId -> roles.stream().noneMatch(r -> r.getId() == roleId))
                        .collect(Collectors.toList());

                log.debug("removeRoles " + removeRoles);

                userRoleLinkRepository.deleteAllByIdInBatch(removeRoles);

                List<Role> newRoles = roles.stream()
                        .filter(r -> currentUserRoleLink.stream()
                                .noneMatch(url -> url.getRole().getId() == r.getId()))
                        .collect(Collectors.toList());

                log.debug("newRoles " + newRoles.toString());

                if (!newRoles.isEmpty()) {
                    List<UserRoleLink> userRoleLink = userRoleLinkMapper.toUserRoleLinkList(user, newRoles);
                    userRoleLinkRepository.saveAll(userRoleLink);
                }

            }

        }

    }

}
