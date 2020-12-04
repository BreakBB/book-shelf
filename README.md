# Book Shelf

A simple Spring playground project.

## Setup

### Database initialization

#### Book

```sql
DROP TABLE IF EXISTS book CASCADE;
CREATE TABLE book (
    id              SERIAL PRIMARY KEY,
    isbn            TEXT NOT NULL,
    title           TEXT,
    author          TEXT,
    release_date    DATE
);
```

#### Cover

```sql
DROP TABLE IF EXISTS cover;
CREATE TABLE cover
(
    id          SERIAL PRIMARY KEY,
    isbn        TEXT NOT NULL,
    image       BYTEA NOT NULL
);
```

## Running

1. Enter the correct database connection information in the application.properties
   - Replace: `<PORT>`, `<DATABASE_NAME>`, `<USERNAME>` and `<PASSWORD>`
2. Maven install: `mvn install`
3. Start container: `docker-compose up`
