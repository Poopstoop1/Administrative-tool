## ChangeLog

Todas as mudanças recentes da versão 2 deste projeto serão documentadas neste arquivo.

### v2.0.1 (Task 1: Converter projeto MVC para API Rest )

- Refatorar UsuarioController, CampanhasController, DashboardController
  FilialController para Rest.
- Converter LoginController para Rest.
- Adicionar Lombok
- Adicionar a classe FilialService;
- Adicionar a classe CampanhasService;
- Adicionar a classe UsuarioService;
- Adicionar a classe DashboardService;
- Adicionar parte de arquitetura de camadas Mapper, DTO e Exception
- Adicionado tratamento global de exceções.
- Configurar Spring Security para API Rest
- Remover Thymeleaf

### v2.0.3 (Task 2 : Aplicar SRP E OCP do conceito Solid)

- Adicionar a interface UsuarioValidation com método para validar usuário;
- Adicionar a classe UsuarioValidator como orquestrador das validações do campo de usuário conforme OCP;
- Adicionar as classes NomeValidato com sua propria logica de validação conforme OCP;
- Adicionar a classe UsuarioExistenteValidator que evita repetição do código de busca de usuário para os métodos de buscar, editar e remover;
- Adicionar a exceção personalizada UsuarioValidationException para tratar das validações dos atributos do usuário;
- Adicionar a classe UsuarioMapperImpl para atualizar os campos do usuário, retirando esse trabalho do service conforme SRP;
- Adicionar a interface UsuarioMapper com os métodos de manipulação e mapeio do objeto Usuario tornando extensivel conforme OCP;
- Adicionar a classe UsuarioService delegando as funções de validação e mapeio para suas devidas classes conforme SRP;
- Adicionar validação para emails já existentes no método de adicionar novo usuário;
- Adicionar teste unitário para testar o fluxo completo criando um novo usuário do desafio Extra.

- Interface FilialValidation para padronizar validações;
- Classe FilialValidator como orquestrador das regras de validação (OCP);
- Classe CnpjFilialValidator para validação de CNPJ da filial;
- Classe FilialExistenteValidator para evitar duplicação de lógica de busca;
- Adiciona interface FilialMapper para manipulação e conversão de dados (OCP);
- Adiciona a classe FilialService delegando validações e mapeamentos (SRP);

- Interface CampanhaValidation para padronizar validações;
- Classe CampanhaValidator como orquestrador das validações (OCP);
- Classe MetaValidator para validar consistência entre meta e realizado;
- Classe ParticipanteValidator para validar dados do participante;
- Classe CampanhaExistenteValidator para centralizar busca de campanhas;
- CampanhaMapper para abstração de mapeamento (OCP);
- Adiciona a classe CampanhaService delegando validações e mapeamento (SRP);

### v2.0.3 (Task 3: Criar testes unitarios)

- Adicionar Testes unitarios para a classe UsuarioService
- Adicionar Testes unitarios para a classe FilialService
- Adicionar Testes unitarios para a classe CampanhasService

### v2.0.4 (Task 4 : Atualizações do projeto)

- Atualizar a documentação do projeto.
- Habilitar Swagger UI no projeto
- Adicionar Recuperação de Senha

### v2.0.5 (Task 5: Atualizações do Frontend da aplicação)

- Remover tags do thymeleaf.
- Adicionar Alpine.js para consumo das API Rest.
- Adicionar tratamento de mensagens de erro retornadas pelo backend.
- Corrigir tabela de adição e edição de usuarios
- Corrigir tabela de adição e edição de filial
- Adicionar formatação de CNPJ na exibição da tabela Filial.

Versão 3

### v3.0.1 (Task 6: Usar Bean Validator e Refatorar Validators)

- Adicionar Spring Validator
- Adicionar Anotação Notblank, Pattern, Size a UsuarioRequestDTO
- Adicionar Anotação Notblank, Pattern, Size a FilialRequestDTO
- Adicionar Anotação Notblank, Pattern, Size a CampanhasRequestDTO
- Refatorar Classes na Validations
