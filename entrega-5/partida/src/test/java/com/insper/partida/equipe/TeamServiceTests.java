package com.insper.partida.equipe;

import com.insper.partida.equipe.dto.SaveTeamDTO;
import com.insper.partida.equipe.dto.TeamReturnDTO;
import com.insper.partida.equipe.exceptions.TeamAlreadyExistsException;
import com.insper.partida.equipe.exceptions.TeamDoesNotExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTests {

    @InjectMocks
    TeamService teamService;

    @Mock
    TeamRepository teamRepository;


    @Test
    void test_listTeams() {
        Mockito.when(teamRepository.findAll()).thenReturn(new ArrayList<>());

        List<TeamReturnDTO> resp = teamService.listTeams();

        Assertions.assertEquals(0, resp.size());
    }

    @Test
    void test_listTeamsNotEmpty() {

        Team team = getTeam();

        List<Team> lista = new ArrayList<>();
        lista.add(team);

        Mockito.when(teamRepository.findAll()).thenReturn(lista);

        List<TeamReturnDTO> resp = teamService.listTeams();

        Assertions.assertEquals(1, resp.size());
    }


    @Test
    void test_saveTeam() {
        Team team = getTeam();

        Mockito.when(teamRepository.existsByIdentifier(team.getIdentifier())).thenReturn(false);
        Mockito.when(teamRepository.save(Mockito.any(Team.class))).thenReturn(team);

        SaveTeamDTO saveTeam = new SaveTeamDTO();
        saveTeam.setName(team.getName());
        saveTeam.setIdentifier(team.getIdentifier());

        TeamReturnDTO resp = teamService.saveTeam(saveTeam);

        Assertions.assertEquals(team.getName(), resp.getName());
        Assertions.assertEquals(team.getIdentifier(), resp.getIdentifier());
    }


    @Test
    void test_saveTeamAlreadyExists() {
        Team team = getTeam();

        Mockito.when(teamRepository.existsByIdentifier(team.getIdentifier())).thenReturn(true);

        SaveTeamDTO saveTeam = new SaveTeamDTO();
        saveTeam.setName(team.getName());
        saveTeam.setIdentifier(team.getIdentifier());

        Assertions.assertThrows(TeamAlreadyExistsException.class, () -> {
            teamService.saveTeam(saveTeam);
        });
    }


    @Test
    void test_deleteTeam() {
        Team team = getTeam();

        Mockito.when(teamRepository.findByIdentifier(team.getIdentifier())).thenReturn(team);

        teamService.deleteTeam(team.getIdentifier());
    }


    @Test
    void test_deleteTeamDoesNotExist() {
        Team team = getTeam();

        Mockito.when(teamRepository.findByIdentifier(team.getIdentifier())).thenReturn(null);

        Assertions.assertThrows(TeamDoesNotExistException.class, () -> {
            teamService.deleteTeam(team.getIdentifier());
        });
    }


    @Test
    void test_getTeam() {
        Team team = getTeam();

        Mockito.when(teamRepository.findByIdentifier(team.getIdentifier())).thenReturn(team);

        Team resp = teamService.getTeam(team.getIdentifier());

        Assertions.assertEquals(team.getName(), resp.getName());
        Assertions.assertEquals(team.getIdentifier(), resp.getIdentifier());
    }


    @Test
    void test_getTeamDoesNotExist() {
        Team team = getTeam();

        Mockito.when(teamRepository.findByIdentifier(team.getIdentifier())).thenReturn(null);

        Assertions.assertThrows(TeamDoesNotExistException.class, () -> {
            teamService.getTeam(team.getIdentifier());
        });
    }





    private static Team getTeam() {
        Team team = new Team();
        team.setId("1");
        team.setIdentifier("time-1");
        team.setName("Time 1");
        return team;
    }


}
