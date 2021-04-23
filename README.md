# Book Shelf

![GitHub Workflow Status](https://img.shields.io/github/workflow/status/BreakBB/book-shelf/Java%20CI%20with%20Maven)

A simple Spring playground project. You can find the frontend counter part here: [Book Shelf UI](https://github.com/BreakBB/book-shelf-ui).

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
