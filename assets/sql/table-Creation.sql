USE employeeData;

-- create division, job title, employee, and pay statement history tables

CREATE TABLE division (
    divisionID INT PRIMARY KEY AUTO_INCREMENT,
    divisionName VARCHAR(50) NOT NULL
);

CREATE TABLE jobTitle (
    jobTitleID INT PRIMARY KEY AUTO_INCREMENT,
    jobTitleName VARCHAR(50) NOT NULL
);

CREATE TABLE employee (
    empID INT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    SSN CHAR(9) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(15),
    hireDate DATE NOT NULL,
    salary DECIMAL(10,2) NOT NULL,
    jobTitleID INT,
    divisionID INT,
    CONSTRAINT unique_ssn UNIQUE (SSN),
    FOREIGN KEY (jobTitleID) REFERENCES jobTitle(jobTitleID),
    FOREIGN KEY (divisionID) REFERENCES division(divisionID)
);

CREATE TABLE payStatement (
    payStatementID INT PRIMARY KEY AUTO_INCREMENT,
    empID INT NOT NULL,
    payDate DATE NOT NULL,
    grossPay DECIMAL(10,2) NOT NULL,
    netPay DECIMAL(10,2) NOT NULL,
    deductions DECIMAL(10,2) DEFAULT 0.00,
    FOREIGN KEY (empID) REFERENCES employee(empID)
);

SHOW TABLES;