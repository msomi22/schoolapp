
ALTER TABLE SchoolAccount ADD COLUMN schoolMotto text;
ALTER TABLE SchoolAccount ADD COLUMN website text;
ALTER TABLE SchoolAccount ADD COLUMN dayBoarding text;
UPDATE SchoolAccount SET schoolMotto = 'na',website='na',dayBoarding='YES' WHERE Uuid = ''


CREATE TABLE  smsApi (
    Id SERIAL PRIMARY KEY,
    Uuid text UNIQUE NOT NULL,
    SchoolAccountUuid text REFERENCES SchoolAccount(uuid),
    apiKey text,
    apiPassword text
    

);
ALTER TABLE smsApi OWNER TO school;

INSERT INTO smsApi (Uuid,SchoolAccountUuid,apiKey,apiPassword) VALUES("","","","");


CREATE TABLE  StudentPhoto (
    Id SERIAL PRIMARY KEY,
    Uuid text UNIQUE NOT NULL,
    studentUuid text REFERENCES Student(uuid),
    imagePath text
);
ALTER TABLE StudentPhoto OWNER TO school;