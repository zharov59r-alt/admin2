package ru.zharov.admin.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserAllResponse2 {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDateTime creationDate;
    private String roles;


}