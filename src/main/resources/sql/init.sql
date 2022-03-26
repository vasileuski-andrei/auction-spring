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
    user_status VARCHAR(50) NOT NULL
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
    lot_id INT NOT NULL,
    username VARCHAR(50) NOT NULL,
    user_bid INT,
    FOREIGN KEY (lot_id) REFERENCES lot(id)
);
