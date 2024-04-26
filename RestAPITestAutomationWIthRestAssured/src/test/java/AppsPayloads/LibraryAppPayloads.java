package AppsPayloads;

public class LibraryAppPayloads {
    public static String addBook(String title, String isbn, String aisle, String author) {
        return "{\n" +
                "\n" +
                "\"name\":\"" + title + "\",\n" +
                "\"isbn\":\"" + isbn + "\",\n" +
                "\"aisle\":\"" + aisle + "\",\n" +
                "\"author\":\"" + author + "\"\n" +
                "}\n";
    }

    public static String deleteBook(String id) {
        return"{\n" +
                " \n" +
                "\"ID\" : \"" + id + "\"\n" +
                " \n" +
                "} \n";
    }
}
