--liquibase formatted sql

--changeset andreiv:1
CREATE TABLE IF NOT EXISTS lot_status(
    id INT PRIMARY KEY NOT NULL,
    lot_status VARCHAR(50) NOT NULL
);

--changeset andreiv:2
CREATE TABLE IF NOT EXISTS users(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    birth_date DATE,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(500) NOT NULL,
    role VARCHAR(50) NOT NULL,
    user_status VARCHAR(50) NOT NULL,
    activation_code VARCHAR(120)
);

--changeset andreiv:3
CREATE TABLE IF NOT EXISTS lot(
    id BIGSERIAL PRIMARY KEY,
    lot_name VARCHAR(70) NOT NULL,
    lot_owner VARCHAR(50) NOT NULL,
    start_bid INT NOT NULL,
    status_id INT NOT NULL,
    lot_buyer VARCHAR(50),
    FOREIGN KEY (lot_owner) REFERENCES users(username),
    FOREIGN KEY (status_id) REFERENCES lot_status(id)
);

--changeset andreiv:4
CREATE TABLE IF NOT EXISTS bid(
    id BIGSERIAL PRIMARY KEY,
    lot_name VARCHAR(70) NOT NULL,
    lot_id BIGINT NOT NULL,
    username VARCHAR(50) NOT NULL,
    user_bid INT,
    FOREIGN KEY (lot_id) REFERENCES lot(id)
);

--changeset andreiv:5
INSERT INTO lot_status(id, lot_status)
VALUES (1, 'SELL'),
       (2, 'SOLD'),
       (3, 'NOT_SOLD');
