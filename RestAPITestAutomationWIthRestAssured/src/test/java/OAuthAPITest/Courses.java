package OAuthAPITest;

import POJO.Api;
import POJO.OAuthAPIAllCourses;
import POJO.WebAutomation;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class Courses {


    @Test
    public void getCoursesDetails() {
        // get access token

        Response response = given()
                .formParam( "client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam(" client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type", "client_credentials")
                .formParam("scope", "trust")
                .when().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token");

        String accessToken = response.getBody().jsonPath().getString("access_token");

        // get courses details

        Response bookDetails = given().queryParam("access_token", accessToken)
                .when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails");
        String bookDetailsContent = bookDetails.asPrettyString();
        System.out.println(bookDetailsContent);
        int statusCode = bookDetails.getStatusCode();
        System.out.println(statusCode);

        Assert.assertTrue(statusCode == 401);
        Assert.assertFalse(bookDetailsContent.isEmpty());
        Assert.assertTrue(bookDetailsContent.contains("Appium-Mobile Automation using Java"));
    }

    @Test
    public void getCoursesDetailsWithPOjoClasses() {
        // get access token

        Response response = given()
                .formParam( "client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam(" client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type", "client_credentials")
                .formParam("scope", "trust")
                .when().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token");

        String accessToken = response.getBody().jsonPath().getString("access_token");

        // get courses details

        OAuthAPIAllCourses allCoursesResponse = given()
                .queryParam("access_token", accessToken)
                .when()
                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(OAuthAPIAllCourses.class);

     List<Api> apiCourses = allCoursesResponse.getCourses().getApi();
     int coursePrice = 0;
     for(int i = 0; i < apiCourses.size(); i++) {
         if(apiCourses.get(i).getCourseTitle().contains("Rest Assured")) {
             coursePrice = Integer.parseInt(apiCourses.get(i).getPrice());
             break;
         }
     }
        System.out.println(coursePrice);
     Assert.assertTrue(coursePrice > 0);
    }


    @Test
    public void getAllCoursesFromWebAutomation() {
        Response response = given()
                .formParam( "client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam(" client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type", "client_credentials")
                .formParam("scope", "trust")
                .when().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token");

        String accessToken = response.getBody().jsonPath().getString("access_token");

        // get courses details
        String[] expectedCourseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};

        OAuthAPIAllCourses allCoursesResponse = given()
                .queryParam("access_token", accessToken)
                .when()
                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(OAuthAPIAllCourses.class);

        List<WebAutomation> webAutomationsCourses = allCoursesResponse.getCourses().getWebAutomation();

        for(int i = 0; i < webAutomationsCourses.size(); i++) {
            String currentTitle = webAutomationsCourses.get(i).getCourseTitle();
           Assert.assertEquals(currentTitle, expectedCourseTitles[i]);
        }
    }
}
