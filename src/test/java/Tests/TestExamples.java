package Tests;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

public class TestExamples {

    private String base = "https://reqres.in/api";

    @Test

    public void TestGetStatusCodeEquals200() {

        Response response = get("https://reqres.in/api/users?page=2");
        int statusCode = response.getStatusCode();
        String statusLine = response.getStatusLine();
        String contentType = response.getContentType();
        String respBody = response.getBody().asPrettyString();
        System.out.println(respBody);
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
        Assert.assertEquals(contentType, "application/json; charset=utf-8");
    }

    @Test

    public void TestGET() {
        baseURI = base;
        given()
           .get("/users?page=2")
        .then()
           .statusCode(200).log().all();


    }

    @Test
    public void testPOST() {
        baseURI = base;

        JSONObject request = new JSONObject();
        request.put("name", "Morpheus");
        request.put("job", "leader");

        given().
                contentType(ContentType.JSON)
                .body(request.toJSONString()).
        when()
                .post("/users").
        then()
                .statusCode(201).log().all();
    }

    @Test
    public void testPUT() {
        baseURI = base;

        JSONObject request = new JSONObject();
        request.put("name", "Morpheus");
        request.put("job", "leader of Zion");

        given().
                contentType(ContentType.JSON)
                .body(request.toJSONString()).
                when()
                .put("/users/2").
                then()
                .statusCode(200).log().all();
    }

    @Test
    public void testPATCH() {
        baseURI = base;

        JSONObject request = new JSONObject();
        request.put("name", "THe Great Morpheus");
        request.put("job", "Supreme leader of Zion and defender of all Humanity");

        given().
                contentType(ContentType.JSON)
                .body(request.toJSONString()).
                when()
                .patch("/users/2").
                then()
                .statusCode(200).log().all();
    }

    @Test
    public void testDELETE() {
        baseURI = base;

        JSONObject request = new JSONObject();
        request.put("name", "The Great Morpheus");
        request.put("job", "Supreme leader of Zion and defender of all Humanity");

        given().
                contentType(ContentType.JSON)
                .body(request.toJSONString()).
                when()
                .delete("/users/2").
                then()
                .statusCode(204).log().all();
    }

}
