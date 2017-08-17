# Teacher

## getTeacherById

**Request**

* Requirment : None
* Http Method : GET
* URI : api/teacher/{teacherId}

**Response**

* Status : 200 OK
* Body :
```
{
  "firstName":"",
  "lastName":"",
  "middleName":"",
  "email":""
}
```

**Example**

* URL : api/teacher/5
* Body :
```
{
  "id": 5
  "firstName": "Rafael",
  "lastName" : "Manuel",
  "middleName": "Estrada",
  "email": "erafaelmanuel@gmail.com"
}
```

## getTeacherList

**Request**

* Requirment : None
* Http Method : GET
* URI : api/teacher

**Response**

* Status : 200 OK
* Body :
```
[
  {
    "firstName":"",
    "lastName":"",
    "middleName":"",
    "email":""
  },
  ...
]
```

**Example**

* URL : api/teacher
* Body :
```
[
  {
    "id": 1
    "firstName": "Verlie",
    "lastName" : "Manuel",
    "middleName": "Estrada",
    "email": "verliemanuel@gmail.com"
  },
  ...
]
```

## addTeacher

**Request**

* Requirment : None
* Http Method : POST
* URI : api/teacher
* Fields : 
```
  departmentId=[department_id]
```
* Body : 
```
  {
    "firstName" : "",
    "lastName" : "",
    ...
  }
```
**Response**

* Status : 201 OK
* Body :
```
  {
    "firstName":"",
    "lastName":"",
    "middleName":"",
    "email":""
  }
```

**Example**

* URL : api/teacher?departmentId=2
* Body :
```
  {
    "id": 1
    "firstName": "Verlie",
    "lastName" : "Manuel",
    "middleName": "Estrada",
    "email": "verliemanuel@gmail.com",
    "department" : {
      "id":2,
      "description":"Sample description",
      "name":"College of Computer Studies"
    }
  }
```
