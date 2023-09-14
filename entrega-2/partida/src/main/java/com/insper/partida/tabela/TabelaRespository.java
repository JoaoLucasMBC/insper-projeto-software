package com.insper.partida.tabela;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TabelaRespository extends MongoRepository<Tabela, String> {

    Tabela findByIdentifier(String identifier);

    Tabela findByNome(String nome);

}
