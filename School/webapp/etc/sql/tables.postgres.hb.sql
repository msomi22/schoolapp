

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
ALTER TABLE Account OWNER TO school;
--- COPY old account info 




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
ALTER TABLE ApiCredential OWNER TO school;



CREATE TABLE Category (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    description VARCHAR(255) NOT NULL,
    maxNo integer NOT NULL
);
ALTER TABLE Category OWNER TO school;



---------------------------------------------------------------------------------
---------------  Subject
----------------------------------------------------------------------------------
ALTER TABLE Subject ADD COLUMN accountId VARCHAR(100) REFERENCES Account(uuid);
ALTER TABLE Subject ADD COLUMN categoryId VARCHAR(100) REFERENCES Category(uuid);
ALTER TABLE Subject ADD COLUMN code VARCHAR(10) NOT NULL;
ALTER TABLE Subject ADD COLUMN numericCode VARCHAR(10) NOT NULL;
ALTER TABLE Subject ADD COLUMN description VARCHAR(255) NOT NULL;
--INSERT INTO  Category and Subject





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
ALTER TABLE subCategory OWNER TO school;


-------------------------------------------------------------------------------
---------------- ClassRoom
--------------------------------------------------------------------------------
ALTER TABLE ClassRoom ADD COLUMN accountId VARCHAR(100) REFERENCES Account(uuid);
ALTER TABLE ClassRoom ADD COLUMN description VARCHAR(255) NOT NULL;
ALTER TABLE ClassRoom ADD COLUMN examSubNumber INTEGER NOT NULL;




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
ALTER TABLE Stream OWNER TO school;


    

----------------------------------------------------------------------------------
--------------  Student
------------------------------------------------------------------------------------
ALTER TABLE Student ADD COLUMN accountId VARCHAR(100) REFERENCES Account(uuid);
ALTER TABLE Student ADD COLUMN regStream VARCHAR(100) REFERENCES Stream(uuid);
ALTER TABLE Student ADD COLUMN currentStream VARCHAR(100) REFERENCES Stream(uuid);
ALTER TABLE Student ADD COLUMN isActive VARCHAR(2) NOT NULL;
ALTER TABLE Student ADD COLUMN isAlumni VARCHAR(2) NOT NULL;
ALTER TABLE Student ADD COLUMN isBoarding VARCHAR(2) NOT NULL;
ALTER TABLE Student ADD COLUMN isGoKFeeEligibe VARCHAR(2) NOT NULL;
ALTER TABLE Student ADD COLUMN regNo VARCHAR(50) NOT NULL;
ALTER TABLE Student ADD COLUMN indexNo VARCHAR(50) NOT NULL;
ALTER TABLE Student ADD COLUMN middlename VARCHAR(255) NOT NULL;
ALTER TABLE Student ADD COLUMN passport VARCHAR(255) NOT NULL;
ALTER TABLE Student ADD COLUMN lastUpdated VARCHAR(255) NOT NULL;
    
-------------------------------------------------------------------------------------
--------------  StudentSubject
--------------------------------------------------------------------------------------
ALTER TABLE StudentSubject ADD COLUMN accountId VARCHAR(100) REFERENCES Account(uuid);
ALTER TABLE StudentSubject ADD COLUMN studentId VARCHAR(100) REFERENCES Student(uuid);
ALTER TABLE StudentSubject ADD COLUMN subjectId VARCHAR(100) REFERENCES Subject(uuid);



-- --------------------------------------------------------------------------
--------------  House
------------------------------------------------------------------------------
ALTER TABLE House ADD COLUMN accountId VARCHAR(100) REFERENCES Account(uuid);
ALTER TABLE House ADD COLUMN description VARCHAR(200) NOT NULL;

 

-------------------------------------------------------------------------------------
-----------  StudentHouse
-------------------------------------------------------------------------------------
ALTER TABLE StudentHouse ADD COLUMN accountId VARCHAR(100) REFERENCES Account(uuid);
ALTER TABLE StudentHouse ADD COLUMN studentId VARCHAR(100) REFERENCES Student(uuid);
ALTER TABLE StudentHouse ADD COLUMN houseId VARCHAR(100) REFERENCES House(uuid);
ALTER TABLE StudentHouse ADD COLUMN dateOut timestamp NOT NULL;






