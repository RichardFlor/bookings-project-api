CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    email_validated_at TIMESTAMP,
    role SMALLINT NOT NULL,
    password VARCHAR(255) NOT NULL,
    password_recovery_code VARCHAR(10)
)