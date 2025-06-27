# 🚀 Desafio Tecnico 

API REST para gerenciamento de **Clientes** e suas **Contas**, feita em Java com Spring Boot, JPA/Hibernate e PostgreSQL.

O projeto foi feito para solucionar desafio proposto para testar meus conhecimentos em **java**, **Spring Boot** e **Banco de dados**

---

## 🧑‍💻 Tecnologias usadas e necessárias para rodar o projeto

Para executar este projeto localmente, você precisará das seguintes tecnologias e ferramentas instaladas e configuradas:

- **Java 8 ou superior**  
  Ambiente de execução 

- **Maven**  
  Gerenciador de dependências

- **Spring Boot**  
  Framework Java para desenvolvimento da API REST.

- **Banco de Dados PostgreSQL**


  Armazena e gerencia os dados da aplicação.  

- **IDE Java (opcional, mas recomendada)**  
  Exemplos: IntelliJ IDEA, Eclipse, VSCode com plugin Java.

- **Postman (ou qualquer cliente REST)**  
  Para testar e consumir os endpoints da API 

---

## 📚 Bibliotecas

- **spring-boot-starter-web**  
  Framework pra criar API REST e apps web.

- **spring-boot-starter-data-jpa**  
  Pra usar JPA e mexer com banco.

- **spring-boot-starter-validation**  
  Validação de dados com anotações.

- **spring-boot-starter-actuator**  
  Monitoramento do app.

- **h2 (escopo test)**  
  Banco em memória só pros testes.

- **postgresql (escopo runtime)**  
  Driver pra conectar no PostgreSQL.

- **lombok (escopo provided)**  
  Gera getter/setter e outros no automático.

- **springdoc-openapi-starter-webmvc-ui**  
  Documentação automática da API (Swagger).

- **spring-boot-devtools (runtime opcional)**  
  Reinício automático no desenvolvimento.

- **mockk (escopo test)**  
  Biblioteca pra mocks nos testes.

- **spring-boot-starter-test (escopo test)**  
  Ferramentas de teste, sem Mockito pra não bater com mockk.


## 📄 Entidades principais

### 👤 Cliente

- `id`: Long (chave primária)
- `nome`: String (obrigatório)
- `cpf`: String (único e obrigatório)
- `telefone`: String (opcional)
- `email`: String (opcional)

### 💳 Conta

- `id`: Long (chave primária)
- `referencia`: String (obrigatório)
- `valor`: BigDecimal (>= 0)
- `situacao`: Enum (`PENDENTE`, `PAGA`, `CANCELADA`)
- `cliente`: Cliente (relacionamento ManyToOne)

---

## ⚙️ Regras de negócio importantes

- CPF deve ser único
- Valor da conta não pode ser negativo
- Conta não pode ser criada com situação `CANCELADA`
- Conta não pode ser cancelada duas vezes
- Atualização da situação para `CANCELADA` só por método específico (cancelamento lógico)
- Ao excluir cliente, todas as contas são canceladas

---

## 🛠️ Como rodar o projeto

### Configuração do banco de dados

No arquivo `src/main/resources/application.properties` (ou `.yml`), configure:

1. Configure seu banco PostgreSQL e crie um banco para o projeto.
2. Ajuste as configurações do banco em `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/SEU_BANCO
   spring.datasource.username=SEU_USUARIO
   spring.datasource.password=SUA_SENHA
   spring.jpa.hibernate.ddl-auto=update
   
 **Clone o projeto**

1. git clone https://github.com/CaioCesar2005/desafio-tecnico.git
   
 **Acessar pasta no projeto**

2. cd desafio-tecnico
   
 **Construir o projeto com Maven**

3. mvn clean install

 **Executar a aplicação**

4. mvn spring-boot:run

---

# 📖 Documentação Swagger

### Com essa ferramenta voce tem acesso a mapemanto de end points e parametros do controller de forma interativa e agradavel visualmente


Apos executar o projeto com
  ```
  mvn spring-boot:run
  ``` 
Acesse a documentação interativa da API em:  
 
    📄 http://localhost:8080/swagger-ui.html
     ou  
    📄 http://localhost:8080/swagger-ui/index.html (Springdoc)

---

## 🧪 Usando o Postman

1. Abra o Postman.
2. Escolha o método HTTP (POST, PUT, DELETE).
3. Insira a URL (exemplo: `http://localhost:8080/clientes`).
4. Na aba **Body**, selecione **JSON**.
5. Insira o JSON conforme os exemplos abaixo.
6. Clique em **Send**.
7. Veja a resposta do servidor no painel abaixo.




