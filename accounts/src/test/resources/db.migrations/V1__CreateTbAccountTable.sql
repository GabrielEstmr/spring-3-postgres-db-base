CREATE TABLE IF NOT EXISTS tb_account (
    id serial PRIMARY KEY,
    document_number varchar(20) UNIQUE NOT NULL
);
