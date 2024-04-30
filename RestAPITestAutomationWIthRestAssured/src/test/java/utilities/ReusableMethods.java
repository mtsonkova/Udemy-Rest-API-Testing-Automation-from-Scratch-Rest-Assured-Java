package utilities;

import io.restassured.path.json.JsonPath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReusableMethods {

    public static JsonPath rawToJSON(String response) {
        JsonPath jsPath = new JsonPath(response);
        return jsPath;
    }

    public static String readJsonAsString(String path) throws IOException {
        String JSONAsString = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + path)));
        return JSONAsString;
    }
}
