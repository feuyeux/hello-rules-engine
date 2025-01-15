-- Drop database if exists to start fresh
DROP DATABASE IF EXISTS rulebase;

-- Create database
CREATE DATABASE rulebase;

-- Connect to the database
\c rulebase;

-- Create rules table
CREATE TABLE rules (
    rule_namespace varchar(256) not null,
    rule_id varchar(512) not null,
    condition varchar(2000),
    action varchar(2000),
    priority integer,
    description varchar(1000),
    PRIMARY KEY(rule_namespace, rule_id)
);

-- Insert sample rules
INSERT INTO rules (rule_namespace, rule_id, condition, action, priority, description)
VALUES (
    'LOAN',
    '1',
    'input.monthlySalary >= 50000 && input.creditScore >= 800 && input.requestedLoanAmount < 4000000 && $(bank.target_done) == false',
    'output.setApprovalStatus(true);output.setSanctionedPercentage(90);output.setProcessingFees(8000);',
    1,
    'A person is eligible for Home loan?'
),
(
    'LOAN',
    '2', 
    'input.monthlySalary >= 30000 && input.creditScore >= 750 && input.requestedLoanAmount < 2000000',
    'output.setApprovalStatus(true);output.setSanctionedPercentage(75);output.setProcessingFees(5000);',
    2,
    'Medium risk loan approval'
);