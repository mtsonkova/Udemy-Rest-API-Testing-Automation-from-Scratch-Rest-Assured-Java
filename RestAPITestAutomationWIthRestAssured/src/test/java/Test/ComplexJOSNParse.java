package Test;

import Files.ReusableMethods;
import Files.payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ComplexJOSNParse {

    //1. Print No of courses returned by API
    //2.Print Purchase Amount
    //3. Print Title of the first course
    //4. Print All course titles and their respective Prices
    //5. Print no of copies sold by RPA Course
    //6. Verify if Sum of all Course prices matches with Purchase Amount
    JsonPath jpath = ReusableMethods.rawToJSON(payload.coursePrice());

    //1. Print No of courses returned by API
    @Test
    public void getNumOfCourses() {
        int numOfCourses = jpath.getInt("courses.size()");
        Assert.assertEquals(numOfCourses, 3);
    }

    //2.Print Purchase Amount
    @Test
    public void getPurchaseAmount() {
        int purchaseAmount = jpath.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(purchaseAmount, 910);
    }

    //3. Print Title of the first course
    @Test
    public void getTitleOfFirstCourse() {
        String firstCourseTitle = jpath.getString("courses[0].title");
        String expected = "Selenium Python";
        Assert.assertEquals(firstCourseTitle, expected);
    }

    //4. Print All course titles and their respective Prices
    @Test
    public void getAllCourseTitlesAndPrices() {
        int numOfCourses = jpath.getInt("courses.size()");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < numOfCourses; i++) {
            String currentCourseName = jpath.getString("courses[" + i + "].title");
            int currentCoursePrice = jpath.getInt("courses[" + i + "].price");
            sb.append(String.format("%s -> %d", currentCourseName, currentCoursePrice) + System.lineSeparator());
        }
        String result = sb.toString();

        Assert.assertFalse(result.isEmpty());
    }

    //5. Print no of copies sold by RPA Course

    @Test
    public void getNumOfCopiesSoldByRPACourse() {
        int numOfCourses = jpath.getInt("courses.size()");
        int numOfCopies = 0;
        for (int i = 0; i < numOfCourses; i++) {
            String currentCourseName = jpath.getString("courses[" + i + "].title");
            if (currentCourseName.equals("RPA")) {
                numOfCopies = jpath.getInt("courses[" + i + "].copies");
                break;
            }
        }
        Assert.assertEquals(numOfCopies, 10);
    }

    //6. Verify if Sum of all Course prices matches with Purchase Amount
    @Test
    public void verifyIdSumOfAllCoursePricesEqualsPurchaseAmount() {
        int purchaseAmount = jpath.getInt("dashboard.purchaseAmount");
        int sumOfAllPurchasedCoursesPrice = 0;
        int numOfCourses = jpath.getInt("courses.size()");

        for (int i = 0; i < numOfCourses; i++) {
            int currentCourseCopies = jpath.getInt("courses[" + i + "].copies");
            int currentCoursePrice = jpath.getInt("courses[" + i + "].price");

            sumOfAllPurchasedCoursesPrice += (currentCoursePrice * currentCourseCopies);
        }
        Assert.assertEquals(sumOfAllPurchasedCoursesPrice, purchaseAmount);
    }
}
