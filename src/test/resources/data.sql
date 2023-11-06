INSERT INTO role (role_name) VALUES ('Admin'),('Commerçant'),('Client')
ON CONFLICT (role_name) DO NOTHING;
