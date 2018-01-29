import com.google.gson.*;
import com.sun.net.httpserver.*;

import java.io.*;
import java.net.InetSocketAddress;

/**
 * class json_validate.
 * This class get json file and checks for correctness
 */

public class json_validate {
    /**
     * Main function.
     * Start server and wait json file
     *
     * @param args not used
     * @throws IOException if Input/Output exception occured
     */
    public static void main(String[] args) throws IOException{
        try {
            final HttpServer server = HttpServer.create();
            server.bind(new InetSocketAddress(80), 0);
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting().serializeNulls();
            final Gson gson = builder.create();
            HttpContext context = server.createContext("/", new HttpHandler() {
                int connectionId = 0;

                /**
                 * Check json-file for correctness
                 *
                 * @param httpExchange encapsulates a HTTP request received
                 *                     and a response to be generated in one exchange
                 * @throws IOException if Input/Output exception occured
                 */
                public void handle(HttpExchange httpExchange) throws IOException {
                    String responseString = null;
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()));
                    String buildString = bufferedReader.readLine();
                    StringBuilder stringBuilder = new StringBuilder();
                    while (buildString != null) {
                        stringBuilder.append(buildString);
                        buildString = bufferedReader.readLine();
                    }
                    String jsonString = stringBuilder.toString();
                    try {
                        Object object = gson.fromJson(jsonString, Object.class);
                        responseString = gson.toJson(object);
                    } catch (JsonSyntaxException ex) {
                        String[] errorSplittedString = ex.getMessage().split(".+: | at ");
                        String filename = httpExchange.getRequestURI().getPath();
                        JsonObject Error = new JsonObject();

                        Error.addProperty("errorCode",ex.hashCode());
                        Error.addProperty("errorMessage",errorSplittedString[1]);
                        Error.addProperty("errorPlace", "at"+errorSplittedString[2]);
                        Error.addProperty("errorPlace", "at "+errorSplittedString[2]);
                        Error.addProperty("resource",filename);
                        Error.addProperty("id",connectionId);
                        responseString = gson.toJson(Error);
                    } finally {
                        connectionId += 1;
                    }
                    System.out.println(responseString);
                    httpExchange.sendResponseHeaders(200, responseString.length());
                    OutputStream os = httpExchange.getResponseBody();
                    os.write(responseString.getBytes());
                    os.close();
                }
            });
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
