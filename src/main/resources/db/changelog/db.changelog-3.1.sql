--liquibase formatted sql

--changeset andreiv:1
ALTER TABLE lot ADD COLUMN lot_owner VARCHAR(120) NOT NULL;
