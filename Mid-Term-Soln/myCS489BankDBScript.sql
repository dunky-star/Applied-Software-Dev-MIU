
-- File: myCS489BankDBScript.sql

CREATE TABLE Customers (
  customerId INT PRIMARY KEY,
  firstName VARCHAR(100) NOT NULL,
  lastName VARCHAR(100) NOT NULL,
  telephoneNumber VARCHAR(20)
);

CREATE TABLE AccountTypes (
  accountTypeId INT PRIMARY KEY,
  accountTypeName VARCHAR(100) NOT NULL
);

CREATE TABLE Accounts (
  accountId BIGINT PRIMARY KEY,
  accountNumber VARCHAR(50) NOT NULL UNIQUE,
  dateOpened DATE NOT NULL,
  status ENUM('Active', 'Dormant') NOT NULL,
  balance DECIMAL(15,2) NOT NULL,
  accountTypeId INT NOT NULL,
  FOREIGN KEY (accountTypeId) REFERENCES AccountTypes(accountTypeId)
);

CREATE TABLE Transactions (
  transactionId BIGINT PRIMARY KEY,
  transactionNumber VARCHAR(50) NOT NULL UNIQUE,
  description TEXT NOT NULL,
  valueAmount DECIMAL(15,2) NOT NULL,
  transactionDate DATE NOT NULL,
  transactionTime TIME NOT NULL,
  transactionType VARCHAR(50),
  accountId BIGINT NOT NULL,
  FOREIGN KEY (accountId) REFERENCES Accounts(accountId)
);

CREATE TABLE CustomerAccount (
  customerId INT NOT NULL,
  accountId BIGINT NOT NULL,
  PRIMARY KEY (customerId, accountId),
  FOREIGN KEY (customerId) REFERENCES Customers(customerId),
  FOREIGN KEY (accountId) REFERENCES Accounts(accountId)
);
