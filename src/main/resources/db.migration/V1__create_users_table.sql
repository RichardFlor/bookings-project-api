CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(120) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    role VARCHAR(30) NOT NULL,
    password VARCHAR(255) NOT NULL,
    password_recovery_code VARCHAR(100),
    email_validated_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL
    );