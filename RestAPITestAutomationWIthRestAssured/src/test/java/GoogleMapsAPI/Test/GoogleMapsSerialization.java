package GoogleMapsAPI.Test;

import POJOGoogleMapsAPI.Location;
import POJOGoogleMapsAPI.Place;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.*;

public class GoogleMapsSerialization {
    @BeforeMethod
    public void setUp() {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
    }


    @Test
    public void addNewPlace() {
        Place place = new Place();
        place.setAccuracy(50);
        place.setName("Frontline house");
        place.setPhoneNumber("(+91) 983 893 3937");
        place.setAddress("29, side layout, cohen 09");
        ArrayList<String> types = new ArrayList<>();
        types.add("shoe park");
        types.add("shop");
        place.setTypes(types);
        place.setWebsite("http://google.com");
        place.setLanguage("French-IN");
        Location location = new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);

        place.setLocation(location);



        String addPlaceResponse = given().queryParam("key", "qaclick123")
                .body(place)
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response().asPrettyString();
    }
}
