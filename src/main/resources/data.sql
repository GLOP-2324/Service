INSERT INTO role (role_name) SELECT 'Admin' WHERE NOT EXISTS (SELECT 1 FROM role WHERE role_name = 'Admin');
INSERT INTO role (role_name) SELECT 'Commerçant' WHERE NOT EXISTS (SELECT 1 FROM role WHERE role_name = 'Commerçant');
INSERT INTO role (role_name) SELECT 'Client' WHERE NOT EXISTS (SELECT 1 FROM role WHERE role_name = 'Client');
