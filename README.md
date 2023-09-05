# craft_demo
The purpose of this demo is to create a springboot java client and a c++ grpc server to set the info of a person

## Run Locally


Installing gRPC for mac
```bash
brew install grpc
```

Starting the Server
```bash
  $ cd person_server/cmake/build
  $ cmake ../..
  $ make -j 4
  $ ./person_server
```

Starting Client

```bash
  $ cd person_client
  $ ./mvnw spring-boot:run
```

Send a request to:
```bash 
POST: localhost:8080/api/person/
{
    "firstName": "Ahmad",
    "lastName": "Chaudry",
    "dob": "01/25/1997",
    "email": "email@email.com",
    "phoneNumber": "555-555-5555"
}
```

