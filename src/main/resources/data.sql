INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT(name)
VALUES ('Kriek'),
       ('AltVelvet'),
       ('Paulaner'),
       ('Salhino');

INSERT INTO DISH(name, price, vote_date, restaurant_id)
VALUES ('TEST DISH', 1111, '2023-04-27', 1);

INSERT INTO VOTE(USER_ID, RESTAURANT_ID, VOTE_DATE)
VALUES (1, 1, '2023-04-26'),
       (1, 1, '2023-04-27');