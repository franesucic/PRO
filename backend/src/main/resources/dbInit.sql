CREATE TABLE Theme (
    id SERIAL PRIMARY KEY,
    option VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE Users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    email VARCHAR(100),
    theme_id INT REFERENCES Theme(id)
);

CREATE TABLE Category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE Advert (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    picture BYTEA,
    price NUMERIC(10, 2),
    category_id INT REFERENCES Category(id),
    user_id INT REFERENCES Users(id)
);

CREATE TABLE Favorite (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES Users(id),
    advert_id INT REFERENCES Advert(id)
);

CREATE TABLE Offer (
    id SERIAL PRIMARY KEY,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    price NUMERIC(10, 2),
    advert_id INT REFERENCES Advert(id),
    user_id INT REFERENCES Users(id)
);

CREATE TABLE Review (
    id SERIAL PRIMARY KEY,
    comment TEXT,
    user_id INT REFERENCES Users(id)
);

INSERT INTO Users (username, password, firstName, lastName, email) VALUES
    ('admin', 'admin', 'Pero', 'Perić', 'pero.peric@fer.hr');

INSERT INTO Category (name) VALUES
    ('Majice'),
    ('Hlače'),
    ('Haljine'),
    ('Cipele'),
    ('Košulje'),
    ('Suknje'),
    ('Jakne'),
    ('Džemperi'),
    ('Kaputi'),
    ('Trenirke'),
    ('Kupaći kostimi'),
    ('Čarape'),
    ('Donje rublje'),
    ('Odijela'),
    ('Aksesoari');

INSERT INTO Theme (option) VALUES
    ('svijetla'),
    ('tamna');