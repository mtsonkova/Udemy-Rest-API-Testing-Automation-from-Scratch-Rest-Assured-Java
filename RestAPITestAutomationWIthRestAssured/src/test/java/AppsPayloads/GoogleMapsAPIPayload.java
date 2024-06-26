package AppsPayloads;

public class GoogleMapsAPIPayload {
    public static String AddPlace() {
        return "{\n" +
                "  \"location\": {\n" +
                "    \"lat\": -38.383494,\n" +
                "    \"lng\": 33.427362\n" +
                "  },\n" +
                "  \"accuracy\": 50,\n" +
                "  \"name\": \"Frontline house\",\n" +
                "  \"phone_number\": \"(+91) 983 893 3937\",\n" +
                "  \"address\": \"29, side layout, cohen 09\",\n" +
                "  \"types\": [\n" +
                "    \"shoe park\",\n" +
                "    \"shop\"\n" +
                "  ],\n" +
                "  \"website\": \"http://google.com\",\n" +
                "  \"language\": \"French-IN\"\n" +
                "}";
    }

    public static String updatePlace(String placeId) {
        return "{\n" +
        "\"place_id\":\"" + placeId + "\",\r\n" +
                "\"address\":\"70 winter walk, USA\",\n" +
                "\"key\":\"qaclick123\"\n" +
                "}";
    }

    public static String deletePlace(String placeId) {
        return "{\n" +
                "    \"place_id\":\"" + placeId +"\"\n" +
                "}";
    }

    public static String coursePrice() {
         return "{\n" +
                 "\n" +
                 "\"dashboard\": {\n" +
                 "\n" +
                 "\"purchaseAmount\": 910,\n" +
                 "\n" +
                 "\"website\": \"rahulshettyacademy.com\"\n" +
                 "\n" +
                 "},\n" +
                 "\n" +
                 "\"courses\": [\n" +
                 "\n" +
                 "{\n" +
                 "\n" +
                 "\"title\": \"Selenium Python\",\n" +
                 "\n" +
                 "\"price\": 50,\n" +
                 "\n" +
                 "\"copies\": 6\n" +
                 "\n" +
                 "},\n" +
                 "\n" +
                 "{\n" +
                 "\n" +
                 "\"title\": \"Cypress\",\n" +
                 "\n" +
                 "\"price\": 40,\n" +
                 "\n" +
                 "\"copies\": 4\n" +
                 "\n" +
                 "},\n" +
                 "\n" +
                 "{\n" +
                 "\n" +
                 "\"title\": \"RPA\",\n" +
                 "\n" +
                 "\"price\": 45,\n" +
                 "\n" +
                 "\"copies\": 10\n" +
                 "\n" +
                 "}\n" +
                 "\n" +
                 "]\n" +
                 "\n" +
                 "}\n" +
                 "\n";
    }
}

