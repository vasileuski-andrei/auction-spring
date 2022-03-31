INSERT INTO lot_status(id, lot_status) VALUES (1, 'SELL');
INSERT INTO lot_status(id, lot_status) VALUES (2, 'SOLD');
INSERT INTO lot_status(id, lot_status) VALUES (3, 'NOT_SOLD');

INSERT INTO users(id, username, birth_date, email, password, role, user_status)
VALUES(1, 'admin', '2022-03-11', 'admin@admin.com', 'admin', 'USER', 'ACTIVE');

INSERT INTO users(id, username, birth_date, email, password, role, user_status)
VALUES(2, 'TestUser', '2022-03-13', 'test@test.com', '1', 'USER', 'ACTIVE');

INSERT INTO lot(id, lot_name, lot_owner, start_bid, status_id)
VALUES(1, 'TestLot', 'admin', 1, 1);