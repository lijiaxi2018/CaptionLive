INSERT INTO users (permission, username, password, qq, email, description)
VALUES (0, 'Uehara', 'password', 'qq1', 'email1', 'description1'),
       (0, 'Takasaki', 'password', 'qq2', 'email2', 'description2')
ON CONFLICT DO NOTHING;

INSERT INTO organizations (name, description)
VALUES ('Nijigasaki', 'description'),
       ('Uranohoshi', 'description')
ON CONFLICT DO NOTHING;

INSERT INTO memberships (permission, position, organization_id, user_id)
VALUES (1, 0, 1, 1),
       (0, 0, 1, 2),
       (0, 0, 2, 1)
ON CONFLICT DO NOTHING;