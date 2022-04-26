package dbms.SQL;

import java.sql.*;

public class EntitySQL {

    public ResultSet getAll() throws SQLException {
        Statement state = Sqlite.con.createStatement();
        ResultSet res = state.executeQuery("SELECT entityID, type FROM entity");
        return res;
    }

}
