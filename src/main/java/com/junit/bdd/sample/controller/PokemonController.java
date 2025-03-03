package com.junit.bdd.sample.controller;


import com.junit.bdd.sample.model.PokemonResponse;
import com.junit.bdd.sample.service.PokemonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PokemonController {
    private final PokemonService service;

    @GetMapping("/v1/pokemon/{pokedexEntryNumber}")
    public ResponseEntity<PokemonResponse> pokemonByEntry(@PathVariable Integer pokedexEntryNumber) {
        return ResponseEntity.ok(service.pokemonById(pokedexEntryNumber));
    }
}
