# Customer Data Sync Microservice - Technical Test for Software Engineer Position

This repository is designed to meet the requirements of a technical test for the Software Engineer position.

## Summary
You are tasked with developing an integration solution for a retail company that needs to synchronize customer data between its internal e-commerce platform and a third-party CRM system. The company wants to build a microservice that exposes a REST API for customer data management and integrates with the external CRM to keep customer records in sync.

## Requirements

### 1. Create a Microservice
- Develop a Spring Boot-based microservice that exposes a REST API for basic customer management (Create, Read, Update, Delete - CRUD operations).
- The internal `Customer` object in your API should have the following attributes:
  - `customerId`
  - `firstName`
  - `lastName`
  - `email`
  - `phoneNumber`
  - `address` (with subfields: street, city, state, zipCode)

### 2. Customer Object in CRM
- The `Customer` object in the external CRM differs from the internal API object. The CRM’s data model is structured as follows:
  - `id` (corresponds to customer ID)
  - `fullName` (corresponds to customer name)
  - `contactEmail`
  - `primaryPhone`
  - `location` (a single concatenated address string, e.g., "123 Main St, Springfield, IL, 62704")

### 3. Integration with Third-Party CRM
- Simulate integration with a third-party CRM system, using a mock service (e.g., JSON file, mock server, or in-memory database) to represent the CRM.
- Implement the following functionality:
  - Update the CRM whenever a customer is created or updated in your system.
  - Implement a mechanism to handle CRM downtime or unavailability.

### 4. Data Consistency and Retry Mechanism
- Ensure data consistency between your microservice and the CRM.

### 5. Error Handling & Logging
- Implement detailed error handling for various failure scenarios (e.g., CRM failures, validation errors, mapping issues).

## Proposed Solution for Each Requirement

### Microservice Design
I implemented a set of Java abstract classes commonly used in my projects to handle CRUD operations across both controller and service layers. These can be found in the `com.emanuelpontes.crmtest.global.controller` and `com.emanuelpontes.crmtest.global.service` packages.

### CRM Mock Service
I created a Node.js backend using SQLite as the database to act as the CRM mock service, which is located in the `./crm` folder.

### Integration Solution
To achieve the required integration:
1. **Spring Events**: Used asynchronously to send Customer entries to the CRM.
2. **WebFlux Service**: A WebFlux-based service to manage data transfers and retries between this microservice and the mock CRM.

These implementations are in the `com.emanuelpontes.crmtest.api.integrations.crm` and `com.emanuelpontes.crmtest.api.customer.event` packages.

### Data Consistency
To maintain data consistency, I used a CRON-based service. In Spring Boot, this is implemented using the `@Scheduled` annotation, attached to the `CustomerCrmService.syncData` method. The method uses the `updatedAt` field from the database to identify recently updated customers and initiate the sync process.

### Error Handling and Logging
I created a set of error exceptions and handlers in the Global package for handling errors. For WebFlux, I implemented a configuration in `com.emanuelpontes.crmtest.config.WebClientConfig` to log requests, responses, errors, and retry policies.

## Running the Project

### First: CRM Test Microservice

1. **Build the Docker Image**:
   ```bash
    docker build -t crm-test .
    docker run -p 8080:8080 crm-test
2. **CRUD Customer service**:
    http://localhost:8080/swagger-ui/index.html
    
### Second: Mock CRM Test API
1. **Build the Docker Image**:
    ```bash
    docker build -t crm-api .
    docker run -p 3000:3000 crm-api
2. **Check Customer mock service**:
    http://localhost:3000/crm/customer/one-by-id/:id
## API Documentation

    You can access the Swagger UI for the integration API at the following URL:

    [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
