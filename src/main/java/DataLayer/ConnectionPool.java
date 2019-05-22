package DataLayer;

import DataLayer.DBConnectionPool.BasicDBConnectionPool;
import DataLayer.DBConnectionPool.impl.SQLiteBasicDBConnectionPool;

public class ConnectionPool {

    private final static String dbURL = "jdbc:mysql://db/joboonja";
    private static BasicDBConnectionPool instance;

    public static BasicDBConnectionPool getInstance() {
        if (instance == null) {
            instance = new SQLiteBasicDBConnectionPool(2, 4 , dbURL);
        }
        return instance;
    }
}
