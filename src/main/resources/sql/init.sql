CREATE TABLE IF NOT EXISTS roles(
    id INT PRIMARY KEY NOT NULL,
    user_role VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS status(
    id INT PRIMARY KEY NOT NULL,
    lot_status VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS users(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(45) NOT NULL UNIQUE,
    birth_date DATE NOT NULL,
    email VARCHAR(45) NOT NULL UNIQUE,
    password VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS lot(
    id BIGSERIAL PRIMARY KEY,
    lot_name VARCHAR(45) NOT NULL,
    lot_owner VARCHAR(45) NOT NULL,
    start_bid INT NOT NULL,
    status_id INT NOT NULL,
    lot_buyer VARCHAR(45),
    FOREIGN KEY (lot_owner) REFERENCES users(username),
    FOREIGN KEY (status_id) REFERENCES status(id)
);

CREATE TABLE IF NOT EXISTS bid(
    id BIGSERIAL PRIMARY KEY,
    lot_name VARCHAR(128) NOT NULL,
    lot_id INT NOT NULL,
    username VARCHAR(45) NOT NULL,
    user_bid INT,
    FOREIGN KEY (lot_id) REFERENCES lot(id)
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE ON UPDATE CASCADE
);
