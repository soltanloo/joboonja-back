public class JoboonjaServer {
    public void startServer() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/projects", new Handler.ProjectHandler());
        server.createContext("/user", new Handler.UserHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    class JoboonjaHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            StringTokenizer tokenizer = new StringTokenizer(httpExchange.getRequestURI().getPath(), "/");
            String context = tokenizer.nextToken();
            String page = tokenizer.nextToken();
            System.out.println("context is: " + page);
            Class<IPage> pageClass;
            try {
                pageClass = (Class<IPage>) Class.forName("ir.ac.ut.ece.modernserver." + page);
                IPage newInstance = pageClass.getDeclaredConstructor().newInstance();
                newInstance.HandleRequest(httpExchange);
            } catch (ClassNotFoundException |
                    InstantiationException |
                    IllegalAccessException |
                    IllegalArgumentException |
                    InvocationTargetException |
                    NoSuchMethodException |
                    SecurityException e) {
                e.printStackTrace();
                String response =
                        "<html>"
                                + "<body>Page \""+ page + "\" not found.</body>"
                                + "</html>";
                httpExchange.sendResponseHeaders(404, response.length());
                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }