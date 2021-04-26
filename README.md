# BankService

## Code Structure
- package client contains classes that makes request to external services
- package service contains classes that does business logic
- package config contains classes that configures beans, security
- package controller contains classes that are entry points to handle requests
- package repo contains classes that makes requests to DB
- package entity contains classes that defines mapping between DB tables and Java classes
- package schedule contains classes that handle business logic in schedule

## Database
voucher (
  id INT AUTO_INCREMENT PRIMARY KEY,
  phone_number VARCHAR(250) NOT NULL,
  voucher_code VARCHAR(250) NOT NULL,
  created_date datetime NOT NULL,
  processed boolean default false
)

## Framework and libraries
- Spring Boot, Spring Data JPA, Spring Security, Hibernate, H2

## Step to run application on local machine
- Checkout source code from github
- Install JDK-11, Maven
- Open terminal run cd to source code folder
- Run command: mvn clean package
- Then cd to target folder and run command: java -jar bank-service.jar

## Sample Requests
- curl --location --request GET 'http://localhost:8080/voucher?phoneNumber=0123456789'
- curl --location --request GET 'http://localhost:8080/voucher-history?phoneNumber=0123456789' --header 'Authorization: Basic YWRtaW46YWRtaW4='

# VoucherProvider

## Step to run application on local machine
- Checkout source code from github
- Install JDK-11, Maven
- Open terminal run cd to source code folder
- Run command: mvn clean package
- Then cd to target folder and run command: java -jar voucher-provider.jar

## Sample Requests
- curl --location --request GET 'http://localhost:9099/api/voucher' --header 'x-api-key: cd719eef-2cff-40ff-b966-f7fc9da065d1'

