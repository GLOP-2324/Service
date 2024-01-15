
DELETE FROM account;
DELETE FROM role;


INSERT INTO role (role_id,role_name) VALUES (1,'Admin'),(2,'Commerçant'),(3,'Client');


INSERT INTO account (firstname, lastname, email, password, image, role_id)
VALUES
    ('John', 'Doe', 'joh@example.com', '123',null, 1),
    ('Jane', 'Smith', 'jane@example.com', '456',null, 2),
    ('Alice', 'Johnson', 'alice@example.com', '789',null, 3);

