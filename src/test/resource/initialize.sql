DROP TABLE IF EXISTS accounts;

CREATE TABLE accounts
(
    id bigint unsigned primary key NOT NULL AUTO_INCREMENT,
    balance decimal(10, 2),
    currency varchar(10),
    created_at timestamp default current_timestamp
);