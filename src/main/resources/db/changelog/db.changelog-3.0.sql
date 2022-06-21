--liquibase formatted sql

--changeset andreiv:1
ALTER TABLE lot DROP CONSTRAINT lot_lot_owner_fkey;
ALTER TABLE lot ALTER COLUMN lot_owner TYPE BIGINT USING lot_owner::bigint;
ALTER TABLE lot RENAME COLUMN lot_owner TO user_id;
ALTER TABLE lot ADD FOREIGN KEY (user_id) REFERENCES users(id);
