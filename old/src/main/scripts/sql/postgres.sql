

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
    uuid VARCHAR(100) UNIQUE NOT NULL,
    isActive VARCHAR(2) NOT NULL, 
    name VARCHAR(255) NOT NULL, 
    motto VARCHAR(255) NOT NULL,
    website VARCHAR(255) NOT NULL,
    logo VARCHAR(255) NOT NULL,
    signature VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    mobile VARCHAR(15) NOT NULL, 
    email VARCHAR(100) NOT NULL,
    address VARCHAR(100) NOT NULL,
    town VARCHAR(100) NOT NULL,
    isBoarding VARCHAR(2) NOT NULL,
    isMixed VARCHAR(2) NOT NULL,
    lastUpdated VARCHAR(255) NOT NULL,
    creationDate timestamp with time zone DEFAULT now()

);
--\COPY Account(uuid,isActive,name,motto,website,logo,signature,username,password,mobile,email,address,town,isBoarding,isMixed,lastUpdated) FROM '/tmp/Account.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Account OWNER TO school;




-- -------------------
-- Table outGoingSMS
-- -------------------


CREATE TABLE  outGoingSMS (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    status VARCHAR(2) NOT NULL,
    mobile VARCHAR(15) NOT NULL,
    message text NOT NULL,
    smsCost VARCHAR(50) NOT NULL,
    sendDate timestamp with time zone DEFAULT now()
);
--\COPY outGoingSMS(uuid,accountId,status,mobile,message,smsCost) FROM '/tmp/outGoingSMS.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE outGoingSMS OWNER TO school;



-- -------------------
-- Table incomingSMS
-- -------------------

CREATE TABLE  incomingSMS (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    mobile VARCHAR(15) NOT NULL,
    message text NOT NULL,
    receiveDate timestamp with time zone DEFAULT now()
);
--\COPY incomingSMS(uuid,accountId,mobile,message) FROM '/tmp/incomingSMS.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE incomingSMS OWNER TO school;

-- -------------------
-- Table ApiCredential 
-- -------------------

CREATE TABLE  ApiCredential (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    apiType VARCHAR(100) NOT NULL,
    apiKey text NOT NULL,
    apisecret VARCHAR(255) NOT NULL
 
);
--\COPY ApiCredential(uuid,accountId,apiType,apiKey,apisecret) FROM '/tmp/ApiCredential.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE ApiCredential OWNER TO school;


-- =========================
-- 2.  Subject Management
-- =========================


-- -------------------
-- Table Category
-- -------------------
CREATE TABLE Category (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    description VARCHAR(255) NOT NULL,
    maxNo integer NOT NULL
);
-- import data from the CSV file for the status table
--\COPY Category(uuid,accountId,description,maxNo) FROM '/tmp/Category.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Category OWNER TO school;


-- -------------------
-- Table Subject
-- -------------------
CREATE TABLE Subject (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    categoryId VARCHAR(100) REFERENCES Category(uuid),
    code VARCHAR(10) NOT NULL,
    numericCode VARCHAR(10) NOT NULL,
    description VARCHAR(255) NOT NULL
);
-- import data from the CSV file for the status table
--\COPY Subject(uuid,accountId,categoryId,code,numericCode,description) FROM '/tmp/Subject.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Subject OWNER TO school;



-- -------------------
-- Table subCategory
-- -------------------
CREATE TABLE subCategory (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    categoryId VARCHAR(100) REFERENCES Category(uuid),
    subjectId VARCHAR(100) REFERENCES Subject(uuid)
);
-- import data from the CSV file for the status table
--\COPY subCategory(uuid,accountId,categoryId,subjectId) FROM '/tmp/subCategory.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE subCategory OWNER TO school;





-- =========================
-- 3.  ClassRoom Management
-- =========================
-- -------------------
-- Table classRoom
-- -------------------
CREATE TABLE classRoom (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    description VARCHAR(255) NOT NULL

);
-- import data from the CSV file for the status table
--\COPY classRoom(uuid,accountId,description) FROM '/tmp/classRoom.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE classRoom OWNER TO school;


