package SoftUniFoodyAPI;

import AppsPayloads.FoodyAPIPayload;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.APIAuthentications;
import utilities.ReusableMethods;

import java.io.FileWriter;
import java.io.IOException;

import static io.restassured.RestAssured.*;

public class FoodyAPITests {

    private String authenticationToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJKd3RTZXJ2aWNlQWNjZXNzVG9rZW4iLCJqdGkiOiIzZTI5ZjYwMi1mNWE2LTRjOWMtYjAyMS1mOTcxNTM5MzgwNmUiLCJpYXQiOiIwNC8zMC8yMDI0IDIwOjA5OjI0IiwiVXNlcklkIjoiNTIxYzcwNmMtYzZhMi00OTMyLTIwMjUtMDhkYzY3YmEzZDU5IiwiRW1haWwiOiJzYW1ncmVlbkBlbWFpbC5jb20iLCJVc2VyTmFtZSI6InNhbWdyZWVuIiwiZXhwIjoxNzE0NTI5MzY0LCJpc3MiOiJGb29keV9BcHBfU29mdFVuaSIsImF1ZCI6IkZvb2R5X1dlYkFQSV9Tb2Z0VW5pIn0.lVmFZ4iQUhymu1jGoPZHiV2KV1ZmF_9hCnTSgE9BJSk";
    @BeforeMethod
    public void setUp() {
        RestAssured.baseURI = "http://softuni-qa-loadbalancer-2137572849.eu-north-1.elb.amazonaws.com:86";
    }
    @Test
    public void createNewUser() {

       given().header("Content-Type", "application/json")
                        .body(FoodyAPIPayload.createUser("sammygreen", "Samantha", "Lorel",
                "Green", "samanthagreen@email.com", "qatest")).log().all()
                .when().post("/api/User/Create")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

    }

    @Test
    public void logInAsLegitimateUserAndGetAccessToken() throws IOException {
        SessionFilter sessionFilter = new SessionFilter();

        String authenticationResponse = given().relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .body(FoodyAPIPayload.logInExistingUserAndGetToken("samgreen", "qatest"))
                .log().all().filter(sessionFilter)
                .when().post("/api/User/Authentication")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
        JsonPath jsonPath = ReusableMethods.rawToJSON(authenticationResponse);
        String token = jsonPath.getString("accessToken");

        FileWriter fileWriter = new FileWriter("FoodAPIAccessToken.txt");
        fileWriter.write(token);
        fileWriter.flush();
    }
    @Test(dataProvider = "FoodData")
    public void addMultipleFood(String foodName, String foodDescription, String foodUrl) throws IOException {
        String addNewFoodResponse = given().header("Authorization", "Bearer " + authenticationToken)
                .header("Content-Type", "application/json")
                .body(FoodyAPIPayload.createFood(foodName, foodDescription, foodUrl))
                .when().post("/api/Food/Create")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        FileWriter fileWriter = new FileWriter("NewFoodData.json", true);
        fileWriter.write(addNewFoodResponse);
        fileWriter.flush();

        Assert.assertTrue(addNewFoodResponse.contains("Strawberry cake"));
    }

    @Test
    public void addFood(){
        String addNewFoodResponse = given().headers("Authorization", "Bearer " + authenticationToken)
                .header("Content-Type", "application/json")
                .body(FoodyAPIPayload.createFood("Chicken with Rice", "Favorite dish", "www.newfooddomain.com"))
                .when().post("/api/Food/Create")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
    }

    @Test
    public void getAllFood() {
        String getllFoodResponse = given().header("Authorization", "Bearer " + authenticationToken)
                .when().get("/api/Food/All")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
    }

    @DataProvider(name = "FoodData")
    public Object[][] createMultipleFood() {
        return new Object[][]{
                {"Fried potatoes", "A very yummy dish", "www.gotvach.bg"},
                {"Strawberry cake", "A masterpiece of cream and strawberries", "www.cookingcontest.com"},
                {"Shopska salad", "A typical Bulgarian salad", "www.ngcuizine.com"}
        };
    }
}
