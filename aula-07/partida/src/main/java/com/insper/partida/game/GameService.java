package com.insper.partida.game;

import com.insper.partida.equipe.Team;
import com.insper.partida.equipe.TeamService;
import com.insper.partida.equipe.dto.SaveTeamDTO;
import com.insper.partida.equipe.dto.TeamReturnDTO;
import com.insper.partida.game.dto.EditGameDTO;
import com.insper.partida.game.dto.GameReturnDTO;
import com.insper.partida.game.dto.SaveGameDTO;
import com.insper.partida.tabela.Tabela;
import com.insper.partida.tabela.TabelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private TabelaService tabelaService;

    public Page<GameReturnDTO> listGames(String home, String away, Integer attendance, Pageable pageable) {
        if (home != null && away != null) {

            Team tHome = teamService.getTeam(home);
            Team tAway = teamService.getTeam(away);

            Page<Game> games = gameRepository.findByHomeAndAway(tHome.getIdentifier(), tAway.getIdentifier(), pageable);
            return games.map(game -> GameReturnDTO.covert(game));

        } else if (attendance != null) {
            Page<Game> games =  gameRepository.findByAttendanceGreaterThan(attendance, pageable);
            return games.map(game -> GameReturnDTO.covert(game));
        }
        Page<Game> games =  gameRepository.findAll(pageable);
        return games.map(game -> GameReturnDTO.covert(game));
    }

    public GameReturnDTO saveGame(SaveGameDTO saveGameDTO) {

        Team teamM = teamService.getTeam(saveGameDTO.getHome());
        Team teamV = teamService.getTeam(saveGameDTO.getAway());

        if (teamM == null || teamV == null) {
            return null;
        }

        Game game = new Game();
        game.setIdentifier(UUID.randomUUID().toString());
        game.setHome(teamM.getIdentifier());
        game.setAway(teamV.getIdentifier());
        game.setAttendance(0);
        game.setScoreHome(0);
        game.setScoreAway(0);
        game.setGameDate(LocalDateTime.now());
        game.setStatus("SCHEDULED");

        gameRepository.save(game);
        return GameReturnDTO.covert(game);

    }

    public GameReturnDTO editGame(String identifier, EditGameDTO editGameDTO) {
        Game gameBD = gameRepository.findByIdentifier(identifier);

        gameBD.setScoreAway(editGameDTO.getScoreAway());
        gameBD.setScoreHome(editGameDTO.getScoreHome());
        gameBD.setAttendance(editGameDTO.getAttendance());
        gameBD.setStatus("FINISHED");

        // get both teams
        Team teamH = teamService.getTeam(gameBD.getHome());
        Team teamA = teamService.getTeam(gameBD.getAway());

        // get the tabela of both teams
        Tabela tabelaH = tabelaService.getTabelaByIdentifier(teamH.getIdentifier());

        if (tabelaH == null) {
            tabelaH = new Tabela();
            tabelaH.setIdentifier(teamH.getIdentifier());
            tabelaH.setNome(teamH.getName());
            tabelaH.setPontos(0);
            tabelaH.setVitorias(0);
            tabelaH.setDerrotas(0);
            tabelaH.setEmpates(0);
            tabelaH.setGolsPro(0);
            tabelaH.setGolsContra(0);
            tabelaH.setJogos(0);
        }

        Tabela tabelaA = tabelaService.getTabelaByIdentifier(teamA.getIdentifier());

        if (tabelaA == null) {
            tabelaA = new Tabela();
            tabelaA.setIdentifier(teamA.getIdentifier());
            tabelaA.setNome(teamA.getName());
            tabelaA.setPontos(0);
            tabelaA.setVitorias(0);
            tabelaA.setDerrotas(0);
            tabelaA.setEmpates(0);
            tabelaA.setGolsPro(0);
            tabelaA.setGolsContra(0);
            tabelaA.setJogos(0);
        }

        // update home team tabela
        tabelaH.setPontos(tabelaH.getPontos() + verificaResultado(teamH, gameBD));
        tabelaH.setVitorias(tabelaH.getVitorias() + (verificaVitorias(teamH, gameBD) ? 1 : 0));
        tabelaH.setDerrotas(tabelaH.getDerrotas() + (verificaDerrotas(teamH, gameBD) ? 1 : 0));
        tabelaH.setEmpates(tabelaH.getEmpates() + (verificaEmpates(teamH, gameBD) ? 1 : 0));
        tabelaH.setGolsPro(tabelaH.getGolsPro() + verificaGolsPro(teamH, gameBD));
        tabelaH.setGolsContra(tabelaH.getGolsContra()  + verificaGolsContra(teamH, gameBD));
        tabelaH.setJogos(tabelaH.getJogos() + 1);

        // update away team tabela
        tabelaA.setPontos(tabelaH.getPontos() + verificaResultado(teamA, gameBD));
        tabelaA.setVitorias(tabelaH.getVitorias() + (verificaVitorias(teamA, gameBD) ? 1 : 0));
        tabelaA.setDerrotas(tabelaH.getDerrotas() + (verificaDerrotas(teamA, gameBD) ? 1 : 0));
        tabelaA.setEmpates(tabelaH.getEmpates() + (verificaEmpates(teamA, gameBD) ? 1 : 0));
        tabelaA.setGolsPro(tabelaH.getGolsPro() + verificaGolsPro(teamA, gameBD));
        tabelaA.setGolsContra(tabelaH.getGolsContra()  + verificaGolsContra(teamA, gameBD));
        tabelaA.setJogos(tabelaH.getJogos() + 1);

        // save both tabelas
        tabelaService.saveTabela(tabelaH);
        tabelaService.saveTabela(tabelaA);

        Game game = gameRepository.save(gameBD);
        return GameReturnDTO.covert(game);
    }

    public void deleteGame(String identifier) {
        Game gameBD = gameRepository.findByIdentifier(identifier);
        if (gameBD != null) {
            gameRepository.delete(gameBD);
        }
    }

    public Integer getScoreTeam(String identifier) {
        Team team = teamService.getTeam(identifier);

        return 0;
    }

    public GameReturnDTO getGame(String identifier) {
        return GameReturnDTO.covert(gameRepository.findByIdentifier(identifier));
    }

    public void generateData() {

        String [] teams = {"botafogo", "palmeiras", "gremio", "flamengo", "fluminense", "bragantino", "atletico-mg", "athletico-pr", "fortaleza", "cuiaba", "sao-paulo",
                        "internacional", "cruzeiro", "corinthians", "goias", "bahia", "santos", "vasco", "coritiba", "america-mg"};

        for (String team : teams) {
            SaveTeamDTO saveTeamDTO = new SaveTeamDTO();
            saveTeamDTO.setName(team);
            saveTeamDTO.setStadium(team);
            saveTeamDTO.setIdentifier(team);

            Team teamDB = teamService.getTeam(team);
            if (teamDB == null) {
                teamService.saveTeam(saveTeamDTO);
            }
        }

        for (int i = 0; i < 1000; i++) {

            Integer team1 = new Random().nextInt(20);
            Integer team2 = new Random().nextInt(20);
            while  (team1 == team2) {
                team2 = new Random().nextInt(20);
            }

            Game game = new Game();
            game.setIdentifier(UUID.randomUUID().toString());
            game.setHome(teams[team1]);
            game.setAway(teams[team2]);
            game.setScoreHome(new Random().nextInt(4));
            game.setScoreAway(new Random().nextInt(4));
            game.setStadium(teams[team1]);
            game.setAttendance(new Random().nextInt(4) * 1000);

            gameRepository.save(game);
            EditGameDTO gameedit = new EditGameDTO();
            gameedit.setAttendance(game.getAttendance());
            gameedit.setScoreAway(game.getScoreAway());
            gameedit.setScoreHome(game.getScoreHome());
            this.editGame(game.getIdentifier(), gameedit);

        }

    }

    public List<Game> getGameByTeam(String identifier) {
        return gameRepository.findByHomeOrAway(identifier, identifier);
    }


    private Integer verificaResultado(Team time, Game game) {
        if (game.getScoreHome() == game.getScoreAway()) {
            return 1;
        }
        if (game.getHome().equals(time.getIdentifier()) && game.getScoreHome() > game.getScoreAway()) {
            return 3;
        }
        if (game.getAway().equals(time.getIdentifier()) && game.getScoreAway() > game.getScoreHome()) {
            return 3;
        }
        return 0;
    }

    private Integer verificaGolsPro(Team time, Game game) {
        if (game.getHome().equals(time.getIdentifier())) {
            return game.getScoreHome();
        }
        return game.getScoreAway();
    }

    private Integer verificaGolsContra(Team time, Game game) {
        if (game.getHome().equals(time.getIdentifier())) {
            return game.getScoreAway();
        }
        return game.getScoreHome();
    }

    private boolean verificaVitorias(Team time, Game game) {
        if (game.getHome().equals(time.getIdentifier()) && game.getScoreHome() > game.getScoreAway()) {
            return true;
        }
        if (game.getAway().equals(time.getIdentifier()) && game.getScoreAway() > game.getScoreHome()) {
            return true;
        }
        return false;
    }

    private boolean verificaDerrotas(Team time, Game game) {
        if (game.getHome().equals(time.getIdentifier()) && game.getScoreHome() < game.getScoreAway()) {
            return true;
        }
        if (game.getAway().equals(time.getIdentifier()) && game.getScoreAway() < game.getScoreHome()) {
            return true;
        }
        return false;
    }

    private boolean verificaEmpates(Team time, Game game) {
        if (game.getScoreHome() == game.getScoreAway()) {
            return true;
        }
        return false;
    }
}
