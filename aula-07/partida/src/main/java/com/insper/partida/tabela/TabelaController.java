package com.insper.partida.tabela;

import com.insper.partida.tabela.dto.TabelaReturnDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tabela")
public class TabelaController {

    @Autowired
    private TabelaService tabelaService;


    @GetMapping
    public List<TabelaReturnDTO> getTabela() {
        return tabelaService.getTabela();
    }

    @GetMapping("/{identifier}")
    public TabelaReturnDTO getTabelaByIdentifier(@PathVariable String identifier) {
        return TabelaReturnDTO.covert(tabelaService.getTabelaByIdentifier(identifier));
    }

    @PostMapping
    public TabelaReturnDTO saveTabela(Tabela tabela) {
        return TabelaReturnDTO.covert(tabelaService.saveTabela(tabela));
    }

}
