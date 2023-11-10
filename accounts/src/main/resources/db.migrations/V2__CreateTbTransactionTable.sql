CREATE TABLE IF NOT EXISTS tb_transaction (
    id serial PRIMARY KEY,
    account_id int NOT NULL,
    operation_type_d smallint NOT NULL,
    amount decimal NOT NULL,
    created_date timestamptz NOT NULL,
    last_modified_date timestamptz NOT NULL,
    CONSTRAINT account_id FOREIGN KEY (account_id) REFERENCES tb_account (id)
);
