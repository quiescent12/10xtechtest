# 10x Tech Test

RESTful API to handle transaction requests.

## Set up
1. Run `docker compose up` to run set up mysql database
2. Exec into mysql pod `docker exec -it mysql bash`
3. Run `mysql transactions -u root -p` and typing in root password from docker compose file
4. Create database table by running query `CREATE TABLE accounts(id bigint unsigned primary key NOT NULL AUTO_INCREMENT, balance decimal(10, 2), currency varchar(10), created_at timestamp default current_timestamp);` 

## Endpoints
* POST http://localhost:8080/transactions/transfer
  * Used to request a transfer between two accounts 
  * Request body example:
      * {"sourceAccountId":1,"targetAccountId":2,"amount":10.0}
* POST http://localhost:8080/transactions/createAccount
  * Sets up an account to use
  * Request body:
    * {"balance":20.00,"currency":"GBP"}

## Potential add-ons
* Handling currency types
* Committing transactions completed as a value in the database
* Double amount validation. Use floats?
* Database schema management - flyway, percona?
* Metrics
* Handling multiple concurrency requests with same data, need to prevent them both executing and transferring twice the amount possible
* Database error handling - connection loss ect.