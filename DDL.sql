DROP TABLE if exists SETTING;
CREATE TABLE SETTING (
	SET_ID VARCHAR(255) NOT NULL PRIMARY KEY
	, SET_NAME VARCHAR(255) NOT NULL
	, SET_TYPE VARCHAR(255)
	, SET_VALUE VARCHAR(255)
	, CREATE_BY VARCHAR(255)
	, CREATE_DATE DATETIME
	, LAST_MODIFIED_BY VARCHAR(255)
	, LAST_MODIFIED_DATE DATETIME
) DEFAULT CHARACTER SET UTF8;
INSERT INTO SETTING(SET_ID, SET_NAME, SET_TYPE, SET_VALUE, CREATE_BY, CREATE_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE) 
VALUES('loanDate',' 대출기간', 'INTEGER', '14', 'system', NOW(), 'system', NOW());
INSERT INTO SETTING(SET_ID, SET_NAME, SET_TYPE, SET_VALUE, CREATE_BY, CREATE_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE) 
VALUES('loanCnt',' 대출권수', 'INTEGER', '5', 'system', NOW(), 'system', NOW());

DROP TABLE if exists USERS;
CREATE TABLE USERS (
	USER_ID VARCHAR(255) NOT NULL PRIMARY KEY 
	, USER_PASSWORD VARCHAR(255) NOT NULL
	, USER_NAME VARCHAR(255)
	, USER_SEX VARCHAR(1) CHECK(USER_SEX IN ('M', 'W'))
	, USER_BIRTH VARCHAR(10)
	, USER_AUTHORITY VARCHAR(10)
	, USER_LOGIN_FAIL_CNT INT 
	, USER_FIND_PASSWORD_YN VARCHAR(1) CHECK(USER_FIND_PASSWORD_YN IN ('Y', 'N'))
	, USER_OAUTH_TYPE VARCHAR(255)
	, CREATE_BY VARCHAR(255)
	, CREATE_DATE DATETIME
	, LAST_MODIFIED_BY VARCHAR(255)
	, LAST_MODIFIED_DATE DATETIME
) DEFAULT CHARACTER SET UTF8;

DROP TABLE if exists LOGIN_TOKEN;
CREATE TABLE LOGIN_TOKEN
(
	  LOGIN_TOKEN_SERIES VARCHAR(255) NOT NULL PRIMARY KEY
	, LOGIN_TOKEN_USERNAME VARCHAR(255) NOT NULL
	, LOGIN_TOKEN VARCHAR(255) NOT NULL
	, LOGIN_TOKEN_LAST_USED VARCHAR(255) NOT NULL
	, CREATE_BY VARCHAR(255)
	, CREATE_DATE DATETIME
	, LAST_MODIFIED_BY VARCHAR(255)
	, LAST_MODIFIED_DATE DATETIME
) DEFAULT CHARACTER SET UTF8;

DROP TABLE IF EXISTS BOOK_CATEGORY;
CREATE TABLE BOOK_CATEGORY
(
	  CATEGORY_ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY
	, CATEGORY_NAME VARCHAR(255) NOT NULL
	, CREATE_BY VARCHAR(255)
	, CREATE_DATE DATETIME
	, LAST_MODIFIED_BY VARCHAR(255)
	, LAST_MODIFIED_DATE DATETIME
) DEFAULT CHARACTER SET UTF8;

