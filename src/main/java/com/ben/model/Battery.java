package com.ben.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@Data
@Accessors(chain = true)
public class Battery {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private long postcode;
    private Double capacity;

}
