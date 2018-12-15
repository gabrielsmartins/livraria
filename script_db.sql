
CREATE DATABASE IF NOT EXISTS casadocodigo;
CREATE DATABASE IF NOT EXISTS casadocodigo_test;

USE casadocodigo;

insert into Role values ('ROLE_ADMIN');

-- senha 123456
insert into Usuario (email, nome, senha) values ('admin@casadocodigo.com.br', 'Administrador', '$2a$04$qP517gz1KNVEJUTCkUQCY.JzEoXzHFjLAhPQjrg5iP6Z/UmWjvUhq');

insert into Usuario_Role(Usuario_email, roles_nome) values ('admin@casadocodigo.com.br', 'ROLE_ADMIN');