# HealthcareInstitution

[![Build Status][ci-img]][ci]

[ci-img]:          https://travis-ci.org/Sorackb/HealthcareInstitution.svg
[ci]:              https://travis-ci.org/Sorackb/HealthcareInstitution


An API that will take control over the healthcare institution registration as well as the exams ingest.

## API documentation

A API description and try-out methods can be viewed in the following URL:

  /swagger-ui.html

**Create a Healthcare Institution**
---
  Creates a single Healthcare Institution.

* **URL**

  /healthcareinstitution/

* **Method:**

  `POST`

* **Data Params**

  **Required:**

  `Name=[alphanumeric]` <br />
  `CNPJ=[alphanumeric]`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{ "id": 4, "Name": "lucasbernardo.org", "CNPJ": "16191374000171" }`

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
    url: '/healthcareinstitution/',
    method: 'POST',
    timeout: 0,
    dataType: 'json',
    data: JSON.stringify({ Name: 'lucasbernardo.org', CNPJ: '16.191.374/0001-71' })
  }).done(function (response) {
    console.log(response);
  });
  ```

---
**Create a Exam**
---
  Creates a single Exam. This resource charges 1 pixeon.

* **URL**

  /healthcareinstitution/:healthcare_institution_id/exam

* **Method:**

  `POST`

*  **URL Params**

   **Required:**

   `healthcare_institution_id=[integer]`

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

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ "HealthcareInstitution": "id \"2\" not found." }`

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
    url: '/healthcareinstitution/1/exam/',
    method: 'POST',
    timeout: 0,
    dataType: 'json',
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

  /healthcareinstitution/:healthcare_institution_id/exam/:exam_id/

* **Method:**

  `PUT`

*  **URL Params**

   **Required:**

   `healthcare_institution_id=[integer]`<br />
   `exam_id=[integer]`

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

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ "HealthcareInstitution": "id \"2\" not found." }`

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
    url: '/healthcareinstitution/1/exam/6',
    method: 'PUT',
    timeout: 0,
    dataType: 'json',
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

  /healthcareinstitution/:healthcare_institution_id/exam/:exam_id/

* **Method:**

  `DELETE`

*  **URL Params**

   **Required:**

   `healthcare_institution_id=[integer]`<br />
   `exam_id=[integer]`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{ "deleted": true }`

* **Error Response:**

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ "HealthcareInstitution": "id \"2\" not found." }`

  OR

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ "Exam": "id \"2\" not found." }`

* **Sample Call:**

  ```javascript
  $.ajax({
    url: '/healthcareinstitution/1/exam/6',
    method: 'DELETE',
    timeout: 0,
  }).done(function (response) {
    console.log(response);
  });
  ```

---
**Retrieve an exam**
---
  Retrieve a previously created Exam. Charges 1 pixeon in the first call of every exam.

* **URL**

  /healthcareinstitution/:healthcare_institution_id/exam/:exam_id/

* **Method:**

  `GET`

*  **URL Params**

   **Required:**

   `healthcare_institution_id=[integer]`<br />
   `exam_id=[integer]`

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

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ "HealthcareInstitution": "id \"2\" not found." }`

  OR

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ "Exam": "id \"2\" not found." }`

  OR

  * **Code:** 500 INTERNAL SERVER ERROR <br />
    **Content:** `{ "HealthcareInstitution": "Out of budget." }`

* **Sample Call:**

  ```javascript
  $.ajax({
    url: '/healthcareinstitution/1/exam/6',
    method: 'GET',
    timeout: 0
  }).done(function (response) {
    console.log(response);
  });
  ```