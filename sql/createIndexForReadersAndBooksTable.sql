DROP INDEX BOOKTITLE ON BOOKS;
CREATE INDEX BOOKTITLE ON BOOKS (TITLE);

DROP INDEX READER ON READERS;
EXPLAIN SELECT * FROM READERS WHERE FIRSTNAME="John";
CREATE INDEX READER ON READERS(FIRSTNAME,LASTNAME);
EXPLAIN SELECT * FROM READERS WHERE FIRSTNAME="John";