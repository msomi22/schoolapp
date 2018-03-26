
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


ALTER TABLE classRoom ADD COLUMN examSubNumber INTEGER; 
ALTER TABLE Student ADD COLUMN indexNo VARCHAR;
ALTER TABLE StudentPrimary ADD COLUMN kcpeGrade VARCHAR;



ALTER TABLE outgoingsms
ALTER COLUMN status TYPE VARCHAR(50);


ALTER TABLE AcessLevel ADD COLUMN acessId VARCHAR;

 ALTER TABLE AcessLevel
  ALTER COLUMN acessId TYPE VARCHAR(50);