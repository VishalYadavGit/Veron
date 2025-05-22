import java.util.Map;
import java.util.HashMap;
import java.io.PrintWriter;
import java.util.Objects;

public class Router {
    public interface RouteHandler {
        void handle(PrintWriter writer);
    }

    private static class RouteKey {
        private final String path;
        private final String method;

        public RouteKey(String path, String method) {
            this.path = normalizePath(path);
            this.method = method;
        }

        private static String normalizePath(String path) {
            if (path.endsWith("/") && path.length() > 1) {
                return path.substring(0, path.length() - 1);
            }
            return path;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            RouteKey routeKey = (RouteKey) obj;
            return Objects.equals(path, routeKey.path) && Objects.equals(method, routeKey.method);
        }

        @Override
        public int hashCode() {
            return Objects.hash(path, method);
        }
    }

    private Map<RouteKey, RouteHandler> routes = new HashMap<>();

    public void addRoute(String path, String method, RouteHandler handler) {
        RouteKey key = new RouteKey(path, method);
        routes.put(key, handler);
    }

    public void handleRequest(String path, String method, PrintWriter writer) {
        RouteKey key = new RouteKey(path, method);
        RouteHandler handler = routes.get(key);
        if (handler != null) {
            handler.handle(writer);
        } else {
            System.out.println("404 Not Found: " + path + " [" + method + "]");
            writer.println("HTTP/1.1 404 Not Found");
            writer.println("Content-Type: text/plain");
            writer.println();
            writer.println("404 Not Found");
        }
    }

    public void initializeRoutes() {
        addRoute("/", "GET", Views::RenderHome);
        addRoute("/about", "GET", Views::RenderAbout);
        addRoute("/submit", "POST", Views::HandleSubmit);
    }
}