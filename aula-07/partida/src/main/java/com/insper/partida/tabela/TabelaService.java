package com.insper.partida.tabela;

import com.insper.partida.equipe.TeamService;
import com.insper.partida.equipe.dto.TeamReturnDTO;
import com.insper.partida.game.Game;
import com.insper.partida.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class TabelaService {

    @Autowired
    private TabelaRespository tabelaRepository;

    public List<Tabela> getTabela() {

        List<Tabela> response = tabelaRepository.findAll();
        response.sort(Comparator.comparingInt(Tabela::getPontos).reversed());
        return response;

    }

    public Tabela getTabelaByIdentifier(String identifier) {
        return tabelaRepository.findByIdentifier(identifier);
    }

    public Tabela saveTabela(Tabela tabela) {
        return tabelaRepository.save(tabela);
    }
}
