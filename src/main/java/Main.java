import client.Client;

public class Main {
    public static String ADDRESS = "localhost";
    public static int PORT = 60228;

    public static void main(String[] args) {
        Client client = new Client(ADDRESS, PORT);
        client.start();
    }
}
