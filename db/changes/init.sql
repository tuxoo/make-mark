CREATE SCHEMA IF NOT EXISTS mmark;

CREATE TABLE "user"
(
    id            uuid         default gen_random_uuid() primary key,
    name          varchar(255)        not null,
    login_email   varchar(255) unique not null,
    password_hash varchar(255)        not null,
    registered_at timestamp           not null,
    visited_at    timestamp           not null,
    role          varchar(255) default 'USER',
    is_enabled    boolean      default false
);

CREATE TABLE session
(
    id            bigint generated by default as identity
        primary key,
    refresh_token uuid default gen_random_uuid(),
    expires_at    timestamp not null,
    user_id       uuid      not null
        constraint fk_session_user_id references "user"
);