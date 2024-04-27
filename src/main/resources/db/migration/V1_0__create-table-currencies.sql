CREATE TABLE currencies
(
    id          BIGSERIAL PRIMARY KEY,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    code        VARCHAR(3)                          NOT NULL UNIQUE,
    description VARCHAR(64)                         NOT NULL,
    active      BOOLEAN   DEFAULT TRUE
);

CREATE INDEX currencies_active_uidx
    on currencies (active);