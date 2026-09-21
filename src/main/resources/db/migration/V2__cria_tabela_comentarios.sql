create table comentarios (
    id bigserial primary key,
    texto varchar(255) not null,
    criado_em timestamp,
    chamado_id bigint not null references chamados (id)
);
