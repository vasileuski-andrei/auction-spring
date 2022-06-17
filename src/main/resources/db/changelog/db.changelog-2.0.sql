--liquibase formatted sql

--changeset andreiv:1
ALTER TABLE users ADD COLUMN telegram_account VARCHAR(120) UNIQUE;