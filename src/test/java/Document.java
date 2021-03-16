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
public class Document {
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
