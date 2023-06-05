INSERT INTO users (permission, username, password, qq, email, description)
VALUES (0, '上原步梦', 'password', 'qq1', 'email1', 'description1'),
       (0, '高咲侑', 'password', 'qq2', 'email2', 'description2')
ON CONFLICT DO NOTHING;

INSERT INTO organizations (name, description)
VALUES ('虹之咲字幕组', 'description'),
       ('浦之星字幕组', 'description')
ON CONFLICT DO NOTHING;

INSERT INTO memberships (permission, position, organization_id, user_id)
VALUES (1, 0, 1, 1),
       (0, 0, 1, 2),
       (0, 0, 2, 1)
ON CONFLICT DO NOTHING;