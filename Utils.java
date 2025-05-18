import java.io.*;
import java.net.Socket;

public class Utils {
    public static String[] ParseRoute(Socket client) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String inputLine = null;
        String method = null;
        String path = null;

        try {
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if (method == null && path == null && inputLine.contains(" ")) {
                    String[] requestParts = inputLine.split(" ");
                    if (requestParts.length >= 2) {
                        method = requestParts[0];
                        path = requestParts[1];
                        System.out.println("Method: " + method);
                        System.out.println("Path: " + path);
                    }
                }
                if (inputLine.isEmpty()) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (method == null || path == null) {
            System.out.println("Invalid request");
            return null;
        } else {
            return new String[]{method, path};
        }
    }

    public static void RenderHTML(String path, PrintWriter writer) {
        try {
            System.out.println("Rendering HTML: " + path);
            File htmlFile = new File(path);
            BufferedReader fileReader = new BufferedReader(new FileReader(htmlFile));
            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = fileReader.readLine()) != null) {
                responseBody.append(line).append("\n");
            }
            fileReader.close();
            writer.println("HTTP/1.1 200 OK");
            writer.println("Content-Type: text/html");
            writer.println("Content-Length: " + responseBody.toString().getBytes("UTF-8").length);
            writer.println();
            writer.println(responseBody.toString());
        } catch (IOException e) {
            e.printStackTrace();
            writer.println("HTTP/1.1 500 Internal Server Error");
            writer.println("Content-Type: text/plain");
            writer.println();
            writer.println("Internal Server Error");
        }
    }
}