-- -------------------
-- Table Stream
-- -------------------
CREATE TABLE Stream (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    classRoomId VARCHAR(100) REFERENCES classRoom(uuid),
    description VARCHAR(255) NOT NULL

);
-- import data from the CSV file for the status table
--\COPY Stream(uuid,accountId,classRoomId,description) FROM '/tmp/Stream.csv' WITH DELIMITER AS '|' CSV HEADER
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
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    regStream VARCHAR(100) REFERENCES Stream(uuid),
    currentStream VARCHAR(100) REFERENCES Stream(uuid),
    isActive VARCHAR(2) NOT NULL,
    isAlumni VARCHAR(2) NOT NULL,
    isBoarding VARCHAR(2) NOT NULL,
    isGoKFeeEligibe VARCHAR(2) NOT NULL,
    regNo VARCHAR(50) NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    middlename VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    dob VARCHAR(50) NOT NULL,
    bcertNo VARCHAR(50) NOT NULL,
    county VARCHAR(255) NOT NULL,
    regTerm VARCHAR(10) NOT NULL,  
    finalYear Integer NOT NULL,  
    finalTerm Integer NOT NULL, 
    passport VARCHAR(255) NOT NULL,
    lastUpdated VARCHAR(255) NOT NULL,
    admissionDate timestamp with time zone DEFAULT now()
   
);

--\COPY Student(uuid,accountId,regStream,currentStream,isActive,isAlumni,isBoarding,regNo,firstname,middlename,lastname,gender,dob,bcertNo,county,regTerm,finalYear,finalTerm,passport,lastUpdated) FROM '/tmp/Student.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Student OWNER TO school;


-- -------------------
-- StudentSubject
-- -------------------
CREATE TABLE StudentSubject (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    studentId VARCHAR(100) REFERENCES Student(Uuid),
    subjectId VARCHAR(100) REFERENCES Subject(uuid),
    allocationDate timestamp with time zone DEFAULT now()

);

--\COPY StudentSubject(uuid,accountId,studentId,subjectId) FROM '/tmp/StudentSubject.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE StudentSubject OWNER TO school;


-- -------------------
-- Table StudentParent
----------------------
CREATE TABLE StudentParent (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    studentId VARCHAR(100) REFERENCES Student(uuid),
    name VARCHAR(255) NOT NULL,
    mobile VARCHAR(15) NOT NULL,
    email VARCHAR(100) NOT NULL,
    lastUpdated VARCHAR(255) NOT NULL
    


);
--\COPY StudentParent(uuid,accountId,studentId,name,mobile,email,lastUpdated) FROM '/tmp/StudentParent.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE StudentParent OWNER TO school;



-- -------------------
-- Table StudentPrimary
----------------------
CREATE TABLE StudentPrimary (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    studentId VARCHAR(100) REFERENCES Student(uuid),
    schoolName VARCHAR(255) NOT NULL,
    index VARCHAR(100) NOT NULL,
    kcpeYear VARCHAR(50) NOT NULL,
    kcpeMark VARCHAR(50) NOT NULL
);
--\COPY StudentPrimary(uuid,accountId,studentId,schoolName,index,kcpeYear,kcpeMark) FROM '/tmp/StudentPrimary.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE StudentPrimary OWNER TO school;


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
CREATE TABLE StudentMisc (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    studentId VARCHAR(100) REFERENCES Student(uuid),
    key VARCHAR(255),
    value VARCHAR(255)
);
ALTER TABLE StudentMisc OWNER TO school;



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
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    acessId VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL
);
--\COPY AcessLevel(uuid,accountId,description) FROM '/tmp/AcessLevel.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE AcessLevel OWNER TO school;


-- -------------------
-- Table Staff
-- -------------------
CREATE TABLE Staff (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    acessLevelId VARCHAR(100) REFERENCES AcessLevel(uuid),
    staffNo VARCHAR(50) NOT NULL,
    isActive VARCHAR(2) NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    middlename VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL, 
    gender VARCHAR(10) NOT NULL, 
    mobile VARCHAR(15) NOT NULL,
    email VARCHAR(100) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    lastUpdated VARCHAR(255) NOT NULL,
    regDate timestamp with time zone DEFAULT now()
);
--\COPY Staff(uuid,accountId,acessLevelId,staffNo,isActive,firstname,middlename,lastname,gender,mobile,email,username,password,lastUpdated) FROM '/tmp/Staff.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Staff OWNER TO school;
--uuid  accountId   acessLevelId    staffNo isActive    firstname   middlename  lastname    
--gender  mobile  email   username    password    lastUpdated

-- -------------------
-- Table TeacherSubject
-- -------------------

