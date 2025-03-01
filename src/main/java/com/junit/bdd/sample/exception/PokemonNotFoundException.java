package com.junit.bdd.sample.exception;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class PokemonNotFoundException extends RuntimeException {
    public PokemonNotFoundException(String message) {
        super(message);
    }
}
