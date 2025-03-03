package com.junit.bdd.sample.service;

import com.junit.bdd.sample.exception.PokemonNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

// Sets the output in IntelliJ when running the tests. Take a look in the output after running :)
// No necessary for BDD-style tests, but something I like to add as a detail
@DisplayName("class: PokemonService")
public class PokemonServiceTests {

    private PokemonService service;
    private PokemonRepository repository;

    // First layer before each. This is executed first before every other before each. This layer
    // only sets up the service under test and its dependencies.
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
            // attribute making the class names irrelevant! Try running this test and take a look
            // in the build/test-results/test directory. Afterward, make one of the class names
            // super long and try re-running
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
                    // We're taking advantage of the before each here! Because this runs
                    // before each test, we can save the output from the service call and
                    // perform assertions against the output in separate tests. This allows
                    // each test to be completely atomic!
                    response = service.pokemonById(pokedexEntryNumber);
                }

                // The first test! We can create independent tests due to our layered approach
                @Test
                @DisplayName("should call the DB to retrieve the Pokemon")
                void shouldCallRepository() {
                    verify(repository).findById(pokedexEntryNumber);
                }

                // The second test! Completely separate from the other with a completely
                // different assertion. This one can fail while the other succeeds, allowing
                // you to know which test failed and why right away!
                @Test
                @DisplayName("should return the Pokemon found")
                void shouldReturnThePokemon() {
                    assertEquals(new PokemonResponse("Pikachu"), response);
                }
            }

            // Our second scenario! If you read the other "WHEN" scenario (hint: you should :))
            // then this is where the power of this framework shines. We set up a separate mock
            // return value
            @Nested
            @DisplayName("WHEN a Pokemon is NOT found THEN it")
            class WhenThePokemonIsNotFound {
                private PokemonNotFoundException exception;

                // Final layer of before each. This is executed after every other layer. Data
                // from each parent layer can be fed in as arguments, creating natively
                // parameterized tests
                @BeforeEach
                void beforeEach() {
                    doReturn(Optional.empty())
                            .when(repository)
                            .findById(25);
                    // Wait! There's an assertion in the before each. This is a bit odd, but
                    // it's useful. If this assertion fails, then all other tests will fail,
                    // but don't worry, they will each spit out a proper error message and
                    // each will have the same message!
                    exception = assertThrows(
                            PokemonNotFoundException.class,
                            () -> service.pokemonById(pokedexEntryNumber)
                    );
                }

                // We've seen this before. Yes there is some repetition, but we still want
                // to assert that this action is taking place. Embrace some redundancy!
                @Test
                @DisplayName("should call the DB to retrieve the Pokemon")
                void shouldCallRepository() {
                    verify(repository).findById(pokedexEntryNumber);
                }

                // Now we assert the value of the exception to ensure the message is correct
                @Test
                @DisplayName("should indicate there is no such Pokemon found")
                void shouldThrowException() {
                    assertEquals(
                            new PokemonNotFoundException("Pokedex entry " + pokedexEntryNumber + " not found."),
                            exception
                    );
                }
            }
        }
    }
}
