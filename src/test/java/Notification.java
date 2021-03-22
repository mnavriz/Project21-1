import PoJoModel.Notification.NotificationPoJo;
import Utils.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsString;

public class Notification extends Hooks{
    NotificationPoJo notificationPoJo = new NotificationPoJo();
    ReusableMethods rm = new ReusableMethods();


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
    public HashMap <String, String> initMap(){
        HashMap<String, String> body = new HashMap<>();
       // body.put("id", String.valueOf(rm.randomInt(2)));
        body.put("name", rm.randomString(2)+rm.randomInt(3));
        body.put("description", rm.randomString(5));
        body.put("type", "STUDENT_PAYMENT_TIME");
        body.put("schoolId", "5c5aa8551ad17423a4f6ef1d");

        return body;
    }
    private ResponseSpecification responseSpecification(int statusCode) {
        return responseSpecification = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(statusCode)
                .build();
    }

    @Test
    public void CreateNotification(){

        HashMap<String, String> body = initMap();

        given()
                .cookies(cookies)
                .body(body)
                .contentType(ContentType.JSON)
                .when()
                .post("/school-service/api/notifications")
                .then()
                .log().body()
                //.spec(responseSpecification(201))
                .contentType(ContentType.JSON)
                .statusCode(201)
                .body(not(empty()))
                .body("name",equalTo(body.get("name")))
                .body("description",equalTo(body.get("description")))
                .body("type",equalTo(body.get("type")))
                .body("schoolId",equalTo(body.get("schoolId")))
                .extract().as(NotificationPoJo.class);

    }


    @Test(dependsOnMethods = "CreateNotification")
    public void DuplicateNotification(){

        HashMap<String, String> duplicateBody = new HashMap<>();

        duplicateBody.put("id", null);
        duplicateBody.put("name",notificationPoJo.getName());
       // duplicateBody.put("description",notificationPoJo.getDescription());
        duplicateBody.put("type",notificationPoJo.getType());
        duplicateBody.put("schoolId",notificationPoJo.getSchoolId());

        System.out.println(duplicateBody);

         given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(duplicateBody)
                .when()
                .post("/school-service/api/notifications")
                .then()
                .contentType(ContentType.JSON)
                 .log().body()
                 .statusCode(400)
                .body("message", allOf(
                        containsString(duplicateBody.get("name")),
                        containsString("already exists")));

    }

    @Test(dependsOnMethods = "CreateNotification")
    public void EditNotification(){
        HashMap<String, String> body = initMap();

       body.put("id",notificationPoJo.getId());


        given()
                .cookies (cookies)
                .contentType(ContentType.JSON)
                //.spec(requestSpecification)
                .body(body)
                .when()
                .put("/school-service/api/notifications")
                .then()
                .log().body()
                .contentType(ContentType.JSON)
                .statusCode(200)
                //.spec(responseSpecification(200))
                .body("name",equalTo(body.get("name")));

    }

    @Test(dependsOnMethods = "EditNotification")
    public void DeleteNotification(){
        given()
                .cookies(cookies)
                .spec(requestSpecification)
                .pathParam("id",notificationPoJo.getId())
                .when()
                .delete("/school-service/api/notifications/{id}")
                .then()
                .log().body()
                .statusCode(200);

    }

    @Test(dependsOnMethods = "DeleteNotification")
    public void DeleteNotificationAgain(){
        given()
                .cookies(cookies)
                .spec(requestSpecification)
                .pathParam("id",notificationPoJo.getId())
                .when()
                .delete("/school-service/api/notifications/{id}")
                .then()
                .log().body()
                .statusCode(404);
    }

}
