package com.junit.bdd.sample.store.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Pokemon {
    @Id
    private Integer id;
    @Getter
    private String name;
}