DROP TABLE IF EXISTS BOOK;
CREATE TABLE BOOK
(
	  BOOK_ISBN VARCHAR(255) NOT NULL PRIMARY KEY
	, BOOK_CATEGORY_ID INTEGER
	, BOOK_TITLE VARCHAR(255)
	, BOOK_AUTHOR VARCHAR(255)
	, BOOK_PUBLISHER VARCHAR(255)
	, BOOK_DISTRIBUTOR VARCHAR(255)
	, BOOK_PUBLICATION_YEAR VARCHAR(10)
	, BOOK_INDEX TEXT
	, BOOK_INT TEXT
	, BOOK_AUTHOR_INT TEXT
	, BOOK_IMAGE VARCHAR(255)
	, BOOK_MAX_LOAN_CNT INTEGER
	, BOOK_MAX_RESERVE_CNT INTEGER
	, BOOK_LIKE INTEGER
	, BOOK_LOAN_CNT INTEGER
	, BOOK_RESERVE_CNT INTEGER
	, BOOK_TOTAL_PAGE_CNT INTEGER
	, CREATE_BY VARCHAR(255)
	, CREATE_DATE DATETIME
	, LAST_MODIFIED_BY VARCHAR(255)
	, LAST_MODIFIED_DATE DATETIME
	, FOREIGN KEY(BOOK_CATEGORY_ID) REFERENCES BOOK_CATEGORY(CATEGORY_ID) ON DELETE CASCADE
) DEFAULT CHARACTER SET UTF8;
CREATE INDEX IDX_BOOK_CATEGORY ON BOOK(BOOK_CATEGORY_ID);
CREATE INDEX IDX_BOOK_TITLE ON BOOK(BOOK_TITLE);

DROP TABLE IF EXISTS BOOK_LOAN;
CREATE TABLE BOOK_LOAN
(
	  LOAN_ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY
	, LOAN_ISBN VARCHAR(255) NOT NULL
	, LOAN_DATE DATETIME NOT NULL
	, LOAN_RETURN_DATE DATETIME
	, LOAN_USER VARCHAR(255) NOT NULL
	, LOAN_RETURN_YN VARCHAR(1) CHECK(LOAN_RETURN_YN IN ('Y', 'N'))
	, CREATE_BY VARCHAR(255)
	, CREATE_DATE DATETIME
	, LAST_MODIFIED_BY VARCHAR(255)
	, LAST_MODIFIED_DATE DATETIME
	, FOREIGN KEY(LOAN_ISBN) REFERENCES BOOK(BOOK_ISBN) ON DELETE CASCADE
	, FOREIGN KEY(LOAN_USER) REFERENCES USERS(USER_ID) ON DELETE CASCADE
) DEFAULT CHARACTER SET UTF8;
CREATE INDEX IDX_BOOK_LOAN_BOOK ON BOOK_LOAN(LOAN_RETURN_YN, LOAN_ISBN);
CREATE INDEX IDX_BOOK_LOAN_USER ON BOOK_LOAN(LOAN_RETURN_YN, LOAN_USER);
CREATE INDEX IDX_BOOK_LOAN_HIS ON BOOK_LOAN(LOAN_USER, LOAN_DATE);

DROP TABLE IF EXISTS BOOK_RESERVE;
CREATE TABLE BOOK_RESERVE
(
	  RESERVE_ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY
	, RESERVE_ISBN VARCHAR(255) NOT NULL
	, RESERVE_DATE DATETIME NOT NULL
	, RESERVE_USER VARCHAR(255) NOT NULL
	, RESERVE_STATUS VARCHAR(10) NOT NULL
	, CREATE_BY VARCHAR(255) 
	, CREATE_DATE DATETIME
	, LAST_MODIFIED_BY VARCHAR(255)
	, LAST_MODIFIED_DATE DATETIME
	, FOREIGN KEY(RESERVE_ISBN) REFERENCES BOOK(BOOK_ISBN) ON DELETE CASCADE
	, FOREIGN KEY(RESERVE_USER) REFERENCES USERS(USER_ID) ON DELETE CASCADE
) DEFAULT CHARACTER SET UTF8;
CREATE INDEX IDX_BOOK_RESERVE_BOOK ON BOOK_RESERVE(RESERVE_STATUS, RESERVE_ISBN);
CREATE INDEX IDX_BOOK_RESERVE_USER ON BOOK_RESERVE(RESERVE_STATUS, RESERVE_USER);
CREATE INDEX IDX_BOOK_RESERVE_HIS ON BOOK_RESERVE(RESERVE_USER, RESERVE_DATE);

