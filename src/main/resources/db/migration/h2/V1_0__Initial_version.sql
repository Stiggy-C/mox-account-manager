CREATE TABLE IF NOT EXISTS account (
    id UUID PRIMARY KEY,
    balance DOUBLE NOT NULL DEFAULT 0,
    number BIGINT NOT NULL,
    created_by VARCHAR(320),
    created_date_time TIMESTAMP WITH TIME ZONE,
    updated_by VARCHAR(320),
    updated_date_time TIMESTAMP WITH TIME ZONE
);

INSERT INTO account (id, balance, number, created_by, created_date_time) VALUES (
    UUID(), 1000000, 12345678, 'SYSTEM', CURRENT_TIMESTAMP
);

INSERT INTO account (id, balance, number, created_by, created_date_time) VALUES (
    UUID(), 1000000, 88888888, 'SYSTEM', CURRENT_TIMESTAMP
);