package Tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class TestExamples {
    @Test

    public void Test_One() {
        Response response = RestAssured.get("https://reqres.in/api/users?page=2");
    }


}
