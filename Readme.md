# VERON - A Simple Java HTTP Server Framework

VERON is a lightweight HTTP server implemented in Java. It provides basic routing functionality and serves as a foundation for building a web framework in the future.

## Project Structure

- **Main.java**: The entry point of the application. It starts the server and handles client connections.
- **Router.java**: Manages routes and delegates requests to appropriate handlers.
- **Views.java**: Contains methods to render specific pages.
- **Utils.java**: Provides utility functions for parsing HTTP requests and rendering HTML files.
- **run.sh**: A shell script to compile and run the project.

## How It Works

1. The server listens on port `8080` for incoming HTTP requests.
2. When a request is received, the `Router` checks if the requested path matches a registered route.
3. If a route is found, the corresponding handler in `Views` is called to render the response.
4. If no route matches, a `404 Not Found` response is returned.

### Routes

- `/`: Renders the home page (`templates/index.html`).
- `/about`: Renders the about page (`templates/about.html`).

## Prerequisites

- Java Development Kit (JDK) installed.
- Bash shell (for running the `run.sh` script).

## How to Run

1. Clone or download the project to your local machine.
2. Ensure you have the required HTML files in the `templates` directory:
   - `templates/index.html`
   - `templates/about.html`
3. Open a terminal and navigate to the project directory.
4. Run the following command to start the server:
   ```bash
   ./run.sh
   ```
5. Open a web browser and navigate to `http://localhost:8080/` or `http://localhost:8080/about`.

## Example Output

### Home Page
When accessing `/`, the server will render the home page and log:
```
Rendering Home Page
Rendering HTML: templates/index.html
```

### About Page
When accessing `/about`, the server will render the about page and log:
```
Rendering About Page
Rendering HTML: templates/about.html
```

### 404 Not Found
When accessing an undefined route, the server will return a `404 Not Found` response and log:
```
404 Not Found: /undefined
```

## Future Scope

VERON is designed to be a starting point for a Java-based web framework. Potential future features include:
- Middleware support.
- Dynamic route parameters.
- JSON response handling.
- Session and cookie management.

## Notes

- Ensure the `templates` directory exists and contains the required HTML files.
- The server runs on port `8080` by default. You can modify this in `Main.java` if needed.