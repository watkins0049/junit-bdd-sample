package com.junit.bdd.sample.store.repository;

import com.junit.bdd.sample.store.entity.Pokemon;
import org.springframework.data.repository.CrudRepository;

public interface PokemonRepository extends CrudRepository<Pokemon, Integer> {
}
