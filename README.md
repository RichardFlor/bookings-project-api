# Bookings Project API

API REST para gerenciamento de reservas de tipos de locação.
Desenvolvida com **Java 21 Spring Boot**, utilizando **JWT Authentication**, **Flyway Migrations**, **Docker**, e **PostgreSQL**.

---

# Tecnologias utilizadas

* Java 21
* Spring Boot
* Spring Security
* JWT Authentication
* PostgreSQL
* Flyway
* Docker
* Docker Compose
* Swagger / OpenAPI
* Maven

---

## ⚠️ Requisitos de ambiente

Para rodar o projeto é necessário ter instalado:

### Docker 🐳 
```
 Docker
 Docker Compose
```

---

# Como rodar o projeto

Clone o repositório:

```bash
git clone https://github.com/RichardFlor/bookings-project-api.git
cd bookings-project-api
```

---

## 🐳 Rodando com Docker

Execute no terminal:

```bash
docker compose up --build
```

A aplicação ficará disponível em:

```
http://localhost:8080
```

Swagger:

```
http://localhost:8080/api/v1/swagger-ui/index.html
```

---

## 📄 Documentação da API

A documentação interativa da API está disponível via Swagger:

```
http://localhost:8080/api/v1/swagger-ui/index.html
```

---

## 🔐 Autenticação

A API utiliza **JWT Authentication**.

## Credenciais de Admin

Para acessar como administrador:

```
Email: admin@admin.com
Senha: string
```

## ⚠️ Acesso como Customer

Para acessar como **customer**:
 ```
  1. Crie um usuário pela rota **create user**
  2. Utilize um **email válido**
  3. Confirme o **email de verificação**
  4. Após confirmar o email será possível realizar login na aplicação
```
---

## 🗄️ Migrações de Banco

As migrações são gerenciadas automaticamente pelo **Flyway** ao iniciar a aplicação.

---

