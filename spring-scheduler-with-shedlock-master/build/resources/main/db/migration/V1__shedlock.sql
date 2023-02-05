-- For Postgresql
-- CREATE TABLE IF NOT EXISTS shedlock(name VARCHAR(64) NOT NULL, locked_at TIMESTAMP NOT NULL, lock_until TIMESTAMP NOT NULL, locked_by VARCHAR(255) NOT NULL, PRIMARY KEY (name));

-- For Mysql
CREATE TABLE IF NOT EXISTS shedlock(name VARCHAR(64) NOT NULL, locked_at DATETIME NOT NULL, lock_until DATETIME NOT NULL, locked_by VARCHAR(255) NOT NULL, PRIMARY KEY (name));