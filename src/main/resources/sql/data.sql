INSERT INTO lot_status(id, lot_status) VALUES (1, 'SELL');
INSERT INTO lot_status(id, lot_status) VALUES (2, 'SOLD');
INSERT INTO lot_status(id, lot_status) VALUES (3, 'NOT_SOLD');

INSERT INTO users(username, birth_date, email, password, role, user_status)
VALUES('admin', '2022-03-11', 'admin@admin.com', '$2a$12$o3lI0qeOG9h.B7RFkoUDLOY9VJa/Ih5Hl1Aa34.ATNb1tTrogOCnu', 'USER', 'ACTIVE');

INSERT INTO users(username, birth_date, email, password, role, user_status)
VALUES('TestUser', '2022-03-13', 'test@test.com', '$2a$12$o3lI0qeOG9h.B7RFkoUDLOY9VJa/Ih5Hl1Aa34.ATNb1tTrogOCnu', 'USER', 'ACTIVE');

INSERT INTO lot(lot_name, lot_owner, start_bid, status_id)
VALUES('TestLot', 'admin', 1, 1);