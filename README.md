# **Uma ferramenta para adicionar dados de uma planilha online.**

## **Sobre o projeto:**

Esse projeto foi voltado a um desafio academico para adicionar regras de autorização e dados de uma planilha online.
Essa implementação compõe as operações de consulta, criação, edição e exclusão de úsuarios e filiais e consulta, importar e exclusão de dados de campanhas.

O projeto demonstra uma transição de uma aplicação model-view-controller para API Rest com arquitetura em camadas moderna.
Ele destaca competências em desenvolvimento backend, incluindo persistência de dados, documentação de endpoints e implementação de testes unitários.

## **Índice**

- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Funcionalidades](#-funcionalidades)
- [Como Executar](#-como-executar)
  - [Pré-requisitos](#pré-requisitos)
  - [Instalação](#instalação)
  - [Executando o Projeto](#executando-o-projeto)
- [Acesso a Endpoints](#acesso-a-endpoints)
- [Licença](#-licença)

## **Tecnologias utilizadas:**

O projeto foi desenvolvido com as seguintes tecnologias:

- **Java 21**
- **Spring Boot 3.3.4**
  - Spring Web
  - Spring Boot DevTools
  - Spring Data JPA
- **PostgreSQL** como banco de dados relacional
- **JUnit 5** e **Mockito** para testes
- **Springdoc OpenAPI** para documentação da API (Swagger UI)
- **Maven** como gerenciador de dependências
- **Lombok** para reduzir boilerplate de código
- **Google Sheets API** - Para importar dados de planilha online.

- **Frontend**:
  - Angular em desenvolvimento..

---

## **Funcionalidades**

1. **Usuário**
   - Criar novo usuário;
   - Buscar todos os usuários;
   - Editar usuário por ID;
   - Excluir usuário por ID.
2. **Filial**
   - Criar nova filial;
   - Buscar todos os filiais;
   - Editar filial por ID;
   - Excluir filial por ID.
   2. **Campanha**
   - Importar Campanha;
   - Buscar campanha por Pagina;
   - Excluir campanha por ID.

## **Como Executar**

### **Pré-requisitos**

Antes de começar, certifique-se de ter instalado:

- **Java 21**
- **Maven**
- Uma IDE como **IntelliJ IDEA** ou **Eclipse**

### **Instalação**

1. Clone este repositório:

```bash
   git clone https://github.com/Poopstoop1/Administrative-tool.git
   cd Administrative-tool
```

2. Configure o banco de dados PostgreSQL:

- Atualize as credenciais no arquivo `application.properties`.

```properties
    # Conexão do banco de dados
    # certifique-se de ter criado um banco Mesa
    spring.datasource.url=jdbc:postgresql://localhost:5432/Mesa
    spring.datasource.username=postgres
    spring.datasource.password=SeuPassordDobanco
    spring.datasource.driver-class-name=org.postgresql.Driver

    # JPA/Hibernate
    spring.jpa.hibernate.ddl-auto=update
    server.servlet.session.persistent=false
    spring.thymeleaf.cache=false

```

3. Compile o projeto:

```bash
   mvn clean install
```

- **Google Sheets API**:

1. Para habilitar o Google Sheets API:

- Acesse Google Cloud na sua conta
- Vá em Menu que está cima no lado esquerdo > API e Serviços > APIs e serviços Ativados > Ativar APIs e Serviços
- Procure por Google Sheets API > Enable or Ativar

2. Criar Credentials.json:

- Em API e Serviços > Criar Credenciais > Contas de serviços
- Adicione nome da conta de serviço > em permissões > continuar > principais com acesso > continuar
- Em API e Serviços clique na conta de serviços que você criou
- Vá em Chaves > Adicionar chave > Criar nova chave > Json > Criar
- Certifique-se que já foi feito o download da credentials, senão crie novamente a chave.

3. Credentials para o projeto

- adicione .env em /administrative-tool
- no env adicione GOOGLE_CREDENTIALS_JSON= ApenasOCaminhoDaSuaCredentials
- se preferivel renomeie o arquivo credentials para credentials.json
- Exemplo de caminho da Credentials: "C:\Users\Danielxpdk\Desktop\Projetos\Administrative-tool\credentials.json"

4. Adicionar planilha no Google Sheets

- Entre com sua contano Google sheets
- crie uma planilha em branco
- Vá em arquivo > Importar > Fazer Upload
- Procure o Planilha-Projeto.xlsx que está no projeto > importar local - substituir planilha
- importar dados

5. Adicionar sua propria planilha na aplicação

- Na planilha online copie o ID da planilha que está na URL
  ex: ![Id da planilha](./Id.JPG)
- Vá para em GoogleSheetsConfigs.java que está em \src\main\java\com\project\Mesa\Config
- No atributo SPREADSHEET_ID atribua o ID da sua planilha.

### Executando o Projeto

- Inicie o servidor Spring Boot:

```bash
   mvn spring-boot:run
```

- A aplicação estará disponível em: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui/index.html#/

---

## Acesso a Endpoints

Para acessar os endpoints, é necessário fazer login. Fiz com que ao dar start no projeto criasse alguns perfis:

- **Administrador**:

  O **Administrador** tem acesso aos dados de todas as filiais. Ele pode adicionar, editar e excluir filiais, além de gerenciar usuários. Ou seja, é o administrador quem adiciona, edita e exclui os usuários do sistema e importa os dados da planilha.

  ```bash
  username: ErysonMoreira
  senha: X#x123456
  ```

- **Usuarios**:

  Os Usuarios têm acesso apenas a listar os dados, no aplicativo eles terão apenas acesso aos dados da filiais deles.

  ```bash
  username: CarlaFerreira
  Senha: X#x123456

  username: GabrielLima
  Senha: X#x123456

  username: MarianaSoares
  Senha: X#x123456
  ```

> **⚠️ Atenção:**  
> **Não exclua os usuários já criados**, pois eles são necessários para que a aplicação funcione corretamente.

## Licença

Este projeto está licenciado sob a Licença MIT.
Contribuições são bem vindas! Por favor abra um issue or realize um pull request.
