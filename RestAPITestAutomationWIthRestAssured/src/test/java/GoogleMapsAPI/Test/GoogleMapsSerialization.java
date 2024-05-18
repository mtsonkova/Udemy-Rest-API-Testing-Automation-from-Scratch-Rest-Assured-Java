package GoogleMapsAPI.Test;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class GoogleMapsSerialization {
    @BeforeMethod
    public void setUp() {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
    }

    @Test
    public void addNewPlace() {
        String addPlaceResponse = given().queryParam("key", "qaclick123")
                .body("arg")
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response().asPrettyString();
    }
}
