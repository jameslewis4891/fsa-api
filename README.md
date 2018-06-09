# fsa-api

This application communicates with the food standards agency

# Building the program

To build the program run the following command from the root directory

`mvn clean install`

This will run all unit and integration tests

# Running the program

From the command prompt in the root directory run 

`mvn spring-boot:run`

This should bring the application up ready to serve requests 

NB the application requires port 8080 to be free to start

# API

There are two endpoints to this api

## `/authorities`

This endpoint provides a subset of all authorities from the food standards agency API

## `/establishments/ratings/byAuthority/{authorityId}`

This API will provide a list of food hygiene ratings and a percentage for each
rating for the provided authority id.

If the passed in authority id cannot be found the caller will be returned a 400 error 
with an appropriate response