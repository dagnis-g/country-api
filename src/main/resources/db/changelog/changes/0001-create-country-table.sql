--liquibase formatted sql

--changeset dagnis:1

create table if not exists country
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    ip          VARCHAR(50) NOT NULL,
    country     VARCHAR(50) NOT NULL,
    city        VARCHAR(50) NOT NULL,
    location    VARCHAR(50) NOT NULL
);