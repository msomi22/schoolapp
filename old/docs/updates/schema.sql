
-- -------------------
-- Table House
-- -------------------
CREATE TABLE House (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    houseName VARCHAR(200) NOT NULL,
    description VARCHAR(200) NOT NULL

);
ALTER TABLE House OWNER TO school;



-- -------------------
-- Table StudentHouse
----------------------
CREATE TABLE StudentHouse (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    studentId VARCHAR(100) REFERENCES Student(uuid),
    houseId VARCHAR(100) REFERENCES House(uuid),
    dateOut timestamp NOT NULL,
    dateIn timestamp with time zone DEFAULT now()
);
ALTER TABLE StudentHouse OWNER TO school;

-- -------------------
-- Table StudentMisc
----------------------
-- Stores any new property belonging to a student
CREATE TABLE StudentMisc (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    studentId VARCHAR(100) REFERENCES Student(uuid),
    key VARCHAR(255),
    value VARCHAR(255)
);
ALTER TABLE StudentMisc OWNER TO school;


---Future update
---examSubNumber can be 11 or 12 for form one, 11 or 10 for from 2, 8 for from 3 and 7 for form 4
---these are the number of subjects to be used in granding 
ALTER TABLE classRoom ADD COLUMN examSubNumber INTEGER NOT NULL; 
