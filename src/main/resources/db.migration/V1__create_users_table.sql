CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    role VARCHAR(50) NOT NULL,
    name VARCHAR(120) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    cpf VARCHAR(14) UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    password_recovery_code VARCHAR(6),
    email_validated_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP
);