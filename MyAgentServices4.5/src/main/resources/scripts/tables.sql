-- Schema setup for MyAgentServices 4.5
-- Target database: resume-db (MySQL)

CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_users_email (email)
);

CREATE TABLE IF NOT EXISTS users_objectives (
    id INT NOT NULL AUTO_INCREMENT,
    userid INT NOT NULL,
    objective VARCHAR(255) NOT NULL,
    active TINYINT(1) NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    KEY idx_users_objectives_userid (userid),
    CONSTRAINT fk_users_objectives_user FOREIGN KEY (userid) REFERENCES users (id) ON DELETE CASCADE
);

INSERT INTO users (name, email)
VALUES
    ('John Smith', 'john.smith@example.com'),
    ('Jane Doe', 'jane.doe@example.com')
ON DUPLICATE KEY UPDATE
    name = VALUES(name),
    email = VALUES(email);

INSERT INTO users_objectives (userid, objective, active)
VALUES
    (1, 'Deliver exceptional customer support', 1),
    (1, 'Improve first-call resolution KPIs', 1),
    (2, 'Lead onboarding for new agents', 1),
    (2, 'Document workflow improvements', 0)
ON DUPLICATE KEY UPDATE
    objective = VALUES(objective),
    active = VALUES(active);

