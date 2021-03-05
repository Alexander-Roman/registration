CREATE TABLE accounts
(
    id       BIGSERIAL    NOT NULL UNIQUE,
    email    VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL UNIQUE,
    name     VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(255) NOT NULL,
    blocked  BOOLEAN      NOT NULL,
    enabled  BOOLEAN      NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE email_verifications
(
    id         BIGSERIAL    NOT NULL UNIQUE,
    token      VARCHAR(255) NOT NULL UNIQUE,
    issued     TIMESTAMP    NOT NULL,
    expires    TIMESTAMP    NOT NULL,
    confirmed  TIMESTAMP,
    account_id BIGINT       NOT NULL,
    FOREIGN KEY (account_id) REFERENCES accounts (id),
    PRIMARY KEY (id)
);