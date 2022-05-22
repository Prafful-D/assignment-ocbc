CREATE TABLE if not exists countries (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE if not exists users (
    id uuid default random_uuid(),
    name VARCHAR(128) NOT NULL,
    user_name VARCHAR(128) ,
    password VARCHAR(128),
    amount NUMERIC (20, 5),
    PRIMARY KEY (id)
);

CREATE TABLE if not exists users_balance (
    id uuid default random_uuid(),
    name VARCHAR(128) NOT NULL,
    user_name VARCHAR(128) ,
    password VARCHAR(128),
    amount NUMERIC (20, 5),
    PRIMARY KEY (id)
);

CREATE TABLE if not exists transactions (
    id uuid default random_uuid(),
    user_id uuid NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    transaction_date TIMESTAMP ,
    amount NUMERIC (20, 5),
    PRIMARY KEY (id)
);

CREATE TABLE if not exists pending_transactions (
    id uuid default random_uuid(),
    to_user uuid NOT NULL,
    from_user uuid NOT NULL,
    amount NUMERIC (20, 5),
    transaction_date TIMESTAMP ,
    updated_date TIMESTAMP ,
    PRIMARY KEY (id)
);