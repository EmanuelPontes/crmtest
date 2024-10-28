This repository is designed to meet the following requirements for a technical test for the Software Engineer position.

Summary: You are tasked with developing an integration solution for a retail company that needs to synchronize customer data between its internal e-commerce platform and a third-party CRM system. The company wants to build a microservice that exposes a REST API for customer data management and integrates with the external CRM to keep customer records in sync.

Requirements:

Create a Microservice:

Develop a Spring Boot-based microservice that exposes a REST API for basic customer management (Create, Read, Update, Delete - CRUD operations).
The internal Customer object in your API should have the following attributes:
customerId
firstName
lastName
email
phoneNumber
address (with subfields: street, city, state, zipCode)
Customer Object in CRM:

The Customer object in the external CRM differs from the internal API object. The CRM’s data model is structured as follows:
id (corresponds to customer ID)
fullName (corresponds to customer name)
contactEmail
primaryPhone
location (corresponds to address, but as a single concatenated string, e.g., "123 Main St, Springfield, IL, 62704")
Integration with Third-Party CRM:

Simulate integration with a third-party CRM system. For this, you can use a mock service (e.g., a JSON file, mock server, or in-memory database) to represent the CRM.
Implement the following functionality:
Whenever a customer is created or updated in your system, this change needs to be updated in the CRM.
There should be a mechanism to handle CRM downtime or unavailability.
Data Consistency and Retry Mechanism:

Ensure data consistency between your microservice and the CRM.
Error Handling & Logging:

Implement detailed error handling for various failure scenarios (e.g., CRM failures, validation errors, mapping issues).
Proposed Solution for Each Requirement:

Microservice Design:

I implemented a set of Java abstract classes I commonly use in other projects to handle CRUD operations in both controller and service layers. These can be found in the com.emanuelpontes.crmtest.global.controller and com.emanuelpontes.crmtest.global.service packages.
CRM Mock Service:

I created a Node.js backend using SQLite as the database to act as the CRM mock service, which is located in the ./crm folder.
Integration Solution:

To achieve this, I developed two sets of solutions. The first uses Spring Events asynchronously to send Customer entries to the CRM. Additionally, I created a WebFlux-based service to manage data transfers and retries between this microservice and the mock CRM. These implementations are in the com.emanuelpontes.crmtest.api.integrations.crm and com.emanuelpontes.crmtest.api.customer.event packages.

Data Consistency:

To maintain data consistency, I used a CRON-based service. In Spring Boot, this is implemented using the @Scheduled annotation, which is attached to the CustomerCrmService.syncData method. The method uses the updatedAt field from the database to identify recently updated customers and trigger the sync process.
Error Handling and Logging:

I created a set of error exceptions and handlers in the Global package for handling errors. For WebFlux, I implemented a configuration in com.emanuelpontes.crmtest.config.WebClientConfig to log requests, responses, errors, and retry policies.


SWAGGER URL FOR THE Integration API

http://localhost:8080/swagger-ui/index.html

How to run the project:

First crm-test: 
    Create the docker image using the following command:
        docker build -t crm-test .
    Run the container with:
        docker run -p 8080:8080 crm-test 
    Use the swagger-ui for calling the apis and create a customer:
        http://localhost:8080/swagger-ui/index.html

Second Mock CRM API
    Go to the ./crm folder and run the following:

    Create the docker image using the following command:
        docker build -t crm-api .
    Run the container with:
        docker run -p 3000:3000 crm-api 
P.S: The application.properties, property crm.integration.base.url must have value http://host.docker.internal:3000/