DROP TABLE IF EXISTS BOOK_HOPE;
CREATE TABLE BOOK_HOPE
(
	  HOPE_ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY
	, HOPE_USER VARCHAR(255) NOT NULL
	, HOPE_ISBN VARCHAR(255) NOT NULL
	, HOPE_TITLE VARCHAR(255) NOT NULL
	, HOPE_AUTHOR VARCHAR(255) NOT NULL
	, HOPE_PUBLISHER VARCHAR(255) NOT NULL
	, HOPE_DATE VARCHAR(255) NOT NULL
	, HOPE_STATUS VARCHAR(10) NOT NULL
	, CREATE_BY VARCHAR(255) 
	, CREATE_DATE DATETIME
	, LAST_MODIFIED_BY VARCHAR(255)
	, LAST_MODIFIED_DATE DATETIME
	, FOREIGN KEY(HOPE_USER) REFERENCES USERS(USER_ID) ON DELETE CASCADE
) DEFAULT CHARACTER SET UTF8;
CREATE INDEX IDX_BOOK_HOPE ON BOOK_HOPE(HOPE_STATUS, HOPE_DATE);
CREATE INDEX IDX_BOOK_HOPE_TITLE ON BOOK_HOPE(HOPE_STATUS, HOPE_DATE, HOPE_TITLE);
CREATE INDEX IDX_BOOK_HOPE_ISBN ON BOOK_HOPE(HOPE_STATUS, HOPE_DATE, HOPE_ISBN);
CREATE INDEX IDX_BOOK_HOPE_HIS ON BOOK_HOPE(HOPE_USER, HOPE_DATE);

DROP TABLE IF EXISTS QNA;
CREATE TABLE QNA
(
	  QNA_ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY
	, QNA_USER VARCHAR(255) NOT NULL
	, QNA_DATE VARCHAR(10) NOT NULL
	, QNA_QUESTION TEXT
	, QNA_ANSWER TEXT
	, QNA_ANSWER_YN VARCHAR(1)
	, CREATE_BY VARCHAR(255) 
	, CREATE_DATE DATETIME
	, LAST_MODIFIED_BY VARCHAR(255)
	, LAST_MODIFIED_DATE DATETIME
	, FOREIGN KEY(QNA_USER) REFERENCES USERS(USER_ID) ON DELETE CASCADE
) DEFAULT CHARACTER SET UTF8;
CREATE INDEX IDX_QNA ON QNA(QNA_DATE);
CREATE INDEX IDX_QNA_USER ON QNA(QNA_USER, QNA_DATE);

DROP TABLE IF EXISTS NOTIFY;
CREATE TABLE NOTIFY
(
	  NOTIFY_ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY
	, NOTIFY_TYPE VARCHAR(10)
	, NOTIFY_TITLE VARCHAR(255)
	, NOTIFY_TEXT TEXT
	, NOTIFY_IMG VARCHAR(255)
	, NOTIFY_START_DT VARCHAR(10)
	, NOTIFY_END_DT VARCHAR(10)
	, CREATE_BY VARCHAR(255) 
	, CREATE_DATE DATETIME
	, LAST_MODIFIED_BY VARCHAR(255)
	, LAST_MODIFIED_DATE DATETIME
) DEFAULT CHARACTER SET UTF8;
CREATE INDEX IDX_NOTIFY ON NOTIFY(NOTIFY_START_DT, NOTIFY_END_DT);

DROP TABLE if exists USER_LIKE;
CREATE TABLE USER_LIKE (
	LIKE_USER VARCHAR(255) NOT NULL
	, LIKE_ISBN VARCHAR(255) NOT NULL
	, CREATE_BY VARCHAR(255)
	, CREATE_DATE DATETIME
	, LAST_MODIFIED_BY VARCHAR(255)
	, LAST_MODIFIED_DATE DATETIME
	, PRIMARY KEY (LIKE_USER, LIKE_ISBN)
	, FOREIGN KEY (LIKE_USER) REFERENCES users (USER_ID) ON DELETE CASCADE
	, FOREIGN KEY (LIKE_ISBN) REFERENCES book (BOOK_ISBN) ON DELETE CASCADE
) DEFAULT CHARACTER SET UTF8;