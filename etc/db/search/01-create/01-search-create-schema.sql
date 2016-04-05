-- ###########################################
-- # Create
-- ###########################################
-- Create pk sequence
    


-- Create normal entities
    
        
CREATE TABLE TB_RECOMMENDED_KEYWORDS (
  ID NUMBER(19) NOT NULL,
  KEYWORD VARCHAR2(255 CHAR) NOT NULL,
  UPDATE_DATE_TZ VARCHAR2(50 CHAR),
  UPDATE_DATE TIMESTAMP,
  CREATED_DATE_TZ VARCHAR2(50 CHAR),
  CREATED_DATE TIMESTAMP,
  CREATED_BY VARCHAR2(50 CHAR),
  LAST_UPDATED_TZ VARCHAR2(50 CHAR),
  LAST_UPDATED TIMESTAMP,
  LAST_UPDATED_BY VARCHAR2(50 CHAR),
  VERSION NUMBER(19) NOT NULL
);


        
CREATE TABLE TB_RECOMMENDED_LINKS (
  ID NUMBER(19) NOT NULL,
  URL VARCHAR2(255 CHAR) NOT NULL,
  TITLE VARCHAR2(255 CHAR) NOT NULL,
  DESCRIPTION VARCHAR2(255 CHAR),
  UPDATE_DATE_TZ VARCHAR2(50 CHAR),
  UPDATE_DATE TIMESTAMP,
  CREATED_DATE_TZ VARCHAR2(50 CHAR),
  CREATED_DATE TIMESTAMP,
  CREATED_BY VARCHAR2(50 CHAR),
  LAST_UPDATED_TZ VARCHAR2(50 CHAR),
  LAST_UPDATED TIMESTAMP,
  LAST_UPDATED_BY VARCHAR2(50 CHAR),
  VERSION NUMBER(19) NOT NULL,
  RECOMMENDED_KEYWORD NUMBER(19) NOT NULL
);



-- Create many to many relations
    

-- Primary keys
    
        
ALTER TABLE TB_RECOMMENDED_KEYWORDS ADD CONSTRAINT PK_TB_RECOMMENDED_KEYWORDS
    PRIMARY KEY (ID)
;

        
ALTER TABLE TB_RECOMMENDED_LINKS ADD CONSTRAINT PK_TB_RECOMMENDED_LINKS
    PRIMARY KEY (ID)
;

    

-- Unique constraints
    



-- Foreign key constraints
    

  
  
ALTER TABLE TB_RECOMMENDED_LINKS ADD CONSTRAINT FK_TB_RECOMMENDED_LINKS_RECO86
    FOREIGN KEY (RECOMMENDED_KEYWORD) REFERENCES TB_RECOMMENDED_KEYWORDS (ID)
;

  

    

-- Index

