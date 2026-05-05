CREATE SEQUENCE IF NOT EXISTS audience_seq START 1 INCREMENT 50;
CREATE SEQUENCE IF NOT EXISTS philosophy_seq START 1 INCREMENT 50;

INSERT INTO audience (first_name, last_name, email, active)
VALUES ('Jack', 'Kelly', 'jk0827@gmail.com', true),
('Ellie', 'Kelly', 'ellie.baldwin.kelly@gmail.com', true);