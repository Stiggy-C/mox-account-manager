# Mox Account Manager
### 2021-09-20

## APIs

#### Create
##### http://localhost:8080/rs/accounts

HTTP Method: POST
Content-Type: application/json   
Success status: 201
Sample request:
```json
{
    "createdBy": "SYSTEM",
    "balance": 0.0,
    "number": 23456789
}
```

#### Find by #
##### http://localhost:8080/rs/accounts/number/${{accountNumber}}

HTTP Method: GET   
Content-Type: application/json   
Success status: 200
Sample response:
```json
{
    "id": "6aaee6ce-7de9-4191-86f1-9e36a3977db4",
    "createdBy": "SYSTEM",
    "createdDateTime": "2021-09-20T11:13:14.57478+08:00",
    "updatedDateTime": "2021-09-20T12:03:56.921693+08:00",
    "balance": 10.0,
    "number": 23456789
}
```

#### Patch by id
##### http://localhost:8080/rs/accounts/number/${{id}}

HTTP Method: PATCH   
Content-Type: application/merge-patch+json   
Success status: 204

#### Transfer funds
##### http://localhost:8080/rs/accounts/from/${{fromAccountNumber}}/to/${{toAccountNumber}}/amount/${{amount}}

HTTP Method: POST   
Success status: 204