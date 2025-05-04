# Authenticator API

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-336791?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

Este projeto é uma API de autenticação desenvolvida com Java, Spring Boot, PostgreSQL e JWT, funcionando como um provedor de identidade (IDP). O sistema permite o registro e login de usuários, além da verificação de email, com geração de tokens JWT para controle de autenticação e autorização.

---

## Table of Contents

- [Instalação](#instalação)
- [Uso](#uso)
- [API Endpoints](#api-endpoints)
- [Autenticação](#autenticação)
- [Database](#database)
- [Contribuindo](#contribuindo)

---

## Instalação

1. Clone o repositório:

```bash
git clone https://github.com/rogeriioms/authenticator.git
```

2. Instale as dependências com o Maven:

```bash
mvn install
```

3. Configure o banco de dados PostgreSQL e ajuste as variáveis de ambiente conforme necessário.

---

## Uso

1. Inicie a aplicação com o Maven:

```bash
mvn spring-boot:run
```

2. A API estará acessível em: [http://localhost:8080](http://localhost:8080)

---

## API Endpoints

### Registro de Usuário

`POST /api/auth/register`

```json
{
  "name": "João Silva",
  "email": "joao@example.com",
  "password": "senhaSegura123"
}
```

**Response:**

```json
{
  "message": "Usuário registrado. Verifique seu email para ativar a conta."
}
```

---

### Login

`POST /api/auth/login`

```json
{
  "email": "joao@example.com",
  "password": "senhaSegura123"
}
```

**Response:**

```json
{
  "token": "jwt-token",
  "redirect_url": "https://appcliente.com/dashboard"
}
```

---

### Verificação de Email

`GET /api/auth/verify-email?token=<verification_token>`

**Response:**

```json
{
  "message": "Email verificado com sucesso."
}
```

---

## Autenticação

Após o login, o token JWT deve ser incluído no cabeçalho das requisições protegidas:

```
Authorization: Bearer <jwt-token>
```

A autenticação é gerenciada com Spring Security e o token tem validade definida por configuração.

---

## Database

Este projeto utiliza **PostgreSQL**. As tabelas principais incluem:

- **Users**
  - `id` (UUID)
  - `name`
  - `email` (único)
  - `password` (hash com bcrypt)
  - `is_verified` (boolean)

Migrações podem ser gerenciadas com Flyway.

---

## Contribuindo

Contribuições são bem-vindas!

Se encontrar bugs ou quiser propor melhorias, abra uma *issue* ou envie um *pull request*. Siga as [convenções de commit](https://www.conventionalcommits.org/pt-br/v1.0.0/) e mantenha o código limpo.

---
