
DELETE FROM account;
DELETE FROM role;


INSERT INTO role (role_id,role_name) VALUES (1,'Admin'),(2,'Commerçant'),(3,'Client');


INSERT INTO account (account_id,firstname, lastname, email, password, role_id)
VALUES
    (1,'John', 'Doe', 'joh@example.com', '123', 1),
    (2,'Jane', 'Smith', 'jane@example.com', '456', 2),
    (3,'Alice', 'Johnson', 'alice@example.com', '789', 3);
