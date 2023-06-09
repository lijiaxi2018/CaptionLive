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

INSERT INTO projects (is_public, name, type)
VALUES (false, '虹5th Day4', 0)
ON CONFLICT DO NOTHING;

INSERT INTO ownerships (organization_id, project_id)
VALUES (1, 1)
ON CONFLICT DO NOTHING;

INSERT INTO accesses (commitment, permission, user_id, project_id)
VALUES (0, 0, 1, 1)
ON CONFLICT DO NOTHING;

INSERT INTO segments (is_global, project_id)
VALUES (true, 1)
ON CONFLICT DO NOTHING;

INSERT INTO tasks (status, type, segment_id)
VALUES (0, 8, 1),
       (0, 9, 1),
       (0, 10, 1)
ON CONFLICT DO NOTHING;

INSERT INTO segments (is_global, scope, summary, project_id)
VALUES (false, '0:1800', '全篇', 1)
ON CONFLICT DO NOTHING;

INSERT INTO tasks (status, type, segment_id)
VALUES (0, 0, 2)
ON CONFLICT DO NOTHING;

INSERT INTO tasks (status, type, segment_id, worker_user_id)
VALUES (1, 3, 2, 1),
       (1, 7, 2, 2)
ON CONFLICT DO NOTHING;

INSERT INTO segments (is_global, scope, summary, project_id)
VALUES (false, '1800:3600', '返场', 1)
ON CONFLICT DO NOTHING;

INSERT INTO tasks (status, type, segment_id)
VALUES (0, 0, 3),
       (0, 3, 3),
       (0, 7, 3)
ON CONFLICT DO NOTHING;