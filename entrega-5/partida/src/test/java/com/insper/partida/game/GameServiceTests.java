package com.insper.partida.game;


import com.insper.partida.equipe.Team;
import com.insper.partida.equipe.TeamService;
import com.insper.partida.equipe.dto.SaveTeamDTO;
import com.insper.partida.equipe.dto.TeamReturnDTO;
import com.insper.partida.equipe.exceptions.TeamAlreadyExistsException;
import com.insper.partida.equipe.exceptions.TeamDoesNotExistException;
import com.insper.partida.game.dto.EditGameDTO;
import com.insper.partida.game.dto.GameReturnDTO;
import com.insper.partida.game.dto.SaveGameDTO;
import com.insper.partida.game.exceptions.GameDoesNotExistException;
import com.insper.partida.game.exceptions.GameTeamsEqualException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;



@ExtendWith(MockitoExtension.class)
public class GameServiceTests {

    MockMvc mockMvc;

    @InjectMocks
    GameService gameService;

    @Mock
    GameRepository gameRepository;

    @Mock
    TeamService teamService;



    @Test
    void test_saveTeam() {
        Game game = getGame();

        Team team = new Team();
        team.setIdentifier("time-1");

        Team team2 = new Team();
        team2.setIdentifier("time-2");

        SaveGameDTO saveGame = new SaveGameDTO();
        saveGame.setHome(game.getHome());
        saveGame.setAway(game.getAway());

        Mockito.when(gameRepository.save(Mockito.any(Game.class))).thenReturn(game);
        Mockito.when(teamService.getTeam(saveGame.getHome())).thenReturn(team);
        Mockito.when(teamService.getTeam(saveGame.getAway())).thenReturn(team2);


        GameReturnDTO resp = gameService.saveGame(saveGame);

        Assertions.assertEquals(game.getHome(), resp.getHome());
        Assertions.assertEquals(game.getAway(), resp.getAway());
    }

    @Test
    void test_saveTeamHomeEqualAway() {
        Game game = getGame();

        Team team = new Team();
        team.setIdentifier("time-1");

        Team team2 = new Team();
        team2.setIdentifier("time-1");

        SaveGameDTO saveGame = new SaveGameDTO();
        saveGame.setHome(game.getHome());
        saveGame.setAway(game.getHome());

        Mockito.when(teamService.getTeam(saveGame.getHome())).thenReturn(team);
        Mockito.when(teamService.getTeam(saveGame.getAway())).thenReturn(team2);

        Assertions.assertThrows(GameTeamsEqualException.class, () -> {
            gameService.saveGame(saveGame);
        });
    }

    @Test
    void test_saveTeamDoesNotExist() {
        Game game = getGame();

        SaveGameDTO saveGame = new SaveGameDTO();
        saveGame.setHome(game.getHome());
        saveGame.setAway(game.getAway());

        Assertions.assertThrows(TeamDoesNotExistException.class, () -> {
            gameService.saveGame(saveGame);
        });
    }

    @Test
    void test_deleteGame() {
        Game game = getGame();

        Mockito.when(gameRepository.findByIdentifier(game.getIdentifier())).thenReturn(game);

        gameService.deleteGame(game.getIdentifier());
    }

    @Test
    void test_deleteGameDoesNotExist() {
        Game game = getGame();

        Mockito.when(gameRepository.findByIdentifier(game.getIdentifier())).thenReturn(null);

        Assertions.assertThrows(GameDoesNotExistException.class, () -> {
            gameService.deleteGame(game.getIdentifier());
        });
    }

    @Test
    void test_getGame() {
        Game game = getGame();

        Mockito.when(gameRepository.findByIdentifier(game.getIdentifier())).thenReturn(game);

        GameReturnDTO resp = gameService.getGame(game.getIdentifier());

        Assertions.assertEquals(game.getHome(), resp.getHome());
        Assertions.assertEquals(game.getAway(), resp.getAway());
    }

    @Test
    void test_getGameDoesNotExist() {
        Game game = getGame();

        Mockito.when(gameRepository.findByIdentifier(game.getIdentifier())).thenReturn(null);

        Assertions.assertThrows(GameDoesNotExistException.class, () -> {
            gameService.getGame(game.getIdentifier());
        });
    }



    private static Game getGame() {
        Game game = new Game();
        game.setIdentifier("game-1");
        game.setId(null);
        game.setHome("time-1");
        game.setAway("time-2");
        return game;
    }


}
