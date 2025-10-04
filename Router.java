import java.util.*;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class Router {

    // Safe addition: method enum (but still supports String input for backward compatibility)
    public enum HttpMethod {
        GET, POST, PUT, DELETE
    }

    public interface RouteHandler {
        void handle(PrintWriter writer);
    }

    private static class RouteKey {
        private final String path;
        private final String method;

        public RouteKey(String path, String method) {
            this.path = normalizePath(path);
            this.method = method.toUpperCase();
        }

        private static String normalizePath(String path) {
            path = path.trim();
            if (path.endsWith("/") && path.length() > 1)
                path = path.substring(0, path.length() - 1);
            return path.toLowerCase();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            RouteKey routeKey = (RouteKey) obj;
            return Objects.equals(path, routeKey.path) &&
                   Objects.equals(method, routeKey.method);
        }

        @Override
        public int hashCode() {
            return Objects.hash(path, method);
        }
    }

    private final Map<RouteKey, RouteHandler> routes = new HashMap<>();
    private static final Logger logger = Logger.getLogger(Router.class.getName());

    // backward-compatible addRoute
    public void addRoute(String path, String method, RouteHandler handler) {
        RouteKey key = new RouteKey(path, method);
        routes.put(key, handler);
        logger.info("Added route: " + method + " " + path);
    }

    public void addRoute(String path, HttpMethod method, RouteHandler handler) {
        addRoute(path, method.name(), handler);
    }

    public void handleRequest(String path, String method, PrintWriter writer) {
        RouteKey key = new RouteKey(path, method);
        RouteHandler handler = routes.get(key);

        if (handler != null) {
            handler.handle(writer);
        } else {
            send404(writer, path);
        }
    }

    private void send404(PrintWriter writer, String path) {
        logger.warning("404 Not Found: " + path);
        writer.println("HTTP/1.1 404 Not Found");
        writer.println("Content-Type: text/plain");
        writer.println();
        writer.println("404 Not Found: " + path);
    }

    public void initializeRoutes() {
        addRoute("/", "GET", Views::RenderHome);
        addRoute("/about", "GET", Views::RenderAbout);
        addRoute("/submit", "POST", Views::HandleSubmit);
    }
}