CREATE TABLE TeacherSubject (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    teacherId VARCHAR(100) REFERENCES Staff(uuid),
    subjectId VARCHAR(100) REFERENCES Subject(uuid),
    streamId VARCHAR(100) REFERENCES Stream(uuid),
    allocationDate timestamp with time zone DEFAULT now()
   
);
--\COPY TeacherSubject(uuid,accountId,teacherId,subjectId,streamId) FROM '/tmp/TeacherSubject.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE TeacherSubject OWNER TO school;


-- -------------------
-- Table ClassTeacher
-- -------------------

CREATE TABLE ClassTeacher (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    teacherId VARCHAR(100) REFERENCES Staff(uuid),
    streamId VARCHAR(100) REFERENCES Stream(uuid)
        
   
);
--\COPY ClassTeacher(uuid,accountId,teacherId,streamId) FROM '/tmp/ClassTeacher.csv' WITH DELIMITER AS '|' CSV HEADER
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
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    code VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL, 
    outOf Integer NOT NULL
   
   
);

-- import data from the CSV file for the Accounts table
--\COPY Exam(uuid,accountId,code,description,outOf) FROM '/tmp/Exam.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Exam OWNER TO school;




-- -------------------
-- Table Performance
-- -------------------
 CREATE TABLE  Performance (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    studentId VARCHAR(100) REFERENCES Student(uuid),
    subjectId VARCHAR(100) REFERENCES Subject(uuid), 
    streamId VARCHAR(100) REFERENCES Stream(uuid),  
    classRoomId VARCHAR(100) REFERENCES classRoom(uuid),   
    examId VARCHAR(100) REFERENCES Exam(uuid),   
    score integer NOT NULL,  
    paper1 integer NOT NULL,  
    paper2 integer NOT NULL,  
    paper3 integer NOT NULL,                                                    
    term VARCHAR(10) NOT NULL,
    year VARCHAR(50) NOT NULL
 
);

-- import data from the CSV file for the Accounts table
--\COPY Performance(accountId,studentId,subjectId,streamId,classRoomId,examId,score,term,year) FROM '/tmp/Perfomance.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Performance OWNER TO school;



-- -------------------
-- Table GradingSystem
-- -------------------
 CREATE TABLE  GradingSystem (
     id SERIAL PRIMARY KEY,
     uuid VARCHAR(100) UNIQUE NOT NULL,
     accountId VARCHAR(100) REFERENCES Account(uuid),
     categoryId VARCHAR(100) REFERENCES Category(uuid),
     lowerLimit integer NOT NULL,
     upperLimit integer NOT NULL,
     description VARCHAR(255) NOT NULL,
     points integer NOT NULL
  
);

-- import data from the CSV file for the GradingSystem table
--\COPY GradingSystem(uuid,accountId,categoryId,lowerLimit,upperLimit,description,points) FROM '/tmp/GradingSystem.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE GradingSystem OWNER TO school;



-- -------------------
-- Table sysConfig
-- -------------------
 CREATE TABLE  sysConfig (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    term VARCHAR(10) NOT NULL,
    year VARCHAR(50) NOT NULL,
    cansendSMS VARCHAR(2) NOT NULL
   
);

-- import data from the CSV file for the sysConfig table
--\COPY sysConfig(uuid,accountId,examId,term,year,cansendSMS) FROM '/tmp/sysConfig.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE sysConfig OWNER TO school;




-- -------------------
-- Table yearlyMean
-- -------------------
 CREATE TABLE  yearlyMean (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    studentId VARCHAR(100) REFERENCES Student(uuid),
    classId VARCHAR(100) REFERENCES classRoom(uuid),  
    year VARCHAR(50) NOT NULL,
    meanOne float NOT NULL, 
    meanTwo float NOT NULL, 
    meanThree float NOT NULL,
    termOnePosition VARCHAR(50) NOT NULL,
    termTwoPosition VARCHAR(50) NOT NULL,
    termThreePosition VARCHAR(50) NOT NULL
   
   
);

-- import data from the CSV file for the yearlyMean table
--\COPY yearlyMean(uuid,accountId,studentId,year,meanOne,meanTwo,meanThree) FROM '/tmp/yearlyMean.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE yearlyMean OWNER TO school;



