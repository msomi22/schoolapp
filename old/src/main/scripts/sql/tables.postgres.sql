
-- Schema Name: schooldb
-- Username: school
-- Password: AllaManO1

-- These tables describe the database a School Management system

-- Make sure you have created a Postgres user with the above username, password
-- and appropriate permissions. For development environments, you can make the 
-- database user to be a superuser to allow for copying of external files. 

-- Then run the "dbSetup.sh" script in the bin folder of this project.

\c postgres

-- Then execute the following:
DROP DATABASE IF EXISTS schooldb; -- To drop a database you can't be logged into it. Drops if it exists.
CREATE DATABASE schooldb;

-- Connect with the database on the username
\c schooldb school



-- =========================
-- 1.  School Account Management
-- =========================


--------------------
-- Table Account
-- -------------------
CREATE TABLE  Account (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    isActive text,
    name text,
    motto text,
    website text,
    logo text,
    signature text,
    username text,
    password text,
    mobile text, 
    email text,
    address text,
    town text,
    isBoarding text,
    isMixed text,
    lastUpdated text,
    creationDate timestamp with time zone DEFAULT now()

);
\COPY Account(uuid,isActive,name,motto,website,logo,signature,username,password,mobile,email,address,town,isBoarding,isMixed,lastUpdated) FROM '/tmp/Account.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Account OWNER TO school;




-- -------------------
-- Table outGoingSMS
-- -------------------


CREATE TABLE  outGoingSMS (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    status text,
    mobile text,
    message text,
    smsCost text,
    sendDate timestamp with time zone DEFAULT now()
);
\COPY outGoingSMS(uuid,accountId,status,mobile,message,smsCost) FROM '/tmp/outGoingSMS.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE outGoingSMS OWNER TO school;

-- -------------------
-- Table smsApi
-- -------------------

CREATE TABLE  smsApi (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    apiKey text,
    apiPassword text
 
);
\COPY smsApi(uuid,accountId,apiKey,apiPassword) FROM '/tmp/smsApi.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE smsApi OWNER TO school;


-- =========================
-- 2.  Subject Management
-- =========================


-- -------------------
-- Table Category
-- -------------------
CREATE TABLE Category (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    description text,
    maxNo integer 
);
-- import data from the CSV file for the status table
\COPY Category(uuid,accountId,description,maxNo) FROM '/tmp/Category.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Category OWNER TO school;


-- -------------------
-- Table Subject
-- -------------------
CREATE TABLE Subject (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    categoryId text REFERENCES Category(uuid),
    code text UNIQUE ,
    numericCode text UNIQUE ,
    description text
);
-- import data from the CSV file for the status table
\COPY Subject(uuid,accountId,categoryId,code,numericCode,description) FROM '/tmp/Subject.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Subject OWNER TO school;



-- -------------------
-- Table subCategory
-- -------------------
CREATE TABLE subCategory (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    categoryId text REFERENCES Category(uuid),
    subjectId text REFERENCES Subject(uuid)
);
-- import data from the CSV file for the status table
\COPY subCategory(uuid,accountId,categoryId,subjectId) FROM '/tmp/subCategory.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE subCategory OWNER TO school;





-- =========================
-- 3.  ClassRoom Management
-- =========================
-- -------------------
-- Table classRoom
-- -------------------
CREATE TABLE classRoom (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    description text

);
-- import data from the CSV file for the status table
\COPY classRoom(uuid,accountId,description) FROM '/tmp/classRoom.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE classRoom OWNER TO school;


-- -------------------
-- Table Stream
-- -------------------
CREATE TABLE Stream (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    classRoomId text REFERENCES classRoom(uuid),
    description text

);
-- import data from the CSV file for the status table
\COPY Stream(uuid,accountId,classRoomId,description) FROM '/tmp/Stream.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Stream OWNER TO school;





-- ================================
-- ================================
-- 4. Students Management
-- ================================
-- ================================

-- ----------------
-- Table Student
-- ----------------
CREATE TABLE Student(
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    regStream text REFERENCES Stream(uuid),
    currentStream text REFERENCES Stream(uuid),
    isActive text,
    isAlumni text,
    isBoarding text,
    regNo text UNIQUE NOT NULL ,
    firstname text ,
    middlename text ,
    lastname text ,
    gender text,
    dob text,
    bcertNo text,
    county text,
    regTerm text,  
    finalYear Integer,  
    finalTerm Integer, 
    passport text,
    lastUpdated text,
    admissionDate timestamp with time zone DEFAULT now()
   
);

