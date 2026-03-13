# Bookings Project API

API para gerenciamento de reservas.

## 🔐 Credenciais de acesso

### Admin

Use as credenciais abaixo para acessar como administrador:

Email: [admin@admin.com](mailto:admin@admin.com)
Senha: string

### Customer

Para acessar como **customer**, é necessário criar um usuário pela rota **create user**.

⚠️ Utilize um **email válido**, pois será necessário **confirmar o email** para conseguir acessar a aplicação.

---

# 🗄️ Configuração do Banco de Dados

Caso rode a aplicação **sem Docker**, é necessário criar manualmente um banco no **PostgreSQL** com o nome:

```
bookings-project-db
```

Credenciais do banco:

```
DB_USERNAME=postgres
DB_PASSWORD=Senai@127
```

---

# ⚙️ Variáveis de Ambiente

Adicione na sua IDE a seguinte variável de ambiente:

```
PROFILE=dev
```

---

# 📄 Documentação da API (Swagger)

Após iniciar a aplicação, acesse:

```
http://localhost:8080/api/v1/swagger-ui/index.html
```

---

# 🐳 Rodando com Docker

Caso prefira rodar com Docker:

No terminal execute:

```
docker compose up --build
```

Após subir os containers, acesse o Swagger em:

```
http://localhost:8080/api/v1/swagger-ui/index.html
```


