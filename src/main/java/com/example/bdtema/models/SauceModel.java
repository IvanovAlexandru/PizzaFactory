package com.example.bdtema.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SauceModel {

    private Integer id;
    private String name;
    private String description;
    private String imagePath;

}
