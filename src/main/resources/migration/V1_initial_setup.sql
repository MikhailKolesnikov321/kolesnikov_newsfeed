CREATE TABLE logs (
    id SERIAL PRIMARY KEY,
    methodName VARCHAR(255),
    url VARCHAR(255),
    ip VARCHAR(255),
    userAgent VARCHAR(255),
    responseStatus INT,
    timestamp TIMESTAMP
);

CREATE TABLE news (
    id SERIAL PRIMARY KEY,
    description TEXT,
    image VARCHAR(255),
    title VARCHAR(255),
    user_id UUID REFERENCES users_nf(id)
);

CREATE TABLE tags (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    news_id INTEGER REFERENCES news_nf(id)
);

CREATE TABLE users (
    id UUID PRIMARY KEY,
    avatar VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    name VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255) DEFAULT 'user',
);