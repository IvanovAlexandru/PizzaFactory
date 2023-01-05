package com.example.bdtema.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PizzaModel {

    private Integer id;
    private String name;
    private String description;
    private String imagePath;
    private Integer price;
}
