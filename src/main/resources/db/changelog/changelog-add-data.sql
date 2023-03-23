insert into role (id, role_name)
values ('123e4567-e89b-12d3-a456-426655440000', 'ADMIN'),
       ('223e4567-e89b-12d3-a456-426655440000', 'PROVIDER'),
       ('323e4567-e89b-12d3-a456-426655440000', 'USER'),
       ('423e4567-e89b-12d3-a456-426655440000', 'DETAINED');


insert into customer (id, username, email, password, balance)
values ('e37b5e5c-7133-45b7-8461-c4f6006d0477',
        'admin',
        'adminadmin@admin.ru',
        '$2a$08$QVptKb9N3qjnUnCPkaUTXOD5ADlArYlVO6TiMH3XNxkf87sUygQvG',
        '200');

insert into users_roles(user_id, role_id)
values ('e37b5e5c-7133-45b7-8461-c4f6006d0477',
        '123e4567-e89b-12d3-a456-426655440000');