package com.example.test.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    public String firstName;
    public String lastName;
    public String email;
    public User(String firstName) {
        this.firstName = firstName;
    }

}