-- -------------------
-- Table ClassMean  (ALTER TABLE ClassMean ADD COLUMN examId text;)
-- -------------------
 CREATE TABLE  ClassMean (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    classId VARCHAR(100) REFERENCES classRoom(uuid),
    streamId VARCHAR(100) REFERENCES Stream(uuid),
    examId VARCHAR(255) NOT NULL,
    classmean float NOT NULL, 
    streammean float NOT NULL, 
    term VARCHAR(10) NOT NULL,
    year VARCHAR(50) NOT NULL,
    dateAdded timestamp with time zone DEFAULT now()
   
   
);
ALTER TABLE ClassMean OWNER TO school;
--uuid,accountId,classId,streamId,classmean,streammean,term,year,dateAdded



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
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    studentId VARCHAR(100) REFERENCES Student(uuid),
    amount integer NOT NULL CHECK (amount>=0)
   

);
--\COPY PocketMoney(uuid,accountId,studentId,amount) FROM '/tmp/PocketMoney.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE PocketMoney OWNER TO school;

-- -------------------
-- Table Deposit
-- -------------------

CREATE TABLE Deposit (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    studentId VARCHAR(100) REFERENCES Student(uuid),
    amount integer NOT NULL CHECK (amount>=0),
    depositDate timestamp with time zone DEFAULT now()
);
--\COPY Deposit(uuid,accountId,studentId,amount) FROM '/tmp/Deposit.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Deposit OWNER TO school;


-- -------------------
-- Table  Withdraw
-- -------------------

CREATE TABLE  Withdraw (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    studentId VARCHAR(100) REFERENCES Student(uuid),
    amount integer NOT NULL CHECK (amount>=0),
    withdrawDate timestamp with time zone DEFAULT now()
);
--\COPY Withdraw(uuid,accountId,studentId,amount) FROM '/tmp/Withdraw.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Withdraw OWNER TO school;


-- -------------------
-- Table  TermFee
-- -------------------

CREATE TABLE  TermFee (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    boaderAmount integer NOT NULL CHECK (boaderAmount>=0),
    dayAmount integer NOT NULL CHECK (dayAmount>=0),
    term VARCHAR(10) NOT NULL, 
    year VARCHAR(50) NOT NULL
  
);
--\COPY TermFee(uuid,accountId,boaderAmount,dayAmount,term,year) FROM '/tmp/TermFee.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE TermFee OWNER TO school;



-- -------------------
-- Table  OtherFee
-- -------------------

CREATE TABLE  OtherFee (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    description VARCHAR(255) NOT NULL,
    amount integer NOT NULL CHECK (amount>=0),
    term VARCHAR(10) NOT NULL,
    year VARCHAR(50) NOT NULL
  
);
--\COPY OtherFee(uuid,accountId,description,amount,term,year) FROM '/tmp/OtherFee.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE OtherFee OWNER TO school;


-- -------------------
-- Table  FeeBreakdown 
-- -------------------

CREATE TABLE  FeeBreakdown (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    feeCategory VARCHAR(255) NOT NULL,
    term VARCHAR(10) NOT NULL,
    year VARCHAR(50) NOT NULL,
    status VARCHAR(100) NOT NULL,
    amount integer NOT NULL CHECK (amount>=0)
  
);
--\COPY FeeBreakdown(uuid,accountId,feeCategory,term,year,status,amount) FROM '/tmp/FeeBreakdown.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE FeeBreakdown OWNER TO school;

-- -------------------
-- Table  GokeMoneyUsage  
-- -------------------
CREATE TABLE  GokeMoneyUsage (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    numberOfStudents integer NOT NULL,
    amountPerStudent integer NOT NULL,
    totalAmount integer NOT NULL, 
    balance integer NOT NULL,  
    term VARCHAR(10) NOT NULL,
    year VARCHAR(50) NOT NULL,
    dateAllocated timestamp with time zone DEFAULT now()
  
);
ALTER TABLE GokeMoneyUsage OWNER TO school;



-- -------------------
-- Table  FeeBreakdownDesc 
-- -------------------

CREATE TABLE  FeeBreakdownDesc (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    feeBreakdownId VARCHAR(100) REFERENCES FeeBreakdown(uuid),
    feeCode VARCHAR(100) NOT NULL,
    feeDescription VARCHAR(255) NOT NULL,
    amount integer NOT NULL CHECK (amount>=0)
  
);
--\COPY FeeBreakdownDesc(uuid,accountId,feeBreakdownId,feeCode,feeDescription,amount) FROM '/tmp/FeeBreakdownList.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE FeeBreakdownDesc OWNER TO school;



-- -------------------
-- Table  StudentOtherFee
-- -------------------

