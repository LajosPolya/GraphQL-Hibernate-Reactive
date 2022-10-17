# GraphQL + Hibernate Reactive + Spring
Re-produce the `java.lang.IllegalStateException: Session/EntityManager is closed` exception

## Problem Statement
Given a Parent entity which has a one-many relationship with a Child entity, 
when multiple Parents exist, each with a child, 
then when a GraphQL query is made to fetch all Parents with their Children using the `withSession` method
then a `java.lang.IllegalStateException: Session/EntityManager is closed` is thrown.

If the `withSession` is replaced by a `withTransaction` then the exception isn't thrown but this doesn't
solve the underlying issue.

## Env Setup
### Database
Running the commands in `src/main/resources/dbCreation.sh` will pull a Docker Image of MySQL 5.7 and start a container. The username/password for this container are the same as in the `application.yml`

### Creating the DB Schema
Use the SQL statements found in `src/main/resources/schemaCreation.sql` to create the schema and the tables.
This script also contains DML queries to create two Parent entities, each having a Child.
Note: I can only reproduce the exception when there are at least two Parents and each Parent has at least one Child.

### Server and API
If the instructions above were used then the server should be able to boot without any errors.
Execute `./gradlew :bootRun` boot the server.

To call the API import `src/main/resources/API.postman_collection.json` into Postman.
Execute the `withSession` request.
This request fetches all Parents and each their Children using GraphQL but fails to fetch the seconds Parent's Child.


Execute the `withTransaction` request.
This query fetches the Children entities in a transaction. This query doesn't throw an exception.

This a simple example to reproduce the exception but using `withTransaction` is not a solution because
I have a more complex data model in a private repo which throws other exceptions down the line if I try to fetch
more deeply nested entities.

#### Expected Behaviour
All Parents and their Children are fetched successfully.

### Actual Behavior
The first Parent and its Child is fetched successfully.
The second Parent is also fetched successfully but an exception is throw while the API tries to fetch the second Parent's child.
