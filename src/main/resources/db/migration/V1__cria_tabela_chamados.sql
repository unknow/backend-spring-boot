create table chamados (
    id bigserial primary key,
    titulo varchar(255) not null,
    descricao varchar(255),
    status varchar(255) not null,
    criado_em timestamp
);
