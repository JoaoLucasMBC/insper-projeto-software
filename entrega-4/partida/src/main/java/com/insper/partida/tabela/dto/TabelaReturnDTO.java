package com.insper.partida.tabela.dto;


import com.insper.partida.tabela.Tabela;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TabelaReturnDTO {

    public String nome;
    public Integer pontos;
    public Integer golsPro;
    public Integer golsContra;
    public Integer vitorias;
    public Integer derrotas;
    public Integer empates;
    public Integer jogos;

    public static TabelaReturnDTO covert(Tabela tabela) {
        TabelaReturnDTO tabelaReturnDTO = new TabelaReturnDTO();
        tabelaReturnDTO.setNome(tabela.getNome());
        tabelaReturnDTO.setPontos(tabela.getPontos());
        tabelaReturnDTO.setGolsPro(tabela.getGolsPro());
        tabelaReturnDTO.setGolsContra(tabela.getGolsContra());
        tabelaReturnDTO.setVitorias(tabela.getVitorias());
        tabelaReturnDTO.setDerrotas(tabela.getDerrotas());
        tabelaReturnDTO.setEmpates(tabela.getEmpates());
        tabelaReturnDTO.setJogos(tabela.getJogos());
        return tabelaReturnDTO;
    }

}