-------------------------------------------------------------------------------------
-----------  StudentParent
--------------------------------------------------------------------------------------
ALTER TABLE StudentParent ADD COLUMN accountId VARCHAR(100) REFERENCES Account(uuid);
ALTER TABLE StudentParent ADD COLUMN studentId VARCHAR(100) REFERENCES Student(uuid);
ALTER TABLE StudentParent ADD COLUMN name VARCHAR(255) NOT NULL;
ALTER TABLE StudentParent ADD COLUMN mobile VARCHAR(15) NOT NULL;
ALTER TABLE StudentParent ADD COLUMN email VARCHAR(100) NOT NULL;
ALTER TABLE StudentParent ADD COLUMN lastUpdated VARCHAR(255) NOT NULL;




-------------------------------------------------------------------------------------
-----------   StudentPrimary
-------------------------------------------------------------------------------------
ALTER TABLE StudentPrimary ADD COLUMN accountId VARCHAR(100) REFERENCES Account(uuid);
ALTER TABLE StudentPrimary ADD COLUMN studentId VARCHAR(100) REFERENCES Student(uuid);
ALTER TABLE StudentPrimary ADD COLUMN kcpeGrade VARCHAR(50) NOT NULL;



CREATE TABLE AcessLevel (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(100) UNIQUE NOT NULL,
    accountId VARCHAR(100) REFERENCES Account(uuid),
    acessId VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL
);
ALTER TABLE AcessLevel OWNER TO school;


----------------------------------------------------------------------------------
------  Staff
-----------------------------------------------------------------------------------
ALTER TABLE Staff ADD COLUMN accountId VARCHAR(100) REFERENCES Account(uuid);
ALTER TABLE Staff ADD COLUMN acessLevelId VARCHAR(100) REFERENCES AcessLevel(uuid);
ALTER TABLE Staff ADD COLUMN staffNo VARCHAR(50) NOT NULL;
ALTER TABLE Staff ADD COLUMN isActive VARCHAR(2) NOT NULL;
ALTER TABLE Staff ADD COLUMN firstname VARCHAR(255) NOT NULL;
ALTER TABLE Staff ADD COLUMN middlename VARCHAR(255) NOT NULL;
ALTER TABLE Staff ADD COLUMN lastname VARCHAR(255) NOT NULL;
ALTER TABLE Staff ADD COLUMN  gender VARCHAR(10) NOT NULL;
ALTER TABLE Staff ADD COLUMN mobile VARCHAR(15) NOT NULL;
ALTER TABLE Staff ADD COLUMN email VARCHAR(100) NOT NULL;
ALTER TABLE Staff ADD COLUMN lastUpdated VARCHAR(255) NOT NULL;
ALTER TABLE Staff ADD COLUMN  regDate timestamp with time zone DEFAULT now();



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
ALTER TABLE TeacherSubject OWNER TO school;




----------------------------------------------------------------------------------
--------------- ClassTeacher
------------------------------------------------------------------------------------
ALTER TABLE ClassTeacher ADD COLUMN accountId VARCHAR(100) REFERENCES Account(uuid);
ALTER TABLE ClassTeacher ADD COLUMN teacherId VARCHAR(100) REFERENCES Staff(uuid);
ALTER TABLE ClassTeacher ADD COLUMN streamId VARCHAR(100) REFERENCES Stream(uuid);






---------------------------------------------------------------------------------
---------------  Exam
-----------------------------------------------------------------------------
ALTER TABLE Exam ADD COLUMN accountId VARCHAR(100) REFERENCES Account(uuid);
ALTER TABLE Exam ADD COLUMN code VARCHAR(100) NOT NULL;
ALTER TABLE Exam ADD COLUMN description VARCHAR(255) NOT NULL;



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

ALTER TABLE Performance OWNER TO school;


-----------------------------------------------------------------------    
---------------  GradingSystem
---------------------------------------------------------------------------------------
ALTER TABLE GradingSystem ADD COLUMN accountId VARCHAR(100) REFERENCES Account(uuid);
ALTER TABLE categoryId ADD COLUMN accountId VARCHAR(100) REFERENCES Category(uuid);
ALTER TABLE GradingSystem ADD COLUMN lowerLimit integer NOT NULL;
ALTER TABLE GradingSystem ADD COLUMN upperLimit integer NOT NULL;
ALTER TABLE GradingSystem ADD COLUMN description VARCHAR(255) NOT NULL;
ALTER TABLE GradingSystem ADD COLUMN points integer NOT NULL;


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
ALTER TABLE yearlyMean OWNER TO school;



-- -------------------
-- Table ClassMean  
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