\COPY Student(uuid,accountId,regStream,currentStream,isActive,isAlumni,isBoarding,regNo,firstname,middlename,lastname,gender,dob,bcertNo,county,regTerm,finalYear,finalTerm,passport,lastUpdated) FROM '/tmp/Student.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Student OWNER TO school;


-- -------------------
-- StudentSubject
-- -------------------
CREATE TABLE StudentSubject (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    studentId text REFERENCES Student(Uuid),
    subjectId text REFERENCES Subject(uuid),
    allocationDate timestamp with time zone DEFAULT now()

);

\COPY StudentSubject(uuid,accountId,studentId,subjectId) FROM '/tmp/StudentSubject.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE StudentSubject OWNER TO school;


-- -------------------
-- Table StudentParent
----------------------
CREATE TABLE StudentParent (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    studentId text REFERENCES Student(uuid),
    name text ,
    mobile text ,
    email text,
    lastUpdated text
    


);
\COPY StudentParent(uuid,accountId,studentId,name,mobile,email,lastUpdated) FROM '/tmp/StudentParent.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE StudentParent OWNER TO school;



-- -------------------
-- Table StudentPrimary
----------------------
CREATE TABLE StudentPrimary (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    studentId text REFERENCES Student(uuid),
    schoolName text,
    index text,
    kcpeYear text ,
    kcpeMark text
);
\COPY StudentPrimary(uuid,accountId,studentId,schoolName,index,kcpeYear,kcpeMark) FROM '/tmp/StudentPrimary.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE StudentPrimary OWNER TO school;



-- ==================
-- ==================
-- .5 Staff Management
-- ==================
-- ==================

--------------------
-- Table AcessLevel
---------------------

CREATE TABLE AcessLevel (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    description text
);
\COPY AcessLevel(uuid,accountId,description) FROM '/tmp/AcessLevel.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE AcessLevel OWNER TO school;


-- -------------------
-- Table Staff
-- -------------------
CREATE TABLE Staff (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    acessLevelId text REFERENCES AcessLevel(uuid),
    staffNo text UNIQUE NOT NULL,
    isActive text,
    firstname text,
    middlename text,
    lastname text,
    gender text, 
    mobile text UNIQUE NOT NULL,
    email text UNIQUE NOT NULL,
    username text UNIQUE NOT NULL,
    password text,
    lastUpdated text,
    regDate timestamp with time zone DEFAULT now()
);
\COPY Staff(uuid,accountId,acessLevelId,staffNo,isActive,firstname,middlename,lastname,gender,mobile,email,username,password,lastUpdated) FROM '/tmp/Staff.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Staff OWNER TO school;
--uuid  accountId   acessLevelId    staffNo isActive    firstname   middlename  lastname    
--gender  mobile  email   username    password    lastUpdated

-- -------------------
-- Table TeacherSubject
-- -------------------

CREATE TABLE TeacherSubject (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    teacherId text REFERENCES Staff(uuid),
    subjectId text REFERENCES Subject(uuid),
    streamId text REFERENCES Stream(uuid),
    allocationDate timestamp with time zone DEFAULT now()
   
);
\COPY TeacherSubject(uuid,accountId,teacherId,subjectId,streamId) FROM '/tmp/TeacherSubject.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE TeacherSubject OWNER TO school;


-- -------------------
-- Table ClassTeacher
-- -------------------

CREATE TABLE ClassTeacher (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    teacherId text REFERENCES Staff(uuid),
    streamId text REFERENCES Stream(uuid)
        
   
);
\COPY ClassTeacher(uuid,accountId,teacherId,streamId) FROM '/tmp/ClassTeacher.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE ClassTeacher OWNER TO school;




-- ======================
-- ======================
-- 5. Exam Management
-- ======================
-- ======================



-- -------------------
-- Table Exam
-- -------------------
 CREATE TABLE  Exam (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    code text,
    description text, 
    outOf Integer
   
   
);

-- import data from the CSV file for the Accounts table
\COPY Exam(uuid,accountId,code,description,outOf) FROM '/tmp/Exam.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Exam OWNER TO school;




