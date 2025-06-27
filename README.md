# 🚀 Desafio Tecnico 

API REST para gerenciamento de **Clientes** e suas **Contas**, feita em Java com Spring Boot, JPA/Hibernate e PostgreSQL.

O projeto foi feito para solucionar desafio proposto para testar meus conhecimentos na area relativa ao mesmo

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

- **IDE Java (opcional, mas recomendada)**  
  Exemplos: IntelliJ IDEA, Eclipse, VSCode com plugin Java.

- **Postman (ou qualquer cliente REST)**  
  Para testar e consumir os endpoints da API 

- **Navegador Web**  
  Para acessar endpoints GET diretamente via URL.

---

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

## 📖 Documentação Swagger

Acesse a documentação interativa da API em:  
📄 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)  
ou  
📄 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) (Springdoc)


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
   spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update

### Clone o projeto
1. git clone https://github.com/seu-usuario/DesafioTecnico.git
   
### Acessar pasta no projeto
2. cd DesafioTecnico
   
### Construir o projeto com Maven
3. mvn clean install

### Executar a aplicação
4. mvn spring-boot:run

---

## 🧪 Usando o Postman ou outra ferramenta REST (Recomendado para POST, PUT, DELETE):

1. Abra o Postman.
2. Escolha o método HTTP (POST, PUT, DELETE).
3. Insira a URL (exemplo: `http://localhost:8080/clientes`).
4. Na aba **Body**, selecione **JSON**.
5. Insira o JSON conforme os exemplos abaixo.
6. Clique em **Send**.
7. Veja a resposta do servidor no painel abaixo.

---

## 🌐 Endpoints da API

### Cliente

   ```cliente
  GET /clientes - Lista todos os clientes cadastrados

  POST /clientes - Cadastra um novo cliente

  PUT /clientes/{id} - Atualiza os dados de um cliente pelo ID

  DELETE /clientes/{id} - Exclui o cliente e cancela todas as suas contas  
  ```
### Conta

  ```conta
  GET /clientes/{clienteId}/contas - Lista todas as contas do cliente informado

  POST /clientes/{clienteId}/contas - Cria uma nova conta para o cliente

  PUT /contas/{id} - Atualiza os dados de uma conta (exceto para CANCELADA)

  PATCH /contas/{id}/cancelar - Cancela logicamente a conta (define situação como CANCELADA)
  ```
---

## 🧪 Usando o Postman ou outra ferramenta REST (Recomendado para POST, PUT, DELETE):

1. Abra o Postman.
2. Escolha o método HTTP (POST, PUT, DELETE).
3. Insira a URL (exemplo: `http://localhost:8080/clientes`).
4. Na aba **Body**, selecione **JSON**.
5. Insira o JSON conforme os exemplos abaixo.
6. Clique em **Send**.
7. Veja a resposta do servidor no painel abaixo.


---


## 🔍 Exemplo prático

### Criar cliente via Postman

- **Método:** POST  
- **URL:** `http://localhost:8080/clientes`  
- **Body JSON:**
```json
{
"nome": "Ana Paula",
"cpf": "12345678900",
"telefone": "999999999",
"email": "ana@example.com"
}
