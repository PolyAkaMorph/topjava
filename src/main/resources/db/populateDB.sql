DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (id, name, email, password) VALUES
  (100000,'User', 'user@yandex.ru', 'password'),
  (100001, 'Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals(id,user_id, datetime,description,calories) VALUES
  (100002,100000,'2020-03-09 10:00:00','Завтрак','800'),
  (100003,100000,'2020-03-09 15:00:00','Обед','1500'),
  (100004,100000,'2020-03-09 20:00:00','Ужин','500'),
  (100005,100001,'2020-03-09 14:00:00','Шаверма смерти','2500');