-- -------------------
-- Table Perfomance
-- -------------------
 CREATE TABLE  Perfomance (
    id SERIAL PRIMARY KEY,
    accountId text REFERENCES Account(uuid),
    studentId text REFERENCES Student(uuid),
    subjectId text REFERENCES Subject(uuid), 
    streamId text REFERENCES Stream(uuid),  
    classRoomId text REFERENCES classRoom(uuid),   
    examId text REFERENCES Exam(uuid),   
    score integer,                                                    
    term text,
    year text
 
);

-- import data from the CSV file for the Accounts table
\COPY Perfomance(accountId,studentId,subjectId,streamId,classRoomId,examId,score,term,year) FROM '/tmp/Perfomance.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Perfomance OWNER TO school;



-- -------------------
-- Table GradingSystem
-- -------------------
 CREATE TABLE  GradingSystem (
     id SERIAL PRIMARY KEY,
     uuid text UNIQUE NOT NULL,
     accountId text REFERENCES Account(uuid),
     categoryId text REFERENCES Category(uuid),
     lowerLimit integer,
     upperLimit integer,
     description text,
     points integer
  
);

-- import data from the CSV file for the GradingSystem table
\COPY GradingSystem(uuid,accountId,categoryId,lowerLimit,upperLimit,description,points) FROM '/tmp/GradingSystem.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE GradingSystem OWNER TO school;



-- -------------------
-- Table sysConfig
-- -------------------
 CREATE TABLE  sysConfig (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    examId text REFERENCES Exam(uuid), 
    term text,
    year text,
    cansendSMS text
   
);

-- import data from the CSV file for the sysConfig table
\COPY sysConfig(uuid,accountId,examId,term,year,cansendSMS) FROM '/tmp/sysConfig.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE sysConfig OWNER TO school;




-- -------------------
-- Table yearlyMean
-- -------------------
 CREATE TABLE  yearlyMean (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    studentId text REFERENCES Student(uuid),
    year text,
    meanOne float, 
    meanTwo float, 
    meanThree float 
   
   
);

-- import data from the CSV file for the yearlyMean table
\COPY yearlyMean(uuid,accountId,studentId,year,meanOne,meanTwo,meanThree) FROM '/tmp/yearlyMean.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE yearlyMean OWNER TO school;



-- ==================
-- ==================
-- .7 Pocket Money Management
-- ==================
-- ==================


-- -------------------
-- Table PocketMoney
-- -------------------

CREATE TABLE PocketMoney (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    studentId text REFERENCES Student(uuid),
    amount integer NOT NULL CHECK (amount>=0)
   

);
\COPY PocketMoney(uuid,accountId,studentId,amount) FROM '/tmp/PocketMoney.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE PocketMoney OWNER TO school;

-- -------------------
-- Table Deposit
-- -------------------

CREATE TABLE Deposit (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    studentId text REFERENCES Student(uuid),
    amount integer NOT NULL CHECK (amount>=0),
    depositDate timestamp with time zone DEFAULT now()
);
\COPY Deposit(uuid,accountId,studentId,amount) FROM '/tmp/Deposit.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Deposit OWNER TO school;


-- -------------------
-- Table  Withdraw
-- -------------------

CREATE TABLE  Withdraw (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    studentId text REFERENCES Student(uuid),
    amount integer NOT NULL CHECK (amount>=0),
    withdrawDate timestamp with time zone DEFAULT now()
);
\COPY Withdraw(uuid,accountId,studentId,amount) FROM '/tmp/Withdraw.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Withdraw OWNER TO school;


-- -------------------
-- Table  TermFee
-- -------------------

CREATE TABLE  TermFee (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    boaderAmount integer NOT NULL CHECK (boaderAmount>=0),
    dayAmount integer NOT NULL CHECK (dayAmount>=0),
    term text, 
    year text
  
);
\COPY TermFee(uuid,accountId,boaderAmount,dayAmount,term,year) FROM '/tmp/TermFee.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE TermFee OWNER TO school;



-- -------------------
-- Table  OtherFee
-- -------------------

CREATE TABLE  OtherFee (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    description text,
    amount integer NOT NULL CHECK (amount>=0),
    term text,
    year text
  
);
\COPY OtherFee(uuid,accountId,description,amount,term,year) FROM '/tmp/OtherFee.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE OtherFee OWNER TO school;



-- -------------------
-- Table  StudentOtherFee
-- -------------------

