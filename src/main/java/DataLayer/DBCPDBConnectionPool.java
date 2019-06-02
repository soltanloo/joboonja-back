package DataLayer;


import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * do not reinvent the wheel!!!
 *
 * you can use DBCP or other libraries.
 *
 * @see <a href="https://www.baeldung.com/java-connection-pooling">A Simple Guide to Connection Pooling in Java</a>
 *
 * */
public class DBCPDBConnectionPool {

    private static BasicDataSource ds = new BasicDataSource();
    private final static String dbURL = "jdbc:mysql://db:3306/joboonja";

    static {
        ds.setUrl(dbURL);
        ds.setUsername("root");
        ds.setPassword("root");
        ds.setMinIdle(1);
        ds.setMaxIdle(2);
    }

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                return ds.getConnection();
            } catch (SQLException ex) {
                continue;
            }
        }
    }

    private DBCPDBConnectionPool(){ }
}
