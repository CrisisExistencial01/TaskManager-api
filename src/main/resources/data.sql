INSERT INTO users (username, email, password) VALUES
-- password: password
('tester', 'tester@example.com', '$2a$12$MUwN6/NOkYuj2u/2aIyl5eodyTjbegZHBDiJxrpPo6FtrMsOg6Nb2'),
-- password: adminpassword
('admin', 'admin@example.com', '$2a$12$cvWYT/460HI5eybba95NZ.wKjoYuCPCnX00Nt96iGBOtOgKFJDrE.');
INSERT INTO tasks (title, description, status, user_id) VALUES ('Tarea 1', 'Descripcion', 'Pendiente', 1);
