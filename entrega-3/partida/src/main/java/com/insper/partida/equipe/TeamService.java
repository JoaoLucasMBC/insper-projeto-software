package com.insper.partida.equipe;

import com.insper.partida.equipe.dto.SaveTeamDTO;
import com.insper.partida.equipe.dto.TeamReturnDTO;
import com.insper.partida.equipe.exceptions.TeamAlreadyExistsException;
import com.insper.partida.equipe.exceptions.TeamDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class  TeamService {

    @Autowired
    private TeamRepository teamRepository;


    public List<TeamReturnDTO> listTeams() {
        return teamRepository.findAll().stream().map(TeamReturnDTO::covert).collect(Collectors.toList());
    }

    public TeamReturnDTO saveTeam(SaveTeamDTO saveTeam) {

        if (teamRepository.existsByIdentifier(saveTeam.getIdentifier())) {
            throw new TeamAlreadyExistsException();
        }

        Team team = new Team();
        team.setName(saveTeam.getName());
        team.setIdentifier(saveTeam.getIdentifier());

        team = teamRepository.save(team);
        return  TeamReturnDTO.covert(team);
    }


    public void deleteTeam(String identifier) {

        Team team = teamRepository.findByIdentifier(identifier);
        if (team != null) {
            teamRepository.delete(team);
        } else {
            throw new TeamDoesNotExistException();
        }

    }

    public Team getTeam(String identifier) {
        return teamRepository.findByIdentifier(identifier);
    }
}
