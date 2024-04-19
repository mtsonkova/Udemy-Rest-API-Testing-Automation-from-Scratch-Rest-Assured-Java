package Test;

import Files.payload;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class GoogleMapsAPI {

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
    }


    // given() has all input details
    // when() has the HTTP method and the resource / endpoint
    // given().log().all() -> lets us see how the parameters are send;
    //then();log().all() -< let us see the received response.
    // to extract the response of the server you use .extract().response().asString

    @Test
    public void testAddNewLocation() {
        String response = given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(payload.AddPlace())
                .when().post("maps/api/place/add/json")
                .then()
                .assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .body("status", equalTo("OK"))
                .header("Server", equalTo("Apache/2.4.52 (Ubuntu)"))
                .extract().response().asString();

        System.out.println(response);
    }



    //Add place -> Update place with address -> Get place to validate if new address is presence in response.
}
