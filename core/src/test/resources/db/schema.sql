DROP TABLE IF EXISTS VACATION_INFO;

CREATE TABLE VACATION_INFO (
ID                      BIGINT              PRIMARY KEY,
USER_ID                 VARCHAR (21)        NOT NULL,
TEAM_ID                 VARCHAR (21)        NOT NULL,
DATE_FROM               DATE                NOT NULL,
DATE_TO                 DATE                NOT NULL,
SUBSTITUTION_USER_IDS   VARCHAR (512)
);
CREATE SEQUENCE IF NOT EXISTS VACATION_INFO_SEQUENCE START WITH 1 INCREMENT BY 1;