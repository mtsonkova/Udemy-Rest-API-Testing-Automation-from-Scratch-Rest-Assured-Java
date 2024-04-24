package Files;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {
    public static JsonPath rawToJSON(String response) {
        JsonPath jsPath = new JsonPath(response);
        return jsPath;

    }
}
