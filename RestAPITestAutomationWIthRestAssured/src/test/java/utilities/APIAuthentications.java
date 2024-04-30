package utilities;

import AppsPayloads.FoodyAPIPayload;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;

public class APIAuthentications {
    public static String getTokenForFoodAPI() {
        RestAssured.baseURI = "http://softuni-qa-loadbalancer-2137572849.eu-north-1.elb.amazonaws.com:86";
        SessionFilter sessionFilter = new SessionFilter();

        String authenticationResponse = given().relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .body(FoodyAPIPayload.logInExistingUserAndGetToken("samgreen", "qatest"))
                .log().all().filter(sessionFilter)
                .when().post("/api/User/Authentication")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
        JsonPath jsonPath = ReusableMethods.rawToJSON(authenticationResponse);
        String token = jsonPath.getString("accessToken");
        return token;
    }
}
