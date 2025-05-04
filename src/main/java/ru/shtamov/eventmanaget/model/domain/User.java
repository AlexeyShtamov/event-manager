package ru.shtamov.eventmanaget.model.domain;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class User {

    private Long id;
    private String login;
    private String password;
    private Integer age;
    private UserRole userRole;

    public User(String login, Integer age, UserRole userRole) {
        this.login = login;
        this.age = age;
        this.userRole = userRole;
    }

    public User(Long id, String login, Integer age, UserRole userRole) {
        this.id = id;
        this.login = login;
        this.age = age;
        this.userRole = userRole;
    }
}
