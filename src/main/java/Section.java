import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public interface Section {
    void HandleRequest(HttpExchange httpExchange) throws IOException;
}
