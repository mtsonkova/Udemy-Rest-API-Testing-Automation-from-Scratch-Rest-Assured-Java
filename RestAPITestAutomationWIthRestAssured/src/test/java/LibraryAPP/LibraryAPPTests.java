package LibraryAPP;

import AppsPayloads.LibraryAppPayloads;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.ReusableMethods;

import static io.restassured.RestAssured.*;


public class LibraryAPPTests {

    private String authorName = "John Doe";

    @BeforeMethod(alwaysRun = true)
    public void setUP() {
        RestAssured.baseURI = "http://216.10.245.166";
    }

    @Test
    public void addBook() {
        String addBookResponse = given()
                .header("Content-Type", "application/json")
                .body(LibraryAppPayloads.addBook())
                .when().post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath jPath = ReusableMethods.rawToJSON(addBookResponse);
        String bookId = jPath.getString("ID");
        Assert.assertFalse(bookId.isEmpty());

    }

    @Test
    public void getAllBooksByAuthorName() {
        String getBooksByAuthorsNameResponse = given().queryParam("AuthorName", authorName)
                .when().get("/Library/GetBook.php")
                .then().assertThat().statusCode(200).extract().response().asString();
        JsonPath jsonPath = ReusableMethods.rawToJSON(getBooksByAuthorsNameResponse);
        String title = jsonPath.getString("book_name");
      Assert.assertTrue(title.contains("Learn Appium Automation with Java selenium"));
    }

    @Test
    public void getBookById() {

        String getBookByIDResponse = given().queryParam("ID", "abc132")
                .when().get("Library/GetBook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

       JsonPath jsonPath = ReusableMethods.rawToJSON(getBookByIDResponse);
        String author= jsonPath.getString("book_name");
        Assert.assertEquals(author, "[Learn Appium Automation with JavaScript]");
    }

    @Test
    public void deleteBook() {
        String deleteBookResponse = given()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        " \n" +
                        "\"ID\" : \"abc132\"\n" +
                        " \n" +
                        "} \n")
                .when().post("/Library/DeleteBook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath jsonPath = ReusableMethods.rawToJSON(deleteBookResponse);
        String msg = jsonPath.getString("msg");
        Assert.assertEquals(msg, "book is successfully deleted");
    }
}
