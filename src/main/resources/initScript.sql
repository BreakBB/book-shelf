CREATE TABLE book
(
    id           SERIAL PRIMARY KEY,
    isbn         TEXT NOT NULL,
    title        TEXT,
    author       TEXT,
    release_date DATE,
    has_cover    BOOL DEFAULT FALSE
);

CREATE TABLE cover
(
    id    SERIAL PRIMARY KEY,
    isbn  TEXT  NOT NULL,
    image BYTEA NOT NULL
);

INSERT INTO book (isbn, title, author, release_date)
VALUES ('3551551677', 'Harry Potter und der Stein der Weisen', 'Joanne K. Rowling', '1997-06-26'),
       ('3551551685', 'Harry Potter und die Kammer des Schreckens', 'Joanne K. Rowling', '1998-07-02'),
       ('3551551693', 'Harry Potter und der Gefangene von Askaban', 'Joanne K. Rowling', '1999-07-08'),
       ('3551551936', 'Harry Potter und der Feuerkelch', 'Joanne K. Rowling', '2000-07-08'),
       ('3551555559', 'Harry Potter und der Orden des Phönix', 'Joanne K. Rowling', '2003-06-21'),
       ('3551566666', 'Harry Potter und der Halbblutprinz', 'Joanne K. Rowling', '2005-07-16'),
       ('9783551577771', 'Harry Potter und die Heiligtümer des Todes', 'Joanne K. Rowling', '2007-07-21');