package ru.shtamov.eventmanaget.model.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private Integer id;
    private String login;
    private String password;
    private Integer age;
    private UserRole userRole;

    public User(Integer id, String login, Integer age, UserRole userRole) {
        this.id = id;
        this.login = login;
        this.age = age;
        this.userRole = userRole;
    }
}
