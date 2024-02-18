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
           .statusCode(200)
           .body("data.id[1]", equalTo(1))
           .body("data[4].first_name", equalTo("George"))
           .body("data.first_name", hasItems("Rachel", "Michael", "Lindsay"));

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
                .statusCode(201);
    }

}
