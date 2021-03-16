

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsString;

    public class test {
        private Cookies cookies;
        private ArrayList<String> idsForCleanUp = new ArrayList<>();;

        /*
        For this project you need to use Rest Assured Libraries
        Authentication Info:
        Base URI:  https://test.campus.techno.study
        Username:  daulet2030@gmail.com
        Password:  TechnoStudy123@
        */
        @BeforeClass
        public void setUp() throws Exception {

            RestAssured.baseURI = "https://test.campus.techno.study";

            Map<String, String> credentials = new HashMap<>();
            credentials.put("username", "daulet2030@gmail.com");
            credentials.put("password", "TechnoStudy123@");
            ValidatableResponse response = given()
                    .body(credentials)
                    .contentType(ContentType.JSON)
                    .when()
                    .post(baseURI.concat("/auth/login"))
                    .then();

            response.statusCode(200);

            cookies = response.extract().detailedCookies();
        }
    /*--- PART 1 ---
Create - Edit - Delete Discount
Api Path: "/school-service/api/discounts"
Create Discount    --->   Status Code should be "201"
Create Discount Negative Test   ---->   Status Code should be "400"
Edit Created Discount (Edit description)    --->   Status Code should be "200"
Delete Created Discount    --->   Status Code should be "200"
Delete Created Discount Negative Test    --->   Status Code should be "404"
Inside body, you need to provide:
code: (Code is up to you)
description: ( Description is up to you)
priority: ( Priority should be number )
active: (true)*/

        @Test
        public void CreateDiscount(){


            HashMap<String,String> body = new HashMap<>();
            body.put("code","C1");
            body.put("description","Create1");
            body.put("priority","1");
            body.put("active", "true");
            given().when().then();

            ValidatableResponse response = given()
                    .cookies(cookies)
                    .body(body)
                    .contentType(ContentType.JSON)
                    .when()
                    .post("/school-service/api/discounts")
                    .then();

            String id = response.statusCode(201).extract().jsonPath().getString("id");
            idsForCleanUp.add(id);
        }

        @Test
        public void DuplicateDiscount(){

            HashMap<String, String> duplicateBody = new HashMap<>();
            duplicateBody.put("code","C1");
            duplicateBody.put("description","Create1");
            duplicateBody.put("priority","1");
            duplicateBody.put("active", "true");

            given()
                    .cookies(cookies)
                    .body(duplicateBody)
                    .contentType(ContentType.JSON)
                    .when()
                    .post("/school-service/api/discounts")
                    .then()
                    .statusCode(400)
                    .body("message", allOf(
                            containsString(duplicateBody.get("name")),
                            containsString("already exists"))
                    );
        }

        @Test
        public void EditDiscount(){
            HashMap<String,String> editedBody = new HashMap<>();
            editedBody.put("id",idsForCleanUp.get(0));
            editedBody.put("description","editedDiscount1");


            given()
                    .cookies (cookies)
                    .body(editedBody)
                    .contentType(ContentType.JSON)
                    .when()
                    .put("/school-service/api/discounts")
                    .then()
                    .statusCode(200)
                    .body("description",equalTo(editedBody.get("description")));

        }

        @Test(dependsOnMethods = "CreateDiscount")
        public void DeleteDiscount(){

            given()
                    .cookies(cookies)
                    .when()
                    .delete("/school-service/api/discounts" +idsForCleanUp.get(0))
                    .then()
                    .statusCode(200);
            idsForCleanUp.remove(0);
        }

        @Test
        public void DeleteDiscountAgain(){

            given()
                    .cookies(cookies)
                    .when()
                    .delete("/school-service/api/discounts" +idsForCleanUp.get(0))
                    .then()
                    .statusCode(404);
        }
    /*--- PART 2 ---
Create - Edit - Delete Notification
Api Path: "/school-service/api/notifications"
Create Notification**    --->   Status Code should be "201"
Create Notification Negative Test   ---->   Status Code should be "400"
Edit Created Notification (Edit name and short name)    --->   Status Code should be "200"
Delete Created Notification    --->   Status Code should be "200"
Delete Created Notification Negative Test    --->   Status Code should be "404"
**Inside body, you need to provide:
name: (Name is up to you)
description: ( description is up to you)
type: ( "STUDENT_PAYMENT_TIME" )
schoolId: (" 5c5aa8551ad17423a4f6ef1d ")
*/

        @Test
        public void CreateNotification(){

            HashMap<String, String> notificationBody = new HashMap<>();
            notificationBody.put("name","N1");
            notificationBody.put("description","Notification1");
            notificationBody.put("type","STUDENT_PAYMENT_TIME");
            notificationBody.put("schoolId","5c5aa8551ad17423a4f6ef1d");

            ValidatableResponse response = given()
                    .cookies(cookies)
                    .body(notificationBody)
                    .contentType(ContentType.JSON)
                    .when()
                    .post("/school-service/api/notifications")
                    .then();

            String id = response.statusCode(201).extract().jsonPath().getString("id");
            idsForCleanUp.add(id);

        }

        @Test(dependsOnMethods = "CreateNotification")
        public void DuplicateNotification(){

            HashMap<String, String> duplicateBody = new HashMap<>();
            duplicateBody.put("name","N1");
            duplicateBody.put("description","Notification1");
            duplicateBody.put("type","STUDENT_PAYMENT_TIME");
            duplicateBody.put("schoolId","5c5aa8551ad17423a4f6ef1d");

            given()
                    .cookies(cookies)
                    .body(duplicateBody)
                    .contentType(ContentType.JSON)
                    .when()
                    .post("/school-service/api/notifications")
                    .then()
                    .statusCode(400)
                    .body("message", allOf(
                            containsString(duplicateBody.get("name")),
                            containsString("already exists")));

        }

        @Test
        public void EditNotification(){
            HashMap<String, String> editNotificationBody = new HashMap<>();
            editNotificationBody.put("id",idsForCleanUp.get(0));
            editNotificationBody.put("name", "editedN1");

            given()
                    .cookies (cookies)
                    .body(editNotificationBody)
                    .contentType(ContentType.JSON)
                    .when()
                    .put("/school-service/api/notifications")
                    .then()
                    .statusCode(200)
                    .body("name",equalTo(editNotificationBody.get("name")));



        }

        @Test
        public void DeleteNotification(){
            given()
                    .cookies(cookies)
                    .when()
                    .delete("/school-service/api/notifications" +idsForCleanUp.get(0))
                    .then()
                    .statusCode(200);
            idsForCleanUp.remove(0);
        }

        @Test
        public void DeleteNotificationAgain(){
            given()
                    .cookies(cookies)
                    .when()
                    .delete("/school-service/api/notifications" +idsForCleanUp.get(0))
                    .then()
                    .statusCode(404);
            idsForCleanUp.remove(0);
        }
        /*
        --- Part 3 ---
        Create - Edit - Delete Document Type
        Api Path: "/school-service/api/attachments"
        Create Document Type**    --->   Status Code should be "201"
        Create Document Type Negative Test   ---->   Status Code should be "400"
        Edit Created Document Type (Edit name and Description)    --->   Status Code should be "200"
        Delete Created Discount    --->   Status Code should be "200"
        Delete Created Discount Negative Test    --->   Status Code should be "404"
        **Inside body, you need to provide:
        name: (Name is up to you)
        description: ( Description is up to you)
        schoolId: ( "5c5aa8551ad17423a4f6ef1d" )
        active: ( true )
        required: ( true )
        attachmentStages: ( "EXAMINATION" , "EMPLOYMENT" , "CERTIFICATE" )
         */
        @Test
        public void CreateDocumentType(){
            HashMap<String, String> documentBody = new HashMap<>();
            documentBody.put("name","DT1");
            documentBody.put("description","DocumentType1");
            documentBody.put("schoolId","5c5aa8551ad17423a4f6ef1d");
            documentBody.put("active", "true");
            documentBody.put("required","true");
            documentBody.put("attachmentStages", "\"EXAMINATION\" , \"EMPLOYMENT\" , \"CERTIFICATE\"");

            ValidatableResponse response = given()
                    .cookies(cookies)
                    .body(documentBody)
                    .contentType(ContentType.JSON)
                    .when()
                    .post("/school-service/api/attachments")
                    .then();

            String id = response.statusCode(201).extract().jsonPath().getString("id");
            idsForCleanUp.add(id);
        }

        @Test
        public void DuplicateDocumentType(){

            HashMap<String, String> duplicateDocumentBody = new HashMap<>();
            duplicateDocumentBody.put("name","D1");
            duplicateDocumentBody.put("description","Description1");
            duplicateDocumentBody.put("schoolId","5c5aa8551ad17423a4f6ef1d");
            duplicateDocumentBody.put("active", "true");
            duplicateDocumentBody.put("required","true");
            duplicateDocumentBody.put("attachmentStages", "\"EXAMINATION\" , \"EMPLOYMENT\" , \"CERTIFICATE\"");

            ValidatableResponse response = given()
                    .cookies(cookies)
                    .body(duplicateDocumentBody)
                    .contentType(ContentType.JSON)
                    .when()
                    .post("/school-service/api/attachments")
                    .then()
                    .body("message", allOf(
                            containsString(duplicateDocumentBody.get("name")),
                            containsString("already exists")));
        }

        @Test
        public void EditDocumentType(){
            HashMap<String,String> editDocumentBody = new HashMap<>();
            editDocumentBody.put("id",idsForCleanUp.get(0));
            editDocumentBody.put("name","editedDT1");
            editDocumentBody.put("description","editedDocumentType1");

            given()
                    .cookies (cookies)
                    .body(editDocumentBody)
                    .contentType(ContentType.JSON)
                    .when()
                    .put("/school-service/api/attachments")
                    .then()
                    .statusCode(200)
                    .body("name",equalTo(editDocumentBody.get("name")))
                    .body("description",equalTo(editDocumentBody.get("description")));


        }

        @Test
        public void DeleteDocumentType(){
            given()
                    .cookies(cookies)
                    .when()
                    .delete("/school-service/api/attachments" +idsForCleanUp.get(0))
                    .then()
                    .statusCode(200);
            idsForCleanUp.remove(0);
        }

        @Test
        public void DeleteDocumentTypeAgain(){
            given()
                    .cookies(cookies)
                    .when()
                    .delete("/school-service/api/attachments" +idsForCleanUp.get(0))
                    .then()
                    .statusCode(404);
            idsForCleanUp.remove(0);
        }


}
