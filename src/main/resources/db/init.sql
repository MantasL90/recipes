INSERT INTO ROLES VALUES ('ADMIN'), ('USER');

INSERT INTO USERS (username, password, name, surname, email_address, role_name)
VALUES ('mantasl', '{bcrypt}$2a$12$Ho4umyT5991bokLKmPoVhuEvfGudq7yE5ovGZKXeoMLJoCEtKNVkW', 'Mantas', 'L', 'dariusg@mail.com', 'ADMIN');
INSERT INTO USERS (username, password, name, surname, email_address, role_name)
VALUES ('user', '{bcrypt}$2a$12$Ho4umyT5991bokLKmPoVhuEvfGudq7yE5ovGZKXeoMLJoCEtKNVkW', 'Useris', 'Useriukas', 'dariusg@mail.com', 'USER');

INSERT INTO RECIPE (id, title, is_published, description, ingredients, preparation, preparation_time, servings, user_id )
VALUES ('00000000-0000-0000-0000-000000000001','testas', false, 'trumpas aprasymas','dsaddas','dasda','fsdf', 10, 'user');

INSERT INTO RECIPE (id, title, is_published, description, ingredients, preparation, preparation_time, servings, user_id )
VALUES ('00000000-0000-0000-0000-000000000002','testadass', false, 'trumsdaas','das','dasda','60 min.', 10, 'user');

INSERT INTO RECIPE (id, title, is_published, description, ingredients, preparation, preparation_time, servings, user_id)
VALUES ('00000000-0000-0000-0000-000000000003','Tortas', true, 'trumsdaas','das','dasda','60 min.', 11,'mantasl');

INSERT INTO RECIPE (id, title, is_published, description, ingredients, preparation, preparation_time, servings, user_id )
VALUES ('00000000-0000-0000-0000-000000000004','Sokoladas', true, 'trumsdaas','das','dasda','60 min.', 1,'mantasl');