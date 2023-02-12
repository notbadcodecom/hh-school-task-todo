CREATE TABLE IF NOT EXISTS tasks (
    task_id      BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title        VARCHAR(256) NOT NULL,
    completed    BOOLEAN NOT NULL,
    created      TIMESTAMP NOT NULL
);
