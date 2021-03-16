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

public class Notification {
    private Cookies cookies;
    private ArrayList<String> idsForCleanUp = new ArrayList<>();;
    @BeforeClass
    public void setUp() {

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
}
