INSERT INTO users (user_id, permission, username, password, qq, email) VALUES
(1, 0, 'Uehara', 'password', 'qq1', 'email1'),
(2, 0, 'Takasaki', 'password', 'qq2', 'email2')
ON CONFLICT DO NOTHING;

INSERT INTO organizations (organization_id, name, description) VALUES
(1, 'Nijigasaki', 'description')
ON CONFLICT DO NOTHING;