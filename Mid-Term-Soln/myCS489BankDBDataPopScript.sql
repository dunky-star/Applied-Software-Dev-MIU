
-- File: myCS489BankDBDataPopScript.sql

-- Insert Account Types
INSERT INTO AccountTypes VALUES (1, 'Checking');
INSERT INTO AccountTypes VALUES (2, 'Savings');
INSERT INTO AccountTypes VALUES (3, 'Loan');

-- Insert Customers
INSERT INTO Customers VALUES (1, 'Daniel', 'Agar', NULL);
INSERT INTO Customers VALUES (2, 'Bernard', 'Shaw', '(641) 472-1234');
INSERT INTO Customers VALUES (3, 'Carly', 'DeFiori', NULL);

-- Insert Accounts
INSERT INTO Accounts VALUES (1, 'CK1089', '2021-10-15', 'Active', 105945.50, 2);
INSERT INTO Accounts VALUES (2, 'SV1104', '2019-06-22', 'Active', 197750.00, 1);
INSERT INTO Accounts VALUES (3, 'SV2307', '2014-02-27', 'Dormant', 842000.75, 1);
INSERT INTO Accounts VALUES (4, 'LN4133', '2005-11-07', 'Active', 674500.00, 3);

-- Insert CustomerAccount associations (joint accounts)
INSERT INTO CustomerAccount VALUES (3, 1); -- Carly owns CK1089
INSERT INTO CustomerAccount VALUES (1, 2); -- Daniel owns SV1104
INSERT INTO CustomerAccount VALUES (2, 2); -- Bernard also owns SV1104
INSERT INTO CustomerAccount VALUES (3, 3); -- Carly owns SV2307
INSERT INTO CustomerAccount VALUES (3, 4); -- Carly owns LN4133

-- Insert Transactions
INSERT INTO Transactions (
  transactionId, transactionNumber, description, valueAmount,
  transactionDate, transactionTime, transactionType, accountId
) VALUES
(1, 'D0187-175', 'Deposit to Savings', 100000.00, '2021-10-15', '12:15:00', 'Deposit', 2),
(2, 'W1736-142', 'Teller counter withdrawal', 550.00, '2022-08-24', '10:05:00', 'Withdrawal', 1),
(3, 'DD001-142', 'Direct deposit – wage', 2475.75, '2014-03-01', '05:00:00', 'Direct deposit', 1),
(4, 'P162-0017', 'Merch purchase online', 150.95, '2019-12-15', '14:19:00', 'Purchase', 1);
