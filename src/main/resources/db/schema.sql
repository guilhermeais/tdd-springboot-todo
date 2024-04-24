CREATE SCHEMA todoapp;

CREATE TABLE todoapp.todos (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(255) NOT NULL,
    observation TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);