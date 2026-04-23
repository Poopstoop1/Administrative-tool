## ChangeLog

Todas as mudanças recentes da versão 2 deste projeto serão documentadas neste arquivo.
a versão 2 tem como objetivo Converter projeto MVC para API Rest

### v2.0.0 (Task 1: Refatorando e Ajustando o Projeto )

- Adicionar Lombok
- Adicionar a classe FilialService;
- Adicionar a classe CampanhasService;
- Adicionar a classe UsuarioService;
- Adicionar parte de arquitetura de camadas Mapper, DTO e Exception
- Adicionado tratamento global de exceções.
- Configurar Spring Security para API Rest
- Adicionar Liçença MIT
- Remover Thymeleaf
- Adicionar JWT E Filter para Spring

### v2.1.0 (Task 2 : Aplicar SRP E OCP do conceito Solid)

- Adicionar a interface BaseValidation método como Base para validar as entidades;
- Adicionar a classe UsuarioValidator como orquestrador das validações do campo de usuário conforme OCP;
- Adicionar as classes NomeValidator, LoginValidator, PasswordValidator com sua propria logica de validação conforme OCP;
- Adicionar a classe UsuarioMapperImpl para atualizar os campos do usuário, retirando esse trabalho do service conforme SRP;
- Adicionar a interface UsuarioMapper com os métodos de manipulação e mapeio do objeto Usuario tornando extensivel conforme OCP;
- Adicionar a classe UsuarioService delegando as funções de validação e mapeio para suas devidas classes conforme SRP;

- Classe CnpjValidator para validação de CNPJ da Filial para Filial, Usuario e Campanha;
- Classe CNPJExistenteValidator para evitar duplicação de lógica de busca;

- Classe FilialValidator como orquestrador das regras de validação (OCP);
- Adiciona interface FilialMapper para manipulação e conversão de dados (OCP);
- Adiciona a classe FilialService delegando validações e mapeamentos (SRP);
- Adicionar a classe FilialMapperImpl para atualizar os campos do usuário, retirando esse trabalho do service conforme SRP;

- Classe CampanhaValidator como orquestrador das validações (OCP);
- CampanhaMapper para abstração de mapeamento (OCP);
- Adiciona a classe CampanhaService delegando validações e mapeamento (SRP);
- Adicionar a classe CampanhaMapperImpl para atualizar os campos do usuário, retirando esse trabalho do service conforme SRP;

- Refatorar UsuarioController, CampanhasController, FilialController para Rest.
- Converter LoginController para API Rest.
- Refatorar CampanhaRepository

- Refatorar GoogleSheetService delegando validações e mapeamentos necessarios.

### v2.2.0 (Task 3: Criar testes unitarios)

- Adicionar Testes unitarios para a classe UsuarioService
- Adicionar Testes unitarios para a classe FilialService
- Adicionar Testes unitarios para a classe CampanhasService

### v2.3.0 (Task 4 : Atualizações do projeto)

- Atualizar a documentação do projeto.

### v2.4.0 (Task 5: Migração de Frontend para Angular)

- Criar projeto Angular
- Configurar rotas
- Configurar environment (API URL)
- Instalar libs básicas
- AuthService

- Tela de login
- Integração com APIs
- Criar Módulo Usuario
- Criar Módulo Filial
- Criar Módulo Campanhas
- Criar Módulo Dashboard

### v3.0.1 (Task 6: Usar Bean Validator e Refatorar Validators)

- Adicionar Spring Validator
- Adicionar Anotação Notblank, Pattern, Size a UsuarioRequestDTO
- Adicionar Anotação Notblank, Pattern, Size a FilialRequestDTO
- Adicionar Anotação Notblank, Pattern, Size a CampanhasRequestDTO
- Refatorar Classes na Validations
