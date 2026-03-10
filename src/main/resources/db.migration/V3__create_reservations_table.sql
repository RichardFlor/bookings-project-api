CREATE TABLE IF NOT EXISTS reservations (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    rental_type_id UUID NOT NULL,
    reservation_date DATE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_reservation_user
    FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE RESTRICT,

    CONSTRAINT fk_reservation_rental_type
    FOREIGN KEY (rental_type_id)
    REFERENCES rental_types(id)
    ON DELETE RESTRICT,

    CONSTRAINT uk_rental_date
    UNIQUE (rental_type_id, reservation_date)
);