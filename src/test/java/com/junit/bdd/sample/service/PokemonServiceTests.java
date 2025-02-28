package com.junit.bdd.sample.service;

import com.junit.bdd.sample.model.PokemonResponse;
import com.junit.bdd.sample.store.entity.Pokemon;
import com.junit.bdd.sample.store.repository.PokemonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.naming.OperationNotSupportedException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

// Sets the output in IntelliJ when running the tests. Take a look in the output after running :)
// No necessary for BDD-style tests, but something I like to add as a detail
@DisplayName("class: PokemonService")
public class PokemonServiceTests {

    private PokemonService service;
    private PokemonRepository repository;

    // First layer before each. This is executed first before every other before each
    @BeforeEach
    void beforeEach() {
        repository = mock(PokemonRepository.class);
        service = new PokemonService(repository);
    }

    // Not quite to the BDD fun yet! This is for organizational purposes only. All tests
    // for hte function pokemonById will be stored under this class. The @Nested annotation
    // lets JUnit 5 know there are tests within the class and to run them as well
    @Nested
    @DisplayName("function: pokemonById()")
    class PokemonByIdFunction {

        // Here we are at the BDD fun! This is the first layer where we're given an input value.
        // This value can then be fed into child classes. This will be important later on :)
        @Nested
        @DisplayName("GIVEN a Pokedex Entry Number")
        class GivenAPokedexEntryNumber {

            private Integer pokedexEntryNumber;

            // Second layer before each. This is executed after the parent level. Technically not
            // needed since the value is hard coded as 25, but can be useful with randomized data.
            @BeforeEach
            void beforeEach() {
                pokedexEntryNumber = 25;
            }

            // Final layer of the BDD tests...for this service! You can go as many layers deep as
            // you want, with a caveat. Too many layers deep can cause test failures because of
            // how the JUnit coverage file is written to the file system. The file name is
            // constructed by appending each class name with each other ("$" delimited). Too many
            // classes, too large of a file for the OS to handle. Good thing we have a DisplayName
            // attribute making the class names irrelevant!
            @Nested
            @DisplayName("WHEN a Pokemon is found THEN it")
            class WhenThePokemonIsFound {

                private PokemonResponse response;

                // Final layer of before each. This is executed after every other layer. Data
                // from each parent layer can be fed in as arguments, creating natively
                // parameterized tests
                @BeforeEach
                void beforeEach() throws OperationNotSupportedException {
                    doReturn(Optional.of(new Pokemon(25, "Pikachu")))
                            .when(repository)
                            .findById(25);
                    response = service.pokemonById(pokedexEntryNumber);
                }

                @Test
                @DisplayName("should call the DB to retrieve the Pokemon")
                void shouldCallRepository() {
                    verify(repository).findById(pokedexEntryNumber);
                }

                @Test
                @DisplayName("should return the Pokemon found")
                void shouldReturnThePokemon() {
                    assertEquals(new PokemonResponse("Pikachu"), response);
                }
            }
        }

    }
}
