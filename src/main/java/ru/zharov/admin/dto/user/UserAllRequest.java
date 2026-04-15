package ru.zharov.admin.dto.user;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Data
public class UserAllRequest {
    // null сделан для работы функции CustomPostgreSQLDialect fts
    private String searchText = "null";
    private List<Long> roles;
    private Integer pageNumber = 0;
    private Integer pageSize = Integer.MAX_VALUE;

    public void setSearchText(String searchText) {

        if (searchText.isBlank()) {
            this.searchText = "null";
        } else {
            this.searchText = searchText;
        }
    }

    // сделано для того чтобы если передается пустой массив, то поведение было как с null
    public void setRoles(List<Long> roles) {
        if (roles.size() != 0)
            this.roles = roles;
    }

}
