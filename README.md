# Cadana Backend Engineer : Coding Exercise

## Task 1: Currency Exchange Rate API

### Objective
Develop a REST API that receives a JSON request with a currency pair (e.g., `{ "currency-pair": "USD-EUR" }`) and retrieves the exchange rate for this pair.

### Workflow
1. Concurrently request exchange rates from two distinct external services (Service A, Service B). Your API would call both of these services internally in the workflow.
2. Return the first response and disregard the second.
3. Return responses in the format: `{ "currency-pair": rate }`, e.g., `{ "USD-EUR": 0.92 }`.

### Assumptions/Constraints
- Use REST API for communication with external services.
- Simulate/Mock these external services within your solution.
- Do not store but fetch and return exchange rates in real-time.
- Each external service requires an API key. Implement a secure method for key management. You can assume AWS (or any other cloud provider) services access.
- Include tests demonstrating your code's functionality.

### Environment Setup
- Use java as the programming language.
- Local development environment setup is at your discretion.
- Any IDE or code editor can be used.


### API Key Management
- Utilize best practices for secure API key storage and retrieval with AWS (Cloud) services. Use mocks where applicable to simluate fetching the keys.


### Testing Requirements
- Include both unit and integration tests.
- Use testing frameworks compatible with Java.

### Documentation
- Document your solution with a README file and inline comments where necessary.

### Performance Considerations
- Aim for efficient and responsive API handling.

---

## Additional Task: Data Manipulation and Interfaces in Java

### Objective
Write a Java program that demonstrates data manipulation and interface usage, focusing on object-oriented practices.

### Tasks
1. **Unmarshal and Object Creation:**
    - Unmarshal a JSON file with 10 records in the format: `{ "id": "X", "personName": "Cadanaut X", "salary": { "value": "10", "currency": "USD" } }` into a struct called `Persons` for the array.

   ``` 
      types Persons struct {
         Data []Person `json:"data"`
      }
   ```

    - Design a `Person` object with appropriate fields and methods to encapsulate each row.

2. **Methods for Data Operations:**
    - Attach methods to the `Persons` struct to perform the following operations:
        - Sort the data array of `Person` objects by salary in ascending and descending order.
        - Group `Person` objects by salary currency into hash maps.
        - Filter `Person` objects by salary criteria in USD. Inject your API logic above as a dependecy to obtain the exchange rates to be able to filter all the different currencies in USD.

## Project Description
This is a springboot 3 web application service running on Java 17 to implement the above tasks.
It uses gradle as the dependency manager and github for version control. When the application is started, it runs on port 8080
and exposes one endpoint `/api/v1/rates/` for getting the exchange rates.
    
## Testing
To run the unit and integrations tests of the project, run the following command in the project root directory

    `./gradlew test`

## Build
To build the project, run the following command in the root directory

    `./gradlew clean build`

## Run
- To run the application, execute the command below in the root directory

    `./gradlew bootrun`
- Send a post request to the endpoint `localhost:8080/api/v1/rates/` passing the body below
  `{"currency-pair": "EUR-USD"}`
- Optionally, play with different currency pairs to see the different rates.
- Enter control + c to stop the service when desired. 


