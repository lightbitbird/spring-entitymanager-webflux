create table PERSON (
    ID serial PRIMARY KEY,
    FIRST_NAME varchar(100) not null,
    LAST_NAME varchar(100) not null,
    CREATED_DATETIME TIMESTAMP,
    UPDATED_DATETIME TIMESTAMP
);
