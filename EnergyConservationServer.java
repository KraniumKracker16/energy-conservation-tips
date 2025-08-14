import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;

// NOTE: This server uses the minimal, built-in Java HTTP Server.
// It is for demonstration purposes and is not intended for production.
public class EnergyConservationServer {

    // A simple, in-memory store for conservation efforts
    private static Map<String, Integer> conservationEfforts = new HashMap<>();
    
    // Quiz questions and answers, formatted for JSON
    private static final List<Map<String, Object>> QUIZ_QUESTIONS = Arrays.asList(
        new HashMap<String, Object>() {{
            put("question", "Which action can save fuel while driving?");
            put("options", Arrays.asList("Accelerate gently", "Drive fast", "Idle your car", "Use cruise control"));
            put("correct", Arrays.asList(0, 3)); // Correct indices are 0 and 3
        }},
        new HashMap<String, Object>() {{
            put("question", "What thermostat setting is energy efficient?");
            put("options", Arrays.asList("68°F", "78°F", "85°F", "60°F"));
            put("correct", Arrays.asList(1));
        }},
        new HashMap<String, Object>() {{
            put("question", "Why should AC filters be cleaned?");
            put("options", Arrays.asList("To improve efficiency", "To look nice", "To cool faster", "To make noise"));
            put("correct", Arrays.asList(0));
        }},
        new HashMap<String, Object>() {{
            put("question", "What helps reduce car energy use?");
            put("options", Arrays.asList("Underinflated tires", "Proper tire inflation", "Removing extra weight", "Idling engine"));
            put("correct", Arrays.asList(1, 2));
        }}
    );

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        
        // Handler for serving the HTML file
        server.createContext("/", new FileHandler("EnergyConservationApp.html", "text/html"));
        
        // Handler for the quiz questions API
        server.createContext("/api/quiz", new QuizHandler());
        
        // Handler for tracking conservation efforts
        server.createContext("/api/track", new TrackHandler());

        // Handler for getting the summary of efforts
        server.createContext("/api/summary", new SummaryHandler());
        
        server.setExecutor(null); // Creates a default executor
        server.start();
        System.out.println("Energy Conservation Server started on port 8000.");
        System.out.println("Open EnergyConservationApp.html in your browser or go to http://localhost:8000/ to access the app.");
    }
    
    /**
     * A generic handler for serving static files.
     */
    static class FileHandler implements HttpHandler {
        private String filename;
        private String contentType;

        public FileHandler(String filename, String contentType) {
            this.filename = filename;
            this.contentType = contentType;
        }

        @Override
        public void handle(HttpExchange t) throws IOException {
            try {
                byte[] response = Files.readAllBytes(Paths.get(filename));
                t.getResponseHeaders().set("Content-Type", contentType);
                t.sendResponseHeaders(200, response.length);
                OutputStream os = t.getResponseBody();
                os.write(response);
                os.close();
            } catch (IOException e) {
                String response = "404 (Not Found): " + e.getMessage();
                t.sendResponseHeaders(404, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }

    /**
     * Handles requests for quiz questions.
     */
    static class QuizHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            // Convert the list of maps to a JSON array string
            JSONArray jsonArray = new JSONArray(QUIZ_QUESTIONS);
            String response = jsonArray.toString();
            
            t.getResponseHeaders().set("Content-Type", "application/json");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    /**
     * Handles requests to track conservation efforts.
     */
    static class TrackHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            if ("POST".equals(t.getRequestMethod())) {
                try {
                    // Read the request body (JSON)
                    String requestBody = new String(t.getRequestBody().readAllBytes());
                    JSONObject json = new JSONObject(requestBody);
                    
                    // Update the in-memory map based on the request
                    for (String key : json.keySet()) {
                        int value = json.getInt(key);
                        conservationEfforts.put(key, conservationEfforts.getOrDefault(key, 0) + value);
                    }
                    
                    String response = "{\"status\":\"success\"}";
                    t.getResponseHeaders().set("Content-Type", "application/json");
                    t.sendResponseHeaders(200, response.length());
                    OutputStream os = t.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                } catch (Exception e) {
                    String response = "{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}";
                    t.sendResponseHeaders(500, response.length());
                    OutputStream os = t.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
            }
        }
    }

    /**
     * Handles requests for the summary of efforts.
     */
    static class SummaryHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            JSONObject json = new JSONObject(conservationEfforts);
            String response = "{\"summary\":" + json.toString() + "}";
            
            t.getResponseHeaders().set("Content-Type", "application/json");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
