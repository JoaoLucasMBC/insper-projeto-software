package com.insper.partida.game;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insper.partida.equipe.Team;
import com.insper.partida.equipe.TeamRepository;

@Service
public class Cache {
    
    private Map<String, Team> teams = new HashMap<>();

    @Autowired
    private TeamRepository teamRepository;

    public Team getTeam(String team) {

        if (teams.containsKey(team)) {
            return teams.get(team);
        }

        Team teamDB = teamRepository.findByIdentifier(team);

        if (team != null) {
            teams.put(team, teamDB);
        }

        return teamDB;

    }

}
