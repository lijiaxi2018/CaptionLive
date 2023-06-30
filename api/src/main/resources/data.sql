INSERT INTO users (permission, username, password, qq, email, description)
VALUES (1, '天王寺璃奈', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'qq1', 'email1', 'description1'),
       (0, '上原步梦', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'qq2', 'email2', 'description2'),
       (0, '高咲侑', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'qq3', 'email3', 'description3')
ON CONFLICT DO NOTHING;

INSERT INTO organizations (name, description)
VALUES ('虹之咲字幕组', 'description'),
       ('浦之星字幕组', 'description')
ON CONFLICT DO NOTHING;

INSERT INTO memberships (permission, position, organization_id, user_id)
VALUES (1, 0, 1, 1),
       (1, 0, 2, 1),
       (0, 0, 1, 2),
       (0, 0, 2, 3)
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

INSERT INTO glossary (category, explanation, remark, romanization, source, term, organization_id)
VALUES ('人名', '阿兔嘭', '', 'agupon', '大和抚子之道', 'あぐぽん', 1),
       ('人名', '大西亚玖璃', '', 'ounishi aguri', '大和抚子之道', '⼤⻄亜玖璃', 1)
ON CONFLICT DO NOTHING;