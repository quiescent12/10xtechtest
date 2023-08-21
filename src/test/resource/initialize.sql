DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS transactions;

CREATE TABLE accounts
(
    id bigint unsigned primary key not null auto_increment,
    balance decimal(10, 2) not null,
    currency varchar(10) not null,
    created_at timestamp default current_timestamp not null
);

CREATE TABLE transactions
(
    id varchar(36) primary key not null,
    source_account_id bigint unsigned not null,
    target_account_id bigint unsigned not null,
    amount decimal(10, 2) not null,
    currency varchar(10) not null
);