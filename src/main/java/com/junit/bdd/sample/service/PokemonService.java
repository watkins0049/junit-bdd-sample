package com.junit.bdd.sample.service;

import com.junit.bdd.sample.model.PokemonResponse;
import com.junit.bdd.sample.store.entity.Pokemon;
import com.junit.bdd.sample.store.repository.PokemonRepository;
import lombok.RequiredArgsConstructor;

import javax.naming.OperationNotSupportedException;

// Removes a lot of boilerplate and establishes a constructor for Spring injection
@RequiredArgsConstructor
public class PokemonService {
    private final PokemonRepository repository;

    public PokemonResponse pokemonById(Integer pokedexEntryNumber) throws OperationNotSupportedException {
        Pokemon pokemon = repository.findById(pokedexEntryNumber).get();
        return new PokemonResponse(pokemon.getName());
    }
}
