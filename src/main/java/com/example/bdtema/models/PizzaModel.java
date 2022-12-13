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

    private int id;
    private String name;
    private String[] ingredients;
    private String description;
    private String imagePath;

    public PizzaModel(int id, String name, String[] ingredients, String description) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
    }

}
