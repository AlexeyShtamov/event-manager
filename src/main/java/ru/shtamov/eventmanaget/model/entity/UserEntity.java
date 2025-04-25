package ru.shtamov.eventmanaget.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String password;

    private Integer age;

    @Column(name = "role")
    private String userRole;

    public UserEntity(String login, String password, Integer age, String userRole) {
        this.login = login;
        this.password = password;
        this.age = age;
        this.userRole = userRole;
    }
}
