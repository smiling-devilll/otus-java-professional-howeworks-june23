create table bot_user
(
    id                 bigint primary key,
    name               varchar(255),
    system             varchar(80),
    external_system_id varchar(255) unique
);

create sequence bot_user_usr_id_seq as bigint
    start with 1
    increment by 1
    owned by bot_user.id;

create table person
(
    id         bigint primary key,
    first_name varchar(255),
    last_name  varchar(255),
    birthday   date   not null,
    user_id    bigint not null references bot_user (id)
);

create sequence person_prs_id_seq as bigint
    start with 1
    increment by 1
    owned by person.id;
