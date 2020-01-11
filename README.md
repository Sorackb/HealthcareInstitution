# HealthcareInstitution

[![Build Status][ci-img]][ci]

[ci-img]:          https://travis-ci.org/Sorackb/HealthcareInstitution.svg
[ci]:              https://travis-ci.org/Sorackb/HealthcareInstitution

An API that will take control over the healthcare institution registration as well as the exams ingest.

## Requirements

  - Java SE Development Kit 8 or higher;
  - Maven 2.0.9 or higher;
  - MySQL 5.0 or higher (The configuration of the connection can be done by editing the `\main\resources\application.properties` file or with the `DATASOURCE_HOST`, `DATASOURCE_SCHEMA`, `DATASOURCE_USERNAME` and `DATASOURCE_PASSWORD` environment variables).

## Running

  ```bash
  mvn spring-boot:run
  ```

## API documentation

The API description and try-out methods can be viewed in the following URL:

  /swagger-ui.html

### healthcareinstitutions

  An API that will take control over the healthcare institution registration.

**Create a Healthcare Institution**
---
  Creates a single Healthcare Institution. The **token** returned needs to be used as Beared Authentication Header for **exams** API.

* **URL**

  /healthcareinstitutions/

* **Method:**

  `POST`

* **Data Params**

  **Required:**

  `Name=[alphanumeric]` <br />
  `CNPJ=[alphanumeric]`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{ "Name": "lucasbernardo.org", "CNPJ": "16191374000171", "token": "XXXXXX.XXXXXXXXXXXXX" }`

* **Error Response:**

  * **Code:** 500 INTERNAL SERVER ERROR <br />
    **Content:** `{ "cnpj": "Duplicate entry '16191374000171' for key 'CNPJ'" }`

  OR

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ "cnpj": "CNPJ is mandatory." }`

  OR

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ "cnpj": "Invalid CNPJ." }`

* **Sample Call:**

  ```javascript
  $.ajax({
    url: '/exams/',
    method: 'POST',
    timeout: 0,
    dataType: 'json',
    data: JSON.stringify({ Name: 'lucasbernardo.org', CNPJ: '16.191.374/0001-71' })
  }).done(function (response) {
    console.log(response);
  });
  ```

### exams

  An API that will take control over the exam ingest. The Beared Authentication Header is required and can be obtain creating a healthcare institution.

---
**Create a Exam**
---
  Creates a single Exam. This resource charges 1 pixeon.

* **URL**

  /exams/

* **Method:**

  `POST`

* **Data Params**

  **Required:**

  `PatientName=[alphanumeric]` <br />
  `PatientAge=[integer]` <br />
  `PatientAge=[enum(M or F)]` <br />
  `PhysicianName=[alphanumeric]` <br />
  `PhysicianCRM=[alphanumeric]` <br />
  `ProcedureName=[alphanumeric]`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:**
  ```json
  {
    "id": 6,
    "healthcareInstitution": {
      "Name": "lucasbernardo.org",
      "CNPJ": "16191374000171"
    },
    "PatientName": "John Doe",
    "PatientAge": 55,
    "PatientGender": "M",
    "PhysicianName": "Taylor Joe",
    "PhysicianCRM": "45465223",
    "ProcedureName": "MRI"
  }
  ```

* **Error Response:**

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ "error": "token \"XXXXXX.XXXXXXXXXXXXX\" not found." }`

  OR

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ "patientName": "PatientName is mandatory." }`

  OR

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ "patientGender": "PatientGender must be \"M\" or \"F\"." }`

  OR

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ "patientAge": "PatientAge should be a positive integer." }`

  OR

  * **Code:** 500 INTERNAL SERVER ERROR <br />
    **Content:** `{ "HealthcareInstitution": "Out of budget." }`

* **Sample Call:**

  ```javascript
  $.ajax({
    url: '/exams/',
    method: 'POST',
    timeout: 0,
    dataType: 'json',
	"headers": {
      "Content-Type": "application/json",
      "Authorization": "Bearer XXXXXX.XXXXXXXXXXXXX"
    },
    data: JSON.stringify({
      PatientName: 'John Doe',
      PatientAge: 55,
      PatientGender: 'M',
      PhysicianName: 'Taylor Joe',
      PhysicianCRM: '45465223',
      ProcedureName: 'MRI'
    })
  }).done(function (response) {
    console.log(response);
  });
  ```

---
**Update an exam**
---
  Update data of an existent Exam.

* **URL**

  /exams/:id/

* **Method:**

  `PUT`

*  **URL Params**

   **Required:**

   `id=[integer]`

* **Data Params**

  **Required:**

  `PatientName=[alphanumeric]` <br />
  `PatientAge=[integer]` <br />
  `PatientAge=[enum(M or F)]` <br />
  `PhysicianName=[alphanumeric]` <br />
  `PhysicianCRM=[alphanumeric]` <br />
  `ProcedureName=[alphanumeric]`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:**
  ```json
  {
    "id": 6,
    "healthcareInstitution": {
      "id": 1,
      "Name": "lucasbernardo.org",
      "CNPJ": "16191374000171"
    },
    "PatientName": "John Doe",
    "PatientAge": 55,
    "PatientGender": "M",
    "PhysicianName": "Taylor Joe",
    "PhysicianCRM": "45465223",
    "ProcedureName": "MRI"
  }
  ```

* **Error Response:**

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ "error": "token \"XXXXXX.XXXXXXXXXXXXX\" not found." }`

  OR

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ "Exam": "id \"2\" not found." }`

  OR

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ "patientName": "PatientName is mandatory." }`

  OR

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ "patientGender": "PatientGender must be \"M\" or \"F\"." }`

  OR

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ "patientAge": "PatientAge should be a positive integer." }`

