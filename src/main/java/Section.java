import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public interface Section {
    public void HandleRequest(HttpExchange httpExchange) throws IOException;
}
