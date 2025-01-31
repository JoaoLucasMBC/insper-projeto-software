# insper-projeto-software

### **Entrega 1**

* Implementação completa em MongoDB
* Tanto no Game quanto no Bet
* Coleção do Postman disponível para download (só ir brincando com os parâmetros e as respostas, pois não está em ordem necessariamente)
* Disponível na pasta `entrega-1`
* Apenas não foram resolvidas as questões que o professor indicou que não era preciso:  
  * As duas Query's de DB sequencial que ainda não aprendemos como adaptar
  * A função verify_bet que não é necessária para o projeto atual


### **Entrega 2**

* Implementação completa da Tabela em banco de dados Mongo
* Documento Tabela + DTO
* Atualização das tabelas na função `edit_game`  
* Rota `/tabela` com resposta por volta de 40ms
* Disponível na pasta `entrega-2`

### **Entrega 3**

* Implementação de GameControllerAdvice e TeamControllerAdvice
* Tratamento de erros no GameService e TeamService nos campos indicados pelo enunciado
* Erros GameDoesNotExist, GameTeamsEqual, TeamDoesNotExist, TeamAlreadyExists personalizados
* Disponível na pasta `entrega-3`


### **Entrega 4**

![Coverage](.github/badges/jacoco.svg)  
* Implementação de CI/CD
* Check de build a todo push e PR na main
* Deploy automático no server AWS
* Check da coverage ser acima de 20%
* Apre PR com nova badge
* Implementação de testes para +20%
* Disponível na pasta `entrega-4`

### **Entrega 5**

* Implementação do serviço de User/Login
* Utilização de Tokens
* Aplicação de Partida checa token do usuário com filtro antes de todos os requests
* DTO's e Filters completos
* Disponível na pasta `entrega-5`
