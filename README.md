# Contas a pagar API REST
API simples de um sistema de contas a pagar.

O sistema permitirá realizar o CRUD de uma conta a pagar, alterar a situação dela quando for efetuado pagamento, obter informações sobre as contas cadastradas no banco de dados, e importar um lote de contas de um arquivo CSV.

# Instruções de iniciais
1. Clonar o projeto
2. Rodar com usando Docker compose ( docker-compose up -d )
3. Acessar http://localhost:8080/swagger-ui/index.html no navegador
4. Credenciais: usuário: username senha: password 
5. IMPORTANTE: 
- para simplificar fica **definido** que o arquivo CSV a ser importado usa "," como delimitador de campos;
- não tem cabeçalho.

## Checklist
Este é o checklist desse de API REST para um sistema simples de contas a pagar.

### Requisitos Gerais

- [x] Deve ser utilizado Java, versão 17 ou superior.
- [x] Utilizar Spring Boot.
- [x] Utilizar o banco de dados PostgreSQL.
- [x] A aplicação deve ser executada em um container Docker.
- [x] A aplicação, banco de dados e outros serviços necessários para executar a aplicação devem ser orquestrados usando Docker Compose.
- [x] O código do projeto deve ser hospedado no GitHub ou GitLab.
- [x] Utilizar mecanismo de autenticação.
- [x] A organização do projeto deve seguir Domain Driven Design.
- [x] Utilizar Flyway para criar a estrutura de banco de dados.
- [x] Utilizar JPA.
- [x] Todas as APIs de consulta devem ser paginadas.

### Requisitos Específicos

- [x] Criar uma tabela no banco de dados para armazenar as contas a pagar com os seguintes campos:
    - id
    - data_vencimento
    - data_pagamento
    - valor
    - descricao
    - situacao
- [x] Implementar a entidade "Conta" na aplicação, de acordo com a tabela criada anteriormente.
- [ ] Implementar as seguintes APIs:
- [x] Cadastrar conta;
- [x] Atualizar conta;
- [x] Alterar a situação da conta;
- [X] Obter a lista de contas a pagar, com filtro de data de vencimento e descrição;
- [x] Obter conta filtrando pelo id;
- [X] Obter valor total pago por período.
- [x] Implementar mecanismo para importação de contas a pagar via arquivo CSV.
    - O arquivo será consumido via API.
- [x] Implementar testes unitários.


PS. Aprecio sugestões.