---------------------------------------------------------------------------------
----------  PocketMoney
----------------------------------------------------------------------------------
ALTER TABLE PocketMoney ADD COLUMN accountId VARCHAR(100) REFERENCES Account(uuid);
ALTER TABLE PocketMoney ADD COLUMN studentId VARCHAR(100) REFERENCES Student(uuid);



----------------------------------------------------------------------------
------------   Deposit
--------------------------------------------------------------------------------
ALTER TABLE Deposit ADD COLUMN accountId VARCHAR(100) REFERENCES Account(uuid);
ALTER TABLE Deposit ADD COLUMN studentId VARCHAR(100) REFERENCES Student(uuid);
ALTER TABLE Deposit ADD COLUMN amount integer NOT NULL CHECK (amount>=0);
ALTER TABLE Deposit ADD COLUMN depositDate timestamp with time zone DEFAULT now();


-----------------------------------------------------------------------------------
--------------   Withdraw
------------------------------------------------------------------------------------
ALTER TABLE Withdraw ADD COLUMN accountId VARCHAR(100) REFERENCES Account(uuid);
ALTER TABLE Withdraw ADD COLUMN studentId VARCHAR(100) REFERENCES Student(uuid);
ALTER TABLE Withdraw ADD COLUMN amount integer NOT NULL CHECK (amount>=0);
ALTER TABLE Withdraw ADD COLUMN withdrawDate timestamp with time zone DEFAULT now();



-----------------------------------------------------------------------------------
-------------   TermFee
----------------------------------------------------------------------------------
ALTER TABLE TermFee ADD COLUMN accountId VARCHAR(100) REFERENCES Account(uuid);
ALTER TABLE TermFee ADD COLUMN boaderAmount integer NOT NULL CHECK (boaderAmount>=0);



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
ALTER TABLE StudentOtherFee OWNER TO school;



--------------------------------------------------------------------------------
-------------  RevertedMoney
----------------------------------------------------------------------------------
ALTER TABLE RevertedMoney ADD COLUMN accountId VARCHAR(100) REFERENCES Account(uuid);
ALTER TABLE RevertedMoney ADD COLUMN studentId VARCHAR(100) REFERENCES Student(uuid);
ALTER TABLE RevertedMoney ADD COLUMN otherFeeId VARCHAR(100) REFERENCES OtherFee(uuid);
ALTER TABLE RevertedMoney ADD COLUMN dateReverted timestamp with time zone DEFAULT now();




-- --------------------------------------------------------------------------
---------    StudentFee
-----------------------------------------------------------------------------
ALTER TABLE StudentFee ADD COLUMN accountId VARCHAR(100) REFERENCES Account(uuid);
ALTER TABLE StudentFee ADD COLUMN studentId VARCHAR(100) REFERENCES Student(uuid);
ALTER TABLE StudentFee ADD COLUMN payMode VARCHAR(255) NOT NULL;
ALTER TABLE StudentFee ADD COLUMN paidHas VARCHAR(100) NOT NULL;
ALTER TABLE StudentFee ADD COLUMN termPiad VARCHAR(10) NOT NULL;
ALTER TABLE StudentFee ADD COLUMN yearPaid VARCHAR(50) NOT NULL;
ALTER TABLE StudentFee ADD COLUMN transactingStaffId VARCHAR(100) NOT NULL;



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
ALTER TABLE Book OWNER TO school;



-- ----------------------------------------------------------------------
-------------   StudentBook
-------------------------------------------------------------------------
ALTER TABLE StudentBook ADD COLUMN accountId VARCHAR(100) REFERENCES Account(uuid);
ALTER TABLE StudentBook ADD COLUMN studentId VARCHAR(100) REFERENCES Student(uuid);
ALTER TABLE StudentBook ADD COLUMN bookId VARCHAR(100) REFERENCES Book(uuid);
ALTER TABLE StudentBook ADD COLUMN hasReturned VARCHAR(50) NOT NULL;

-------------------------------------------------------------------
----------  Miscellanous
-------------------------------------------------------------------

ALTER TABLE Miscellanous ADD COLUMN accountId VARCHAR(100) REFERENCES Account(uuid);

-----------------------------------------------------------------
 ---------- CHAT 
-----------------------------------------------------------------

ALTER TABLE chat ADD COLUMN accountId VARCHAR(100) REFERENCES Account(uuid);
ALTER TABLE chat ADD COLUMN senderId VARCHAR(100) REFERENCES Staff(uuid);
ALTER TABLE chat ADD COLUMN receiverId VARCHAR(100) REFERENCES Staff(uuid);
ALTER TABLE chat ADD COLUMN isRead VARCHAR(2) NOT NULL;
