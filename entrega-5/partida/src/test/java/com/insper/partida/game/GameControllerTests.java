package com.insper.partida.game;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.insper.partida.game.dto.GameReturnDTO;
import com.insper.partida.game.dto.SaveGameDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GameControllerTests {

    MockMvc mockMvc;

    @InjectMocks
    GameController gameController;

    @Mock
    GameService gameService;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(gameController)
                .build();
    }

    @Test
    void test_saveGame() throws Exception {

        SaveGameDTO saveGame = new SaveGameDTO();
        saveGame.setHome("time-1");
        saveGame.setAway("time-2");

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(saveGame);

        GameReturnDTO game = new GameReturnDTO();
        game.setIdentifier("game-1");

        Mockito.when(gameService.saveGame(Mockito.any())).thenReturn(game);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/game")
                        .contentType("application/json")
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();


        String resp = result.getResponse().getContentAsString();
        Assertions.assertEquals(om.writeValueAsString(game), resp);

    }


    @Test
    void test_getGame() throws Exception {

        GameReturnDTO game = new GameReturnDTO();
        game.setIdentifier("game-1");

        Mockito.when(gameService.getGame(Mockito.any())).thenReturn(game);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/game/game-1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ObjectMapper om = new ObjectMapper();

        String resp = result.getResponse().getContentAsString();
        Assertions.assertEquals(om.writeValueAsString(game), resp);

    }


    @Test
    void test_changeGame() throws Exception {

        GameReturnDTO game = new GameReturnDTO();
        game.setIdentifier("game-1");

        Mockito.when(gameService.editGame(Mockito.any(), Mockito.any())).thenReturn(game);

        ObjectMapper om = new ObjectMapper();

        String json = om.writeValueAsString(game);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/game/game-1")
                        .contentType("application/json")
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String resp = result.getResponse().getContentAsString();
        Assertions.assertEquals(json, resp);
    }


    @Test
    void test_deleteGame() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/game/game-1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();
    }

}
