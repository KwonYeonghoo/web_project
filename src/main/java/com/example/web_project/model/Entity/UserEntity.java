package com.example.web_project.model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "UserEntity")
@Table(name = "user")
public class UserEntity {
    @Id
    @NotBlank
    private String userId;

    @NotBlank
    private String userName;
    @NotBlank
    private String userPw;

    private String userAddress;
    
    @Email
    @Column(unique = true)
    private String userEmail;

    @PositiveOrZero
    private int userAge;

    private String userRole;

    @Column(columnDefinition = "tinyint(1) default 0")
    private Boolean isLogin;
}