* **Sample Call:**

  ```javascript
  $.ajax({
    url: '/exams/6',
    method: 'PUT',
    timeout: 0,
    dataType: 'json',
	"headers": {
      "Content-Type": "application/json",
      "Authorization": "Bearer XXXXXX.XXXXXXXXXXXXX"
    },
    data: JSON.stringify({
      PatientName: 'John Doe',
      PatientAge: 55,
      PatientGender: 'M',
      PhysicianName: 'Taylor Joe',
      PhysicianCRM: '45465223',
      ProcedureName: 'MRI'
    })
  }).done(function (response) {
    console.log(response);
  });
  ```

---
**Delete an exam**
---
  Delete an existent Exam.

* **URL**

  /exams/:id/

* **Method:**

  `DELETE`

*  **URL Params**

   **Required:**

   `id=[integer]`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{ "deleted": true }`

* **Error Response:**

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ "error": "token \"XXXXXX.XXXXXXXXXXXXX\" not found." }`

  OR

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ "Exam": "id \"2\" not found." }`

* **Sample Call:**

  ```javascript
  $.ajax({
    url: '/exams/6',
    method: 'DELETE',
    timeout: 0,
	"headers": {
      "Authorization": "Bearer XXXXXX.XXXXXXXXXXXXX"
    }
  }).done(function (response) {
    console.log(response);
  });
  ```

---
**Retrieve an exam**
---
  Retrieve a previously created Exam. Charges 1 pixeon in the first call of every exam.

* **URL**

  /exams/:id/

* **Method:**

  `GET`

*  **URL Params**

   **Required:**

   `id=[integer]`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:**
  ```json
  {
    "id": 6,
    "healthcareInstitution": {
      "id": 1,
      "Name": "lucasbernardo.org",
      "CNPJ": "16191374000171"
    },
    "PatientName": "John Doe",
    "PatientAge": 55,
    "PatientGender": "M",
    "PhysicianName": "Taylor Joe",
    "PhysicianCRM": "45465223",
    "ProcedureName": "MRI"
  }
  ```

* **Error Response:**

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ "error": "token \"XXXXXX.XXXXXXXXXXXXX\" not found." }`

  OR

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ "Exam": "id \"2\" not found." }`

  OR

  * **Code:** 500 INTERNAL SERVER ERROR <br />
    **Content:** `{ "HealthcareInstitution": "Out of budget." }`

* **Sample Call:**

  ```javascript
  $.ajax({
    url: '/exams/6',
    method: 'GET',
	"headers": {
      "Authorization": "Bearer XXXXXX.XXXXXXXXXXXXX"
    },
    timeout: 0
  }).done(function (response) {
    console.log(response);
  });
  ```