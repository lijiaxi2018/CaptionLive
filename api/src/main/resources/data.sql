INSERT INTO users (permission, username, password, qq, email, description, nickname)
VALUES (1, 'intruce', 'be95c8542df7f141f66a1e733f4fe4f993318139e63cb3c94ded2566768a21a9', '', '', '', '释经管理员')
ON CONFLICT DO NOTHING;