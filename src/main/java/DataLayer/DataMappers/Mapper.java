package DataLayer.DataMappers;

import DataLayer.DBCPDBConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class Mapper<T, I> implements IMapper<T, I> {


    abstract protected String getFindStatement();

    abstract protected T convertResultSetToDomainModel(ResultSet rs) throws SQLException;


    public T find(I id){
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindStatement())
        ) {
            st.setString(1, id.toString());
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                resultSet.next();
                return convertResultSetToDomainModel(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
