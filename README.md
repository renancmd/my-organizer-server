# 🗂️ My Organizer (Back-end)

## 📝 Descrição

**My Organizer** é uma aplicação do tipo **To-Do List** focada no minimalismo. Permite ao usuário cadastrar tarefas com **nome**, **descrição** e **data**, organizando seu dia de forma simples e funcional.

Este repositório contém exclusivamente o código do **back-end**, desenvolvido em Java com Spring Boot e banco de dados MySQL.

> 💡 Para acessar a interface de usuário (front-end), veja o repositório do [My Organizer Front-end](https://github.com/renancmd/my-organizer-client).

---

## 💻 Tecnologias Utilizadas

- [Java 21](https://www.oracle.com/java/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [MySQL](https://www.mysql.com/)
- [Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/)
- JWT para autenticação
- BCrypt para criptografia de senhas

---

## 🧠 Arquitetura do Projeto

O back-end segue uma arquitetura em camadas:

- `config` – Configurações gerais (CORS, segurança)
- `controller` – Endpoints REST
- `dto` – Objetos de transferência de dados
- `model` – Entidades do sistema
- `repository` – Comunicação com o banco de dados
- `security` – Autenticação JWT, filtros e permissões
- `service` – Lógica de negócio

---

## ⚙️ Como Executar

### Pré-requisitos

- Java 21
- MySQL
- Maven
- Docker (opcional)

### Rodando localmente

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/my-organizer-backend
cd my-organizer-backend

# Compile e execute
./mvnw spring-boot:run
```

### Usando Docker

```bash
docker-compose up
```

> Configure variáveis de ambiente (`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`) ou ajuste o arquivo `application.properties`.

---

## 🔐 Principais Endpoints da API

| Método | Endpoint           | Descrição                       |
|--------|--------------------|----------------------------------|
| POST   | `/auth/register`   | Criação de novo usuário         |
| POST   | `/auth/login`      | Login e geração de token        |
| GET    | `/user/me`         | Exibe dados do usuário          |
| PUT    | `/user/update`     | Atualização do usuário          |
| PUT    | `/user/passowrd`   | Atualiza senha do usuário       |
| DELETE | `/user/delete`     | Exclusão de usuário             |
| GET    | `/tasks`           | Listagem de tarefas             |
| POST   | `/tasks`           | Criação de nova tarefa          |
| PUT    | `/tasks/{id}`      | Atualização de tarefa           |
| DELETE | `/tasks/{id}`      | Exclusão de tarefa              |
| GET    | `/tasks/{id}`      | Exibe tarefa selecionada        |
| PUT    | `/tasks/{id}/done` | Define tarefa como concluída    |
---

## 🚧 Status do Projeto

✅ Finalizado, mas com planos de aprimoramento futuro.

---

## 📈 Melhorias Futuras

- Testes automatizados
- Validações avançadas
- Caching com Redis

---

## 📄 Licença

Este projeto está licenciado sob a **Licença MIT**.