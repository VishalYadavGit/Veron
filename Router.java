import java.util.Map;
import java.util.HashMap;
import java.io.PrintWriter;

public class Router {
    public interface RouteHandler {
        void handle(PrintWriter writer);
    }

    private Map<String, RouteHandler> routes = new HashMap<>();

    public void addRoute(String path, RouteHandler handler) {
        routes.put(path, handler);
    }

    public void handleRequest(String path, PrintWriter writer) {
        RouteHandler handler = routes.get(path);
        if (handler != null) {
            handler.handle(writer);
        } else {
            System.out.println("404 Not Found: " + path);
            writer.println("HTTP/1.1 404 Not Found");
            writer.println("Content-Type: text/plain");
            writer.println();
            writer.println("404 Not Found");
        }
    }

    public void initializeRoutes() {
        addRoute("/", Views::RenderHome);
        addRoute("/about", Views::RenderAbout);
    }
}