CREATE TABLE  StudentOtherFee (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    studentId text REFERENCES student(uuid),
    otherFeeId text REFERENCES OtherFee(uuid),
    amountPiad integer NOT NULL CHECK (amountPiad>=0),
    payMode text,
    termPiad text,
    yearPaid text,
    datePaid timestamp with time zone DEFAULT now()
  
  
);
\COPY StudentOtherFee(uuid,accountId,studentId,otherFeeId,amountPiad,payMode,termPiad,yearPaid) FROM '/tmp/StudentOtherFee.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE StudentOtherFee OWNER TO school;



-- -------------------
-- Table  RevertedMoney
-- -------------------

CREATE TABLE  RevertedMoney (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    studentId text REFERENCES student(uuid),
    otherFeeId text REFERENCES OtherFee(uuid),
    dateReverted timestamp with time zone DEFAULT now()
  
   

);
\COPY RevertedMoney(uuid,accountId,studentId,otherFeeId) FROM '/tmp/RevertedMoney.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE RevertedMoney OWNER TO school;



-- -------------------
-- Table  StudentFee
-- -------------------

CREATE TABLE  StudentFee (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    studentId text REFERENCES student(uuid),
    amountPaid integer NOT NULL CHECK (amountPaid>=0),
    payMode text,
    transactionId text,
    paidHas text,
    termPiad text,    
    yearPaid text,
    datePaid timestamp with time zone DEFAULT now()
   
);
\COPY StudentFee(uuid,accountId,studentId,amountPaid,payMode,transactionId,paidHas,termPiad,yearPaid) FROM '/tmp/StudentFee.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE StudentFee OWNER TO school;




-- -------------------
-- Table  Suspense
-- -------------------

CREATE TABLE  Suspense (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    studentId text REFERENCES student(uuid),
    otherFeeId text REFERENCES OtherFee(uuid),
    amountPiad integer NOT NULL CHECK (amountPiad>=0),
    payMode text,
    paidHas text,
    termPiad text,    
    yearPaid text,
    datePaid timestamp with time zone DEFAULT now()
 
);
ALTER TABLE Suspense OWNER TO school;





-- ==================
-- ==================
-- .8 Library Management
-- ==================
-- ==================
-- -------------------
-- Table  Book
-- -------------------

CREATE TABLE  Book (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    isbn text UNIQUE NOT NULL,
    author text,
    publisher text,
    title text,
    isAvailable text,
    category text,
    dateAdded timestamp with time zone DEFAULT now()


);
\COPY Book(uuid,accountId,isbn,author,publisher,title,isAvailable,category) FROM '/tmp/Book.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Book OWNER TO school;


-- -------------------
-- Table  StudentBook
-- -------------------

CREATE TABLE  StudentBook (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    studentId text REFERENCES student(uuid),
    bookId text REFERENCES Book(uuid),
    hasReturned text,
    returnDate text,
    borrowDate timestamp with time zone DEFAULT now()
    
);
\COPY StudentBook(uuid,accountId,studentId,bookId,hasReturned,returnDate) FROM '/tmp/StudentBook.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE StudentBook OWNER TO school;




--=========================
-- 9.  Miscellanous
-- =========================
-- -------------------
-- Table Miscellanous
-- -------------------
CREATE TABLE Miscellanous (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    key text,
    value text 

);
-- import data from the CSV file for the Miscellanous CSV file
\COPY Miscellanous(uuid,accountId,key,value) FROM '/tmp/Miscellanous.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Miscellanous OWNER TO school;




--=========================
-- 9.  Chat management
-- =========================
-- -------------------
-- Table chat
-- -------------------
CREATE TABLE chat (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    accountId text REFERENCES Account(uuid),
    senderId text REFERENCES Staff(uuid),
    receiverId text REFERENCES Staff(uuid),
    message text,
    isRead text,
    dateSent timestamp with time zone DEFAULT now()

);
ALTER TABLE chat OWNER TO school;


--=========================
-- 10.  AccData management
-- =========================
-- -------------------
-- Table AccData
-- -------------------
CREATE TABLE AccData (
    id SERIAL PRIMARY KEY,
    uuid text UNIQUE NOT NULL,
    pitch text,
    roll text,
    raw text,
    addDate timestamp with time zone DEFAULT now()

);
ALTER TABLE AccData OWNER TO school;


INSERT INTO AccData (uuid,pitch,roll,raw) VALUES ('79B82D8A-34B1-4E18-B04D-010265997C1F','1','2','3');
