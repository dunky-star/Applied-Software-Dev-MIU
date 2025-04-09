
-- File: myCS489BankQueries.sql

-- Query 1: Display all accounts with customer info, sorted by balance DESC
SELECT 
  a.accountId,
  a.accountNumber,
  a.balance,
  a.status,
  a.dateOpened,
  c.customerId,
  c.firstName,
  c.lastName,
  c.telephoneNumber
FROM 
  Accounts a
JOIN 
  CustomerAccount ca ON a.accountId = ca.accountId
JOIN 
  Customers c ON ca.customerId = c.customerId
ORDER BY 
  a.balance DESC;

-- Query 2: Display all transactions > 500 with account numbers, sorted by date and time ASC
SELECT 
  t.transactionId,
  t.transactionNumber,
  t.description,
  t.valueAmount,
  t.transactionDate,
  t.transactionTime,
  t.transactionType,
  a.accountNumber
FROM 
  Transactions t
JOIN 
  Accounts a ON t.accountId = a.accountId
WHERE 
  t.valueAmount > 500.00
ORDER BY 
  t.transactionDate ASC, t.transactionTime ASC;
