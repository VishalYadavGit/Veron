import java.io.*;
import java.net.*;

class Main {
    public static void HandleClient(Socket client, Router router) {
        try {
            String[] RequestData = Utils.ParseRoute(client);
            if (RequestData == null) return;

            String path = RequestData[1];
            System.out.println("Routing path: " + path);

            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            router.handleRequest(path, out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(8080)) {
            System.out.println("Server started on port 8080");
            System.out.println("Waiting for client connection...");

            Router router = new Router();
            router.initializeRoutes();

            while (true) {
                Socket client = server.accept();
                HandleClient(client, router);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}