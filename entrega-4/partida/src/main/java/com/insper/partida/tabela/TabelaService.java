package com.insper.partida.tabela;

import com.insper.partida.tabela.dto.TabelaReturnDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TabelaService {

    @Autowired
    private TabelaRespository tabelaRepository;

    public List<TabelaReturnDTO> getTabela() {
        return tabelaRepository.findAll()
                .stream()
                .map(TabelaReturnDTO::covert)
                .sorted(Comparator.comparingInt(TabelaReturnDTO::getPontos).reversed())
                .collect(Collectors.toList());

    }

    public Tabela getTabelaByIdentifier(String identifier) {
        return tabelaRepository.findByIdentifier(identifier);
    }

    public Tabela getTabelaByNome(String nome) {
        return tabelaRepository.findByNome(nome);
    }

    public Tabela saveTabela(Tabela tabela) {
        return tabelaRepository.save(tabela);
    }
}
