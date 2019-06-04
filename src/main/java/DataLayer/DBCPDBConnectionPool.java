package DataLayer;


import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
    private final static String dbURL = "jdbc:mysql://db:3306/joboonja?useUnicode=yes&characterEncoding=UTF-8";

    static {
        ds.setUrl(dbURL);
        ds.setUsername("root");
        ds.setPassword("root");
        ds.setMinIdle(1);
        ds.setMaxIdle(2);
        setEncoding();
    }

    private static String getAlterTableString() {
        return "ALTER DATABASE joboonja CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";
    }

    private static void setEncoding() {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.execute(getAlterTableString());
            connection.close();
            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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
                ex.printStackTrace();
                continue;
            }
        }
    }

    private DBCPDBConnectionPool(){ }
}
