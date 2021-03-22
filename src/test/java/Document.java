import PoJoModel.Document.DocumentPoJo;
import PoJoModel.Notification.NotificationPoJo;
import Utils.ReusableMethods;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class Document extends Hooks {

    DocumentPoJo documentPoJo = new DocumentPoJo();
    ReusableMethods rm = new ReusableMethods();


    /*  --- Part 3 ---
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
    public HashMap<String, String> initMap() {
        HashMap<String, String> body = new HashMap<>();
        body.put("id", null);
        body.put("name", rm.randomString(4));
        body.put("description", rm.randomString(5));
        body.put("active", "true");
        body.put("attachmentStages", "EXAMINATION");

        return body;
    }

    private ResponseSpecification responseSpecification(int statusCode) {
        return responseSpecification = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(statusCode)
                .build();
    }


    @Test
    public void CreateDocumentType() {
        HashMap<String, String> body = initMap();

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(body)
                .log().body()
                .when()
                .post("/school-service/api/attachments")
                .then()
                .contentType(ContentType.JSON)
                .log().body()
                .statusCode(201)
                .body(not(empty()))
                .body("name",equalTo(body.get("name")))
                .body("description",equalTo(body.get("description")))
                .body("active",equalTo(body.get("active")))
                .body("attachmentStages",equalTo(body.get("attachmentStages")))
                .extract().as(DocumentPoJo.class);
    }

    @Test
    public void DuplicateDocumentType() {
        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .when()
                .then();
    }

    @Test
    public void EditDocumentType() {
        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .when()
                .then();

    }

    @Test
    public void DeleteDocumentType() {
        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .when()
                .then();
    }

    @Test
    public void DeleteDocumentTypeAgain() {
        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .when()
                .then();
    }


}
