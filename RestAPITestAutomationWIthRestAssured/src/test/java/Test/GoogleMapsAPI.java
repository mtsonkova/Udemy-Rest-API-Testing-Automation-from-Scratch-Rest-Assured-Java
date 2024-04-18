package Test;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;


public class GoogleMapsAPI {

    @BeforeMethod(alwaysRun = true)
   public void setUp() {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
   }

   // given() has all input details
    // when() has the HTTP method and the resource / endpoint
    // given().log().all() -> lets us see how the parameters are send;
    //then();log().all() -< let us see the received response.

   @Test
    public void testAddNewLocation() {
    given().log().all().queryParam("key", "qaclick123")
            .header("Content-Type", "application/json")
            .body("{\n" +
                    "  \"location\": {\n" +
                    "    \"lat\": -38.383494,\n" +
                    "    \"lng\": 33.427362\n" +
                    "  },\n" +
                    "  \"accuracy\": 50,\n" +
                    "  \"name\": \"Frontline house\",\n" +
                    "  \"phone_number\": \"(+91) 983 893 3937\",\n" +
                    "  \"address\": \"29, side layout, cohen 09\",\n" +
                    "  \"types\": [\n" +
                    "    \"shoe park\",\n" +
                    "    \"shop\"\n" +
                    "  ],\n" +
                    "  \"website\": \"http://google.com\",\n" +
                    "  \"language\": \"French-IN\"\n" +
                    "}")
            .when().post("maps/api/place/add/json")
            .then().log().all().assertThat().statusCode(200);
   }
}
