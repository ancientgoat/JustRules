# BetPlace

## Introduction

BetPlace is a simple web app built using the Play Framework and MySQL.
It provides the following services to its users:
- Register
- Login
- Make Bets with chosen odds and amounts
- View betting history

## Installation

To set up the MySQL database you will need to have a 'betplace'
database and a 'better' user with access to that database.

To accomplish this, login to your MySQL database as root and run the
following commands:
```
create database betplace;
create user better;
grant all on betplace.* to 'better'@'localhost';
```

## Running

To run this project, just navigate to the root of this project and run:
```
sbt run
```

## Uninstallation

To undo the MySQL configuration, removing the 'betplace' database and
'better' user, login to your MySQL database as root and do the
following: 
```
drop database betplace;
drop user better;
```
