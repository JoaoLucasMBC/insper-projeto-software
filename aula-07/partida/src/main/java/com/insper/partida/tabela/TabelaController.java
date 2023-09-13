package com.insper.partida.tabela;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tabela")
public class TabelaController {

    @Autowired
    private TabelaService tabelaService;

    @GetMapping
    public List<Tabela> getTabela() {
        return tabelaService.getTabela();
    }

    @GetMapping("/identifier")
    public Tabela getTabelaByIdentifier(String identifier) {
        return tabelaService.getTabelaByIdentifier(identifier);
    }

}
