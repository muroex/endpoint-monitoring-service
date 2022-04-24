# endpoint-monitoring-service
# Endpoint Monitoring Service

This service can monitor endpoints added to the database at a certain time interval. 

## Getting started
Make sure to install [Docker](https://www.docker.com/) first! Then, run the following command:
```
git clone https://github.com/muroex/endpoint-monitoring-service.git
docker compose up --build
```

## Specifications
The service is written in Java 11 with Spring Boot framework and MySQL database.
## REST Endpoint list
The API runs on http://localhost:8080/. To use the service, you need to add a custom HTTP header: `accessToken`. Available UUIDs are:
|      <b>Header name</b>     | <b>Header value</b> |
|:-----------------------:|:------:|
|        `accessToken`       |  93f39e2f-80de-4033-99ee-249d92736a25  |
|     `accessToken`     |   dcb20f8a-5657-4f1b-9f7f-ce65739b359e  |

Available REST endpoints and methods are listed below:

|      <b>Address</b>     | <b>Method</b> |                                     <b>Description</b>                                                   |
|:-----------------------:|:------:|:---------------------------------------------------------------------------------------------------------------:|
|        `/api/v1/monitoredEndpoint`       |   GET  |                                     Gets the list of all recorded endpoints.                                    |
|     `/api/v1/monitoredEndpoint/{id}`     |   GET  |        Gets an endpoint by selected id. If the user token is wrong, the user won't get the endpoint data.       |
| `/api/v1/monitoredEndpoint/{id}/results` |   GET  | Gets 10 recent results for selected endpoint. If the user token is wrong, the user won't get the endpoint data. |
|     `/api/v1/monitoredEndpoint/{id}`     |   PUT  |            Updates selected endpoint. If the user token is wrong, the endpoint data won't be updated.           |
|        `/api/v1/monitoredEndpoint`       |  POST  |                                               Adds a new endpoint.                                              |
|     `/api/v1/monitoredEndpoint/{id}`     | DELETE |       Deletes selected endpoint by its id. If the user token is wrong, the endpoint data won't be deleted.      |
