CREATE TABLE rental_types (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL,
    description VARCHAR(150),
    created_at TIMESTAMP NOT NULL
);