package com.example.bdtema.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private Integer id;
    private String email;
    private String password;

    private Integer deliveryId;

    public UserModel(Integer id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

}
