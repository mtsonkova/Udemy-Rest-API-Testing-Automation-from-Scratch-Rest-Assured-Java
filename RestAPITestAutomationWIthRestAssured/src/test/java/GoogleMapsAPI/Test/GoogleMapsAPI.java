package GoogleMapsAPI.Test;

import AppsPayloads.GoogleMapsAPIPayload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.ReusableMethods;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class GoogleMapsAPI {

private String filePath = "/src/test/java/utilities/GoogleMapsPlaces.json";
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
        String response = given().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(GoogleMapsAPIPayload.AddPlace())
                .when().post("maps/api/place/add/json")
                .then()
                .assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .body("status", equalTo("OK"))
                .header("Server", equalTo("Apache/2.4.52 (Ubuntu)"))
                .extract().response().asString();


        JsonPath jsonPath = ReusableMethods.rawToJSON(response); // class that helps to parse JSON

        String placeId = jsonPath.getString("place_id");

        System.out.println(placeId);

        //get newly added place

        String getResponse = given().queryParam("place_id", placeId)
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .when().get("/maps/api/place/get/json")
                        .then().assertThat().log().all().statusCode(200)
                .extract().response().asString();


        //update place
        given().log().all().queryParam("key", "qaclick123")
                .queryParam("place_id", placeId)
                .header("Content-Type", "application/json")
                .body(GoogleMapsAPIPayload.updatePlace(placeId))
                .when().put("maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));


        //get updated place
        String updatedPlaceInfo = given().queryParam("place_id", placeId)
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .when().get("/maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200)
                .extract().response().asString();

        JsonPath json = ReusableMethods.rawToJSON(updatedPlaceInfo);
        String address = json.getString("address");
        Assert.assertEquals(address, "70 winter walk, USA");
        // delete place by given place id

        given().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(GoogleMapsAPIPayload.deletePlace(placeId))
                .when().post("/maps/api/place/delete/json")
                .then().assertThat().log().all().statusCode(200);
    }

    @Test
    public void addNewPlace() throws IOException {
        String response = given().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(ReusableMethods.readJsonAsString(filePath))
                .when().post("maps/api/place/add/json")
                .then()
                .assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .body("status", equalTo("OK"))
                .header("Server", equalTo("Apache/2.4.52 (Ubuntu)"))
                .extract().response().asString();


        JsonPath jsonPath = ReusableMethods.rawToJSON(response); // class that helps to parse JSON

        String placeId = jsonPath.getString("place_id");

        Assert.assertFalse(placeId.isEmpty());
    }



}
