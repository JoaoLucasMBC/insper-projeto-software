package com.insper.partida.game;

import com.insper.partida.equipe.Team;
import com.insper.partida.equipe.TeamRepository;
import com.insper.partida.game.dto.GameReturnDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private Cache cache;

    @GetMapping("/numbers")
    public HashMap<Integer,Integer> getInts(@RequestParam("amount") Integer amount) {
        List<Integer> nums = new ArrayList<>();
        Random ran = new Random();

        for (int i = 0; i < amount; i++) {
            nums.add(ran.nextInt(100));
        }
        
        // algoritmo ineficiente (arrumado!)
        HashMap<Integer, Integer> count = new HashMap<>();
        for (int j = 0; j < nums.size(); j++) {
            count.merge(nums.get(j), 1, Integer::sum);
        }

        return count;
    }

    @GetMapping("/tenGamesHomeByTeam")
    public List<GameReturnDTO> getTenGamesHomeByTeam(@RequestParam(name = "team") String team) {

        Team teamDB = cache.getTeam(team);
        if (teamDB == null) {
            throw new RuntimeException("Time não encontrado");
        }

        //nunca faça findall! faz tudo no banco de dados, principalmente filter + limit
        List<Game> games = gameRepository
                .findTop10ByHomeOrAway(team); // é melhor fazer um filtro na query da DB, pq se o conjunto de dados vai ser grande, vai dar problema

        return games.stream().map(g -> GameReturnDTO.covert(g)).toList();

    }

    @GetMapping("/getAllGamesByTeams")
    public List<GameReturnDTO> getAllTeams(@RequestParam(name = "team") List<String> teams) {

        for (String team : teams) {
            Team teamDB = cache.getTeam(team); // poderia usar cache
            if (teamDB == null) {
                throw new RuntimeException("Time não encontrado");
            }
        }

        List<Game> allGames = new ArrayList<>();

        for (String team : teams) {
            List<Game> games = gameRepository
                    .findAll()
                    .stream()
                    .filter(g -> g.getHome().equals(team) || g.getAway().equals(team))
                    .limit(10)
                    .toList();

            allGames.addAll(games);
        }

        return allGames.stream().map(g -> GameReturnDTO.covert(g)).toList();
    }


    @GetMapping("/getPointsByTeam")
    public HashMap<String, Integer>  getPointsByTeam(@RequestParam(name = "team") List<String> teams) {

        for (String team : teams) {
            Team teamDB = cache.getTeam(team);
            if (teamDB == null) {
                throw new RuntimeException("Time não encontrado");
            }
        }

        HashMap<String, Integer> teamPoints = new HashMap<>();

        // podia fazer esse cálculo OFFLINE ou usar cache, porque se não dá muito trabalho
        // ou ir atualizando automaticamente, tem uma tabela
        for (String team : teams) {
            List<Game> games = gameRepository
                    .findAll()
                    .stream()
                    .filter(g -> g.getHome().equals(team) || g.getAway().equals(team))
                    .toList();

            Optional<Integer> points = games
                    .stream()
                    .map(g -> numPoints(g, team))
                    .reduce((a, p) -> a + p);
            teamPoints.put(team, points.get());

        }

        return teamPoints;
    }

    private Integer numPoints(Game g, String team) {
        if (g.getScoreAway() == g.getScoreHome()) {
            return 1;
        } else if (g.getHome().equals(team) && g.getScoreHome() > g.getScoreAway()) {
            return 3;
        } else if (g.getAway().equals(team) && g.getScoreAway() > g.getScoreHome()) {
            return 3;
        }
        return 0;
    }


}



/*
 * Problemas gerais:
 * 
 * 1. Algoritmos ineficientes
 * 2. Repetição de chamadas lentas que podem ser otimizadas (cache)
 * 3. Má utilização de ferramentas (ex: DB)
 * 4. Fazer chamadas repetitivas que são custosas (calcular offline)
 * 
 * 
 * Postman para tempo
 * JMeter para concorrência
 */