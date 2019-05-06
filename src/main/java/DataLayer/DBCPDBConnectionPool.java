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
    private final static String dbURL = "jdbc:sqlite:/Users/thesoli/Desktop/Courses/6-Spring98/IE/CAs/CA5/joboonja-back/joboonja.db";

    static {
        ds.setUrl(dbURL);
        ds.setMinIdle(1);
        ds.setMaxIdle(2);
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ds.getConnection();
    }

    private DBCPDBConnectionPool(){ }
}
