INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Test', 'test@gmail.com', '{noop}test'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 3);

INSERT INTO RESTAURANT(name)
VALUES ('Kriek'),
       ('AltVelvet'),
       ('Paulaner'),
       ('Salhino');

INSERT INTO DISH(name, price, vote_date, restaurant_id)
VALUES ( 'kriek dish 1', 10000, '2023-05-01', 1),
       ( 'kriek dish 2', 20000, '2023-05-01', 1),
       ( 'altVelvet dish 1', 11000, '2023-05-01', 2),
       ( 'altVelvet dish 2', 21000, '2023-05-01', 2),
       ( 'paulaner dish 1', 12000, '2023-05-01', 3),
       ( 'paulaner dish 2', 22000, '2023-05-01', 3),
       ( 'salhino dish 1', 13000, '2023-05-01', 4),
       ( 'salhino dish 2', 23000, '2023-05-01', 4);

INSERT INTO VOTE(USER_ID, RESTAURANT_ID, VOTE_DATE)
VALUES (1, 1, '2023-05-01'),
       (1, 2, '2023-05-02'),
       (1, 4, CURRENT_DATE);
