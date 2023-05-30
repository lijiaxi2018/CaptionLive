INSERT INTO users (permission, username, password, qq, email)
VALUES (0, 'Uehara', 'password', 'qq1', 'email1'),
       (0, 'Takasaki', 'password', 'qq2', 'email2')
ON CONFLICT DO NOTHING;

INSERT INTO organizations (name, description)
VALUES ('Nijigasaki', 'description')
ON CONFLICT DO NOTHING;