---

## ✅ Diferenciais e Melhorias Extras

Ao longo do desenvolvimento deste projeto, foram implementadas diversas práticas e melhorias que vão além do escopo básico esperado. Abaixo estão listados os principais diferenciais:

**Validações Extras**
- Utilização de anotações de validação do Bean Validation (`@NotBlank`, `@Email`, `@Size`, etc.) em DTOs.
- Regras de negócio aplicadas na camada de serviço, garantindo integridade dos dados.

**Testes**
- Testes unitários para os serviços, validando regras de negócio.
- Testes de integração com o banco de dados utilizando o perfil de teste e H2 em memória.

**Documentação com Swagger**
- Integração do **Springdoc OpenAPI** para geração automática da documentação da API.
- DTOs anotados com `@Schema` e controllers organizados com `@Tag`, `@Operation` e `@ApiResponse`.

**Uso de DTOs e Mapeamento**
- Criação de DTOs para entrada e saída de dados (Request/Response).
- Mapeamento e conversão dos DTOs em entidades de forma segura e limpa

**Tratamento Global de Erros**
- Implementação de `@ControllerAdvice` para captura e personalização de exceções.
- Respostas padronizadas com status HTTP apropriados e mensagens claras para o cliente.

**Arquitetura Limpa e Camadas Separadas**
- Estrutura baseada em camadas (Controller, Service, Repository, Model, DTO, Exception).
- Padrão de projeto adotado facilita manutenção, testes e escalabilidade.
- Nomeclatura correta de classes, metodos, arquivos, packages e atributos

**Commits atômicos**
- Padrão usado para melhorar desenvolvimento e planejamento do projeto
- Minimizando imprevistos e mantendo organização do repositório remoto e seu histórico, facilitando identificação de erros, sendo possivel um "backup" limpo quando necessário

---

## 🔍 Testes Unitários

### ClienteService

 **Cadastrar cliente**
- Cadastra cliente com sucesso
- Lança exceção se CPF já existe

 **Atualizar cliente**
- Atualiza cliente existente
- Lança exceção se cliente não encontrado

 **Listar clientes**
- Retorna lista de clientes

 **Excluir cliente**
- Exclui cliente e cancela contas associadas
- Lança exceção se cliente não encontrado

---

### ContaService

 **Cadastrar conta**
- Cadastra conta com sucesso
- Lança exceção se valor negativo
- Lança exceção se situação for CANCELADA ao cadastrar
- Lança exceção se cliente não encontrado

 **Cancelar conta logicamente**
- Cancela conta PENDENTE com sucesso
- Lança exceção se conta já estiver CANCELADA
- Lança exceção se conta não encontrada

 **Atualizar conta**
- Atualiza valor da conta com sucesso
- Lança exceção se valor negativo ao atualizar
- Lança exceção se tentar atualizar situação para CANCELADA por esse método
- Lança exceção se conta não encontrada

--- 
## 🔍 Testes de integração 

### ClienteController
- **POST /clientes**: cria um cliente.
- **PUT /clientes/{id}**: atualiza um cliente existente.
- **DELETE /clientes/{id}**: exclui um cliente.
- **GET /clientes**: lista todos os clientes.
- **GET /clientes/{id}**: busca um cliente pelo ID.

### ContaController
- **POST /clientes/{id}/contas**: cria uma conta para o cliente.
- **PUT /contas/{id}**: atualiza uma conta existente.
- **DELETE /contas/{id}**: cancela (logicamente) uma conta.
- **GET /clientes/{id}/contas**: lista todas as contas de um cliente.
