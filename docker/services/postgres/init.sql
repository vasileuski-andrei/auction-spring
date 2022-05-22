CREATE TABLE IF NOT EXISTS lot_status(
    id INT PRIMARY KEY NOT NULL,
    lot_status VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS users(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    birth_date DATE NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(120) NOT NULL,
    role VARCHAR(50) NOT NULL,
    user_status VARCHAR(50) NOT NULL,
    activation_code VARCHAR(120)
);

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

CREATE TABLE IF NOT EXISTS bid(
    id BIGSERIAL PRIMARY KEY,
    lot_name VARCHAR(70) NOT NULL,
    lot_id BIGINT NOT NULL,
    username VARCHAR(50) NOT NULL,
    user_bid INT,
    FOREIGN KEY (lot_id) REFERENCES lot(id)
);

INSERT INTO lot_status(id, lot_status) VALUES (1, 'SELL');
INSERT INTO lot_status(id, lot_status) VALUES (2, 'SOLD');
INSERT INTO lot_status(id, lot_status) VALUES (3, 'NOT_SOLD');

INSERT INTO users(id, username, birth_date, email, password, role, user_status)
VALUES(1, 'admin', '2022-03-11', 'admin@admin.com', '$2a$12$o3lI0qeOG9h.B7RFkoUDLOY9VJa/Ih5Hl1Aa34.ATNb1tTrogOCnu', 'USER', 'ACTIVE');

INSERT INTO users(id, username, birth_date, email, password, role, user_status)
VALUES(2, 'TestUser', '2022-03-13', 'test@test.com', '$2a$12$o3lI0qeOG9h.B7RFkoUDLOY9VJa/Ih5Hl1Aa34.ATNb1tTrogOCnu', 'USER', 'ACTIVE');

INSERT INTO lot(id, lot_name, lot_owner, start_bid, status_id)
VALUES(1, 'TestLot', 'admin', 1, 1);