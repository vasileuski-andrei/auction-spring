INSERT INTO users(id, username, birth_date, email, password, role, user_status)
VALUES(1, 'admin', '2022-03-11', 'admin@admin.com', '$2a$12$o3lI0qeOG9h.B7RFkoUDLOY9VJa/Ih5Hl1Aa34.ATNb1tTrogOCnu', 'USER', 'ACTIVE'),
      (2, 'TestUser', '2022-03-13', 'test@test.com', '$2a$12$o3lI0qeOG9h.B7RFkoUDLOY9VJa/Ih5Hl1Aa34.ATNb1tTrogOCnu', 'USER', 'ACTIVE'),
      (3, 'TestUser2', '2022-03-13', 'test2@test.com', '$2a$12$o3lI0qeOG9h.B7RFkoUDLOY9VJa/Ih5Hl1Aa34.ATNb1tTrogOCnu', 'USER', 'ACTIVE');
SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));

INSERT INTO lot(id, lot_name, lot_owner, start_bid, status_id)
VALUES(1, 'TestLot', 'admin', 1, 1);
SELECT SETVAL('lot_id_seq', (SELECT MAX(id) FROM lot));

INSERT INTO bid(id, lot_name, lot_id, username, user_bid)
VALUES(1, 'TestLot', 1, 'TestUser', 10);
SELECT SETVAL('bid_id_seq', (SELECT MAX(id) FROM bid));