CREATE TABLE  StudentOtherFee (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    studentId VARCHAR(100) REFERENCES student(uuid),
    otherFeeId VARCHAR(100) REFERENCES OtherFee(uuid),
    term VARCHAR(10) NOT NULL,
    dateAllocated timestamp with time zone DEFAULT now()
  
  
);
--\COPY StudentOtherFee(uuid,accountId,studentId,otherFeeId,term) FROM '/tmp/StudentOtherFee.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE StudentOtherFee OWNER TO school;



-- -------------------
-- Table  RevertedMoney
-- -------------------

CREATE TABLE  RevertedMoney (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    studentId VARCHAR(100) REFERENCES student(uuid),
    otherFeeId VARCHAR(100) REFERENCES OtherFee(uuid),
    dateReverted timestamp with time zone DEFAULT now()
  
   

);
--\COPY RevertedMoney(uuid,accountId,studentId,otherFeeId) FROM '/tmp/RevertedMoney.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE RevertedMoney OWNER TO school;



-- -------------------
-- Table  StudentFee
-- -------------------

CREATE TABLE  StudentFee (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    studentId VARCHAR(100) REFERENCES student(uuid),
    amountPaid integer NOT NULL CHECK (amountPaid>=0),
    payMode VARCHAR(255) NOT NULL,
    transactionId VARCHAR(255) NOT NULL,
    paidHas VARCHAR(100) NOT NULL,
    termPiad VARCHAR(10) NOT NULL,    
    yearPaid VARCHAR(50) NOT NULL,
    transactingStaffId VARCHAR(100) NOT NULL, 
    datePaid timestamp with time zone DEFAULT now()
   
);
--\COPY StudentFee(uuid,accountId,studentId,amountPaid,payMode,transactionId,paidHas,termPiad,yearPaid,transactingStaffId) FROM '/tmp/StudentFee.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE StudentFee OWNER TO school;




-- -------------------
-- Table  Suspense
-- -------------------

CREATE TABLE  Suspense (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    studentId VARCHAR(100) REFERENCES student(uuid),
    otherFeeId VARCHAR(100) REFERENCES OtherFee(uuid),
    amountPiad integer NOT NULL CHECK (amountPiad>=0),
    payMode VARCHAR(255) NOT NULL,
    paidHas VARCHAR(100) NOT NULL,
    termPiad VARCHAR(10) NOT NULL,    
    yearPaid VARCHAR(50) NOT NULL,
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
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    isbn VARCHAR(255) UNIQUE NOT NULL,
    author VARCHAR(255) NOT NULL,
    publisher VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    isAvailable VARCHAR(10) NOT NULL,
    category VARCHAR(255) NOT NULL,
    dateAdded timestamp with time zone DEFAULT now()


);
--\COPY Book(uuid,accountId,isbn,author,publisher,title,isAvailable,category) FROM '/tmp/Book.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Book OWNER TO school;


-- -------------------
-- Table  StudentBook
-- -------------------

CREATE TABLE  StudentBook (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    studentId VARCHAR(100) REFERENCES student(uuid),
    bookId VARCHAR(100) REFERENCES Book(uuid),
    hasReturned VARCHAR(50) NOT NULL,
    returnDate VARCHAR(255) NOT NULL,
    borrowDate timestamp with time zone DEFAULT now()
    
);
--\COPY StudentBook(uuid,accountId,studentId,bookId,hasReturned,returnDate) FROM '/tmp/StudentBook.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE StudentBook OWNER TO school;




--=========================
-- 9.  Miscellanous
-- =========================
-- -------------------
-- Table Miscellanous
-- -------------------
CREATE TABLE Miscellanous (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    key VARCHAR(255) NOT NULL,
    value VARCHAR(255) NOT NULL

);
-- import data from the CSV file for the Miscellanous CSV file
--\COPY Miscellanous(uuid,accountId,key,value) FROM '/tmp/Miscellanous.csv' WITH DELIMITER AS '|' CSV HEADER
ALTER TABLE Miscellanous OWNER TO school;




--=========================
-- 9.  Chat management
-- =========================
-- -------------------
-- Table chat
-- -------------------
CREATE TABLE chat (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    senderId VARCHAR(100) REFERENCES Staff(uuid),
    receiverId VARCHAR(100) REFERENCES Staff(uuid),
    message text NOT NULL,
    isRead VARCHAR(2) NOT NULL, 
    dateSent timestamp with time zone DEFAULT now()

);
ALTER TABLE chat OWNER TO school;

