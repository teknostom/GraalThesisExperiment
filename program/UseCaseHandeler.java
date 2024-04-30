import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.ArrayList;

class UC1 {
    public String handleUC1() {
        // Simulating reading a large CSV-like text block
        String[] data = new String[1000];
        for (int i = 0; i < data.length; i++) {
            data[i] = "Name,Value" + i + "," + (i * 100);
        }

        // Processing each line to extract and transform data
        int count = 0;
        for (String line : data) {
            String[] parts = line.split(",");
            if (Integer.parseInt(parts[2]) % 200 == 0) {
                count++;
            }
        }
        return "Processed " + count + " valid entries.";
    }
}

class UC2 {
    public String handleUC2() {
        int size = 100;
        int[][] matrix = new int[size][size];
        int[][] transposed = new int[size][size];

        // Initialize matrix and perform operations like transposition
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = i * j;
                transposed[j][i] = matrix[i][j];
            }
        }

        // Example computation - trace of matrix (sum of diagonal elements)
        int trace = 0;
        for (int i = 0; i < size; i++) {
            trace += transposed[i][i];
        }

        return "The trace of the matrix is: " + trace;
    }
}

public class UseCaseHandeler implements HttpHandler {

    private ArrayList<Long> timesUC1 = new ArrayList<>();
    private ArrayList<Long> timesUC2 = new ArrayList<>();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Start the time measurement
        long startTime = System.nanoTime();
        String requestPath = exchange.getRequestURI().getPath();

        switch (requestPath) {
            case "/UC1":
                UC1 uc1 = new UC1();
                sendResponse(exchange, uc1.handleUC1());
                break;
            case "/UC2":
                UC2 uc2 = new UC2();
                sendResponse(exchange, uc2.handleUC2());
                break;
            default:
                System.out.println("Default");
                break;
        }
        // End the time measurement
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        if (requestPath.equals("/UC1")) {
            timesUC1.add(duration);
        } else if (requestPath.equals("/UC2")) {
            timesUC2.add(duration);
        }
    }

    public void dumpTimes() {
        System.out.println("[");
        System.out.println("UC1: [");
        for (long time : timesUC1) {
            System.out.println(time + ",");
        }
        System.out.println("]");
        System.out.println("UC2: [");
        for (long time : timesUC2) {
            System.out.println(time + ",");
        }
        System.out.println("]");
        System.out.println("]");
    }

    public void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.length());
        exchange.getResponseBody().write(response.getBytes());
        exchange.getResponseBody().close();
    }
}
