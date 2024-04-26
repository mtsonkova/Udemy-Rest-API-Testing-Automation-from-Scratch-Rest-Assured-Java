package LibraryAPP;

import AppsPayloads.LibraryAppPayloads;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.ReusableMethods;

import static io.restassured.RestAssured.*;


public class LibraryAPPTests {

    private String authorName = "John Doe";

    @BeforeMethod(alwaysRun = true)
    public void setUP() {
        RestAssured.baseURI = "http://216.10.245.166";
    }

    @Test(dataProvider = "BookData")
    public void addBook(String bookTitle, String isbn, String aisle, String author) {
        String addBookResponse = given()
                .header("Content-Type", "application/json")
                .body(LibraryAppPayloads.addBook(bookTitle, isbn, aisle, author))
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
                .body(LibraryAppPayloads.deleteBook("abc132"))
                .when().post("/Library/DeleteBook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath jsonPath = ReusableMethods.rawToJSON(deleteBookResponse);
        String msg = jsonPath.getString("msg");
        Assert.assertEquals(msg, "book is successfully deleted");
    }

    @DataProvider(name = "BookData")
    public Object[][] getData() {
        return new Object[][] {
            {"Test Automation with Java and Selenium for absolute beginners", "435", "azt", "Tim Brown"},
                {"Getting started with Swift", "546", "fdg", "Steve Jobs"},
                {"Android Apps testing for absolute beginners", "445", "adg", "Lee Quang"},
                {"How it started", "438", "ref", "Simon Hill"}
        };
    }
}
