create database `banking_app_theo`;
USE `banking_app_theo`;


CREATE TABLE `currencies` (
	`currencyName` varchar(100) NOT NULL,
    `abbreviation` varchar(20) NOT NULL,
    `valueDependingOnDollar` double NOT NULL,
PRIMARY KEY(`currencyName`));

insert into currencies values ("United States Dollar", "USD", 1);
insert into currencies values ("Euro", "EUR", 1.22);
insert into currencies values ("Romanian Leu", "RON", 0.25);

select * from `currencies`;


CREATE TABLE `individuals` (
    `email` varchar(100) NOT NULL,
    `phoneNumber` varchar(20) NOT NULL,
    `identityNumber` varchar(30) NOT NULL,
    `firstName` varchar(100) NOT NULL,
	`lastName` varchar(100) NOT NULL,
PRIMARY KEY(`identityNumber`));

insert into individuals values ("marcelus@gmail.com", "0712345678", "5000520999999", "Marcelus", "Wallace");
insert into individuals values ("john@gmail.com", "0712345679", "5000520999998", "John", "Travolta");
insert into individuals values ("bavaroti@yahoo.com", "0798888888", "5000520999997", "Johnny", "Bavaroti");

select * from `individuals`;


CREATE TABLE `companies` (
    `email` varchar(100) NOT NULL,
    `phoneNumber` varchar(20) NOT NULL,
    `fiscalCode` varchar(30) NOT NULL,
    `companyName` varchar(100) NOT NULL,
PRIMARY KEY(`fiscalCode`));

insert into companies values ("office@th_experts.com", "0777777777", "22111111", "TH Experts");
insert into companies values ("contabilitate@mirano.com", "0777577777", "99987655", "Mirano Bags");
insert into companies values ("office@th_industries.com", "0777777766", "22111139", "TH Industries");

select * from `companies`;


CREATE TABLE `accounts` (
	`iban` varchar(40) NOT NULL,
    `clientId` varchar(30) NOT NULL,
    `currencyName` varchar(100) NOT NULL,
    `numberOfUnits` double NOT NULL,
PRIMARY KEY(`iban`));

insert into accounts values ("RO26THBN2753908718931USD", "22111111", "United States Dollar", 70000);
insert into accounts values ("RO40THBN2697604393945USD", "1990520995398", "United States Dollar", 5000);
insert into accounts values ("RO65THBN0155747391815USD", "5000520999997", "United States Dollar", 1000);
insert into accounts values ("RO81THBN1704883885422USD", "22111139", "United States Dollar", 300);

select * from `accounts`;


CREATE TABLE `debitCards` (
	`cardNumber` varchar(40) NOT NULL,
    `expirationDate` date NOT NULL,
    `holderName` varchar(100) NOT NULL,
	`cvv` varchar(5) NOT NULL,
    `accountIban` varchar(40) NOT NULL,
    `frozen` boolean NOT NULL,
PRIMARY KEY(`cardNumber`));

insert into debitCards values("0703509990891319", '2025-01-08', "Hans Landa", "379", "RO40THBN2697604393945USD", 0);
insert into debitCards values("0743242391315439", '2025-01-08', "Hans Landa", "342", "RO40THBN2697604393945USD", 0);
insert into debitCards values("0432423555325391", '2025-01-08', "Hans Landa", "424", "RO40THBN2697604393945USD", 0);

select * from `debitCards`;


CREATE TABLE `creditCards` (
	`cardNumber` varchar(40) NOT NULL,
    `expirationDate` date NOT NULL,
    `holderName` varchar(100) NOT NULL,
	`cvv` varchar(5) NOT NULL,
    `accountIban` varchar(40) NOT NULL,
    `frozen` boolean NOT NULL,
    `cardLimit` double NOT NULL,
PRIMARY KEY(`cardNumber`));

insert into creditCards values("0989854900108314", '2025-01-08', "Hans Landa", "498", "RO40THBN2697604393945USD", 0, 5000);
insert into creditCards values("8732423413154393", '2025-01-08', "Hans Landa", "889", "RO40THBN2697604393945USD", 0, 5000);
insert into creditCards values("1132442342325391", '2025-01-08', "Hans Landa", "963", "RO40THBN2697604393945USD", 0, 5000);

select * from `creditCards`;


