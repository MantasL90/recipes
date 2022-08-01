INSERT INTO ROLE
VALUES ('USER'),
       ('ADMIN');

INSERT INTO USERS (USERNAME, EMAIL_ADDRESS, NAME, PASSWORD, SURNAME, ROLE_NAME)
VALUES ('mantasl', 'mantas@mail.com', 'Mantas', '{bcrypt}$2a$12$W3EitHx4XBWCJMM2x4qT2eJllLlvln3YAz.6OpSuc.1zI7GHsk2hC',
        'L','ADMIN'),
       ('user', 'user@mail.com', 'Useris', '{bcrypt}$2a$12$Ho4umyT5991bokLKmPoVhuEvfGudq7yE5ovGZKXeoMLJoCEtKNVkW',
        'Useriukas', 'USER');

INSERT INTO RECIPE (ID, DESCRIPTION, INGREDIENTS, IS_PUBLISHED, PREPARATION, PREPARATION_TIME, SERVINGS, TITLE,
                    USERNAME)
VALUES ('00000000-0000-0000-0000-000000000001', 'testas', 'trumpas aprasymas', false, 'dsaddas', 'dasda', 10, 'dsa',
        'user'),
       ('00000000-0000-0000-0000-000000000002', 'testas', 'trumpas aprasymas', false, 'dsaddas', 'dasda', 10, 'hggfh',
        'mantasl'),
       ('00000000-0000-0000-0000-000000000003', 'testas', 'trumpas aprasymas', true, 'dsaddas', 'dasda', 10, 'Tdasda',
        'mantasl'),
       ('00000000-0000-0000-0000-000000000004', 'testas', 'trumpas aprasymas', false, 'dsaddas', 'dasda', 10,
        'Tdakjhsda',
        'user'),
       ('00000000-0000-0000-0000-000000000005', 'testas', 'trumpas aprasymas', true, 'dsaddas', 'dasda', 10,
        'Tdatertsda',
        'user'),
       ('00000000-0000-0000-0000-000000000006', 'testas', 'trumpas aprasymas', false, 'dsaddas', 'dasda', 10,
        'Tdwqeqasda',
        'mantasl'),
       ('00000000-0000-0000-0000-000000000007', 'testas', 'trumpas aprasymas', true, 'dsaddas', 'dasda', 10, 'sdsf',
        'mantasl'),
       ('00000000-0000-0000-0000-000000000008', 'testas', 'trumpas aprasymas', false, 'dsaddas', 'dasda', 10, 'bcvs',
        'user');
