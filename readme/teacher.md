# Teacher

### getTeacherById

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

URL : api/teacher/5
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
