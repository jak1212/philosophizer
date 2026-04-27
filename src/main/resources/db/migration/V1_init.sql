CREATE TABLE audience (
    id          BIGSERIAL PRIMARY KEY,
    first_name  VARCHAR(50)  NOT NULL,
    last_name   VARCHAR(50)  NOT NULL,
    email       VARCHAR(50)  NOT NULL UNIQUE,
    active      BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE TABLE philosophy (
    id       BIGSERIAL PRIMARY KEY,
    quote    VARCHAR(2000) NOT NULL UNIQUE,
    said_by  VARCHAR(100)  NOT NULL
);