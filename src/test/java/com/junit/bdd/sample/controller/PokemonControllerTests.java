package com.junit.bdd.sample.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.junit.bdd.sample.model.PokemonResponse;
import com.junit.bdd.sample.service.PokemonService;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

// These tests are similar to the service layer. If you're starting here first, take a look
// at the tests in PokemonServiceTests class for a full explanation of what's going on. After,
// come back here and take another look!
@DisplayName("class: PokemonController")
public class PokemonControllerTests {

    private final Faker faker = new Faker();

    private PokemonService service;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        service = mock(PokemonService.class);
        PokemonController controller = new PokemonController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Nested
    @DisplayName("function: pokemonByEntry()")
    class PokemonByEntryFunction {
        // This test is for a VALID Pokédex entry. Maybe you can try adding an invalid scenario?
        @Nested
        @DisplayName("GIVEN a valid Pokedex entry number")
        class GivenAPokedexEntryNumber {

            private Integer pokedexEntryNumber;

            @BeforeEach
            void beforeEach() {
                pokedexEntryNumber = faker.number().positive();
            }

            @Nested
            @DisplayName("WHEN a Pokemon is found THEN it")
            class WhenPokemonFound {

                private PokemonResponse expectedResponse;
                private ResultActions result;

                @BeforeEach
                void beforeEach() throws Exception {
                    expectedResponse = new PokemonResponse(faker.pokemon().name());

                    doReturn(expectedResponse)
                            .when(service)
                            .pokemonById(pokedexEntryNumber);

                    result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/pokemon/" + pokedexEntryNumber));
                }

                @Test
                @DisplayName("should retrieve the Pokemon from the database")
                void shouldRetrievePokemon() {
                    verify(service).pokemonById(pokedexEntryNumber);
                }

                @Test
                @DisplayName("should return the pokemon with the appropriate Pokedex number")
                void shouldReturnPokemonName() throws UnsupportedEncodingException, JsonProcessingException {
                    ObjectMapper mapper = new ObjectMapper();
                    assertEquals(expectedResponse, mapper.readValue(
                                    result.andReturn().getResponse().getContentAsString(),
                                    PokemonResponse.class
                            )
                    );
                }

            }
        }
    }
}
