# Book Shelf

## Setup

### Database initialization

```sql
DROP TABLE IF EXISTS book CASCADE;
CREATE TABLE book (
	id		SERIAL PRIMARY KEY,
  	isbn        	TEXT NOT NULL,
  	title       	TEXT,
  	author      	TEXT,
	release_date	DATE
);

INSERT INTO public.books (id, isbn, title, author, release_date)
VALUES (1, 9783423139557, 'Der Graf von Monte Christo', 'Alexandre Dumas', '01-01-2011');

DROP SEQUENCE IF EXISTS hibernate_sequence;
CREATE SEQUENCE hibernate_sequence START 1;
SELECT setval('hibernate_sequence', (SELECT max(id) FROM public.books));
```

## Running

1. Maven install: `mvn install`
2. Docker build: `docker build ./ -t spring-book-shelf`
3. Start container: `docker-compose up`
