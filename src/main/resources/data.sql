insert into
	COURSE (title, desc, available)
values
  	('Mathematics', 'Learn about math and more from the best university',1),
  	('Literature', 'Written works, especially those considered of superior or lasting artistic merit',1),
  	('Entomology', 'The branch of zoology concerned with the study of insects',1),
  	('Psychology', 'the scientific study of the human mind and its functions, especially those affecting behavior in a given context',1),
  	('Artificial intelligence', 'the theory and development of computer systems able to perform tasks that normally require human intelligence, such as visual perception, speech recognition, decision-making, and translation between languages.',1);

insert into
	ROLE (name)
values
  	('ADMIN'),
  	('TEACHER'),
  	('STUDENT');

insert into
	PERMISSION (name)
values
  ('course:read'),
  ('course:attend'),
  ('course:leave'),
  ('course:create'),
  ('course:delete'),
  ('course:update'),
  ('teach:start'),
  ('teach:stop');

insert into
	USER (name, email, password, role_id)
values
  	('adam','adam@courses.com', '$2y$12$11xzosW.JnztkkhrMwJPi.DwotrCWEqlE2r/qN0k2MoYWjKrC7eqm',3),
    ('john','john@courses.com', '$2y$12$11xzosW.JnztkkhrMwJPi.DwotrCWEqlE2r/qN0k2MoYWjKrC7eqm',2),
    ('kelly','john@courses.com', '$2y$12$11xzosW.JnztkkhrMwJPi.DwotrCWEqlE2r/qN0k2MoYWjKrC7eqm',1);

insert into
	ROLE_PERMISSION (ROLE_ID, PERMISSION_ID)
values
  (1,1),
  (1,4),
  (1,5),
  (1,6),
  (2,1),
  (2,7),
  (2,8),
  (3,1),
  (3,2),
  (3,3);