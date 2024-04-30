import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        UseCaseHandeler useCaseHandeler = new UseCaseHandeler();
        server.createContext("/UC1", useCaseHandeler);
        server.createContext("/UC2", useCaseHandeler);
        server.setExecutor(null);
        server.start();

        //Wait for button press
        System.in.read();
        server.stop(0);
        useCaseHandeler.dumpTimes();
    }
}
