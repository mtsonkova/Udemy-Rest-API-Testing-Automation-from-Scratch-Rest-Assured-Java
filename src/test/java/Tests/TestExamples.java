package Tests;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestExamples {
    @Test

    public void TestGetStatusCodeEquals200() {

        Response response = get("https://reqres.in/api/users?page=2");
        int statusCode =  response.getStatusCode();
        String statusLine = response.getStatusLine();
        String contentType = response.getContentType();
        String respBody = response.getBody().asPrettyString();
        System.out.println(respBody);
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
        Assert.assertEquals(contentType, "application/json; charset=utf-8");
    }

    @Test

    public void Test2() {
        baseURI = "https://reqres.in/api";
        given().get("/users?page=2")
                .then().statusCode(200)
                .body("data.id[1]", equalTo(1));

    }

    @Test
    public void testGET() {

    }

}
