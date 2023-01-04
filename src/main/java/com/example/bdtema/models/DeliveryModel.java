package com.example.bdtema.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryModel {
    private Integer id;
    private String name;
    private String address;
    private Integer payment;
    private Date date;
}
