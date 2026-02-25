🚀 Portal de Serviço ST - Backend
Sistema de gerenciamento e aprovação de empresas desenvolvido para a Avaliação 2026. O projeto foca no cadastro de pessoas jurídicas, físicas e estrangeiras, com fluxos de aprovação diferenciados entre usuários internos e externos.

🛠️ Tecnologias Utilizadas
Java 17: Linguagem principal.
Spring Boot 3: Framework para construção da API REST.
H2 Database: Banco de dados em memória para desenvolvimento e testes rápidos.
Spring Data JPA / Hibernate: Para persistência e mapeamento objeto-relacional.


Bean Validation (Jakarta): Validação de integridade de dados (CPF, CNPJ, NotNull).
Lombok: Redução de código boilerplate.

Regras de Negócio Implementadas
O sistema segue rigorosamente as especificações do documento de requisitos:
[RN01]: Cadastros realizados por usuários internos são aprovados automaticamente.
[RN02]: Cadastros realizados por usuários externos ficam com status PENDENTE aguardando aprovação.
[RN03]: Integrações via Siscomex exigem atribuição de um responsável externo após a aprovação.
Validações: Implementação de algoritmos de Módulo 11 para garantir a validade real de CPFs e CNPJs.

Banco de Dados e Carga Inicial
O projeto utiliza o arquivo src/main/resources/data.sql para popular o banco H2 automaticamente ao iniciar.

Como Executar o Projeto
1. Certifique-se de ter o JDK 17, Maven e o banco H@2 instalados.

2. Clone o repositório:
git clone https://github.com/seu-usuario/portal-st-backend.git

3. Execute a aplicação:
4. O backend estará disponível em http://localhost:8080.
5. Console H2: Acesse http://localhost:8080/h2-console para visualizar as tabelas.
   
JDBC URL: jdbc:h2:mem:testdb

User: sa

Password: (vazio)
   
