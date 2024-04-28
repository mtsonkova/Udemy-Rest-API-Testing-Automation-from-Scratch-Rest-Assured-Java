package AppsPayloads;

public class FoodyAPIPayload {

    public static String createUser(String userName, String firstName, String midName, String lastName, String email, String password) {
        String newUser = "{\n" +
                "\"userName\": \"" + userName + "\",\n" +
                "\"firstName\": \"" + firstName + "\", \n" +
                "\"midName\": \"" + midName + "\", \n" +
                "\"lastName\": \"" + lastName + "\", \n" +
                "\"email\": \"" + email+ "\", \n" +
                "\"password\": \"" + password + "\", \n" +
                "\"rePassword\": \"" + password + "\"\n" +
                "}\n";

        return newUser;
    }

    public static String logInExistingUserAndGetToken(String userName, String password) {
        return "{\n" +
                "\"userName\": \"" + userName + "\", \n" +
                "\"password\": \"" + password + "\"\n" +
                "}\n";
    }

}
