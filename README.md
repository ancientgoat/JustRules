# JustRules (JR)

## Introduction

JustRules uses Spring SpEL expressions to execute decision trees based on Rules in JSON snippets.
JustRules is meant to be an intermediate step between a Domain Specific Language (DSL) and storing and 
running rules to perform various actions.

Imagine you needed to run rules for a dating application that matched people together, or
to run rules to see if someone has entered all their information for a loan.  Or to see if 
someone is eligible or qualifies for a loan.  And you want to allow the rules to be authored 
by people other than programmers, people perhaps, who are paid less.  Enter DSL, semi-simple text files
that can be stored in a database, or a file system.  .....

## Installation

To set up the MySQL database you will need to have a 'test'
database and a 'root' user with access to that database.

## Running

To run this project, just navigate to the root of this project and run:
```
sbt run
```

TODO
* Git commit messages
x* sofi database (to 'test')
x* import order - static on top
2* javadoc
2* final
* source formatting in rules
X* hashcode, equals, and toString for entities
2* no log is Enabled ifs
