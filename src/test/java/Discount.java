import PoJoModel.Discount.DiscountPoJo;
import PoJoModel.Discount.TranslateDescription;
import Utils.ReusableMethods;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class Discount extends Hooks{
    DiscountPoJo discountPoJo = new DiscountPoJo();
    TranslateDescription translateDescription = new TranslateDescription();
    ReusableMethods rm = new ReusableMethods();

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

        HashMap<String, String> body = initMap();

        given()
                .cookies(cookies)
                .body(body)
                .contentType(ContentType.JSON)
                .when()
                .post("/school-service/api/discounts")
                .then()
                .log().body()
                .spec(responseSpecification(201))
                .body(not(empty()))
                .body("description", equalTo(body.get("description")))
                .body("code", equalTo(body.get("code")))
                .body("priority", equalTo(Integer.parseInt(body.get("priority"))))
                .extract().as(DiscountPoJo.class);
    }

    @Test (dependsOnMethods = "CreateDiscount")
    public void CreateDiscountNegative(){

        HashMap<String, String> duplicateBody = new HashMap<>();
        duplicateBody.put("code",discountPoJo.getCode());
        duplicateBody.put("description",discountPoJo.getDescription());
        duplicateBody.put("priority",String.valueOf(discountPoJo.getPriority()));
        duplicateBody.put("active", discountPoJo.getActive());

        ExtractableResponse<Response> extract = given()
                .cookies(cookies)
                .spec(requestSpecification)
                .body(duplicateBody)
                .when()
                .post("/school-service/api/discounts")
                .then()
                .log().body()
                .spec(responseSpecification(400))
                .extract();

        String actualMessage = extract.jsonPath().getString("message");
        Assert.assertTrue(actualMessage.contains(discountPoJo.getDescription()));
    }

    @Test(dependsOnMethods = "CreateDiscountNegative" )
    public void EditDiscount(){
        HashMap<String, String> body = initMap();
        body.put("id", discountPoJo.getId());


        given()
                .cookies (cookies)
                .spec(requestSpecification)
                .body(body)
                .when()
                .put("/school-service/api/discounts")
                .then()
                .log().body()
                .spec(responseSpecification(200))
                .body(not(empty()))
                .body("description", equalTo(body.get("description")))
                .body("code", equalTo(body.get("code")))
                .body("priority", equalTo(Integer.parseInt(body.get("priority"))));

    }

    @Test(dependsOnMethods = "EditDiscount")
    public void DeleteDiscount(){
       given()
                .cookies(cookies)
                .spec(requestSpecification)
                .pathParam("discountId", discountPoJo.getId())
                .when()
                .delete("/school-service/api/discounts/{discountId}")
                .then()
                .log().body()
                .statusCode(200);

    }

    @Test (dependsOnMethods = "DeleteDiscount" )
    public void DeleteDiscountAgain(){

        given()
                .cookies(cookies)
                .spec(requestSpecification)
                .pathParam("discountId", discountPoJo.getId())
                .when()
                .delete("/school-service/api/discounts/{discountId}")
                .then()
                .log().body()
                .statusCode(404);
    }


    public ResponseSpecification responseSpecification (int statusCode) {
        ResponseSpecification responseSpecification;
        return responseSpecification = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(statusCode)
                .build();
    }

    public HashMap<String, String> initMap(){
        HashMap<String,String> body = new HashMap<>();
        body.put("code",rm.randomString(2)+rm.randomInt(2));
        body.put("description",rm.randomString(15));
        body.put("priority",String.valueOf(rm.randomInt(1)));
        body.put("active", rm.randomTrueOrFalse());
        return body;
    }
}
