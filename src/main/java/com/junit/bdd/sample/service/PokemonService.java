package com.junit.bdd.sample.service;

import com.junit.bdd.sample.exception.PokemonNotFoundException;
import com.junit.bdd.sample.model.PokemonResponse;
import com.junit.bdd.sample.store.entity.Pokemon;
import com.junit.bdd.sample.store.repository.PokemonRepository;
import lombok.RequiredArgsConstructor;

import javax.naming.OperationNotSupportedException;

// Removes a lot of boilerplate and establishes a constructor for Spring injection
@RequiredArgsConstructor
public class PokemonService {
    private final PokemonRepository repository;

    public PokemonResponse pokemonById(Integer pokedexEntryNumber) {
        Pokemon pokemon = repository.findById(pokedexEntryNumber)
                .orElseThrow(() -> new PokemonNotFoundException("Pokedex entry " + pokedexEntryNumber + " not found."));
        return new PokemonResponse(pokemon.getName());
    }
}
