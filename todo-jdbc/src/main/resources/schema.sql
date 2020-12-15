
DROP TABLE IF EXISTS todo;

CREATE TABLE todo (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    created TIMESTAMP,
    modified TIMESTAMP,
    completed BOOLEAN
);