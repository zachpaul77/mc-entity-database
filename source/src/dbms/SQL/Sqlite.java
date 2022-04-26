package dbms.SQL;

import java.sql.*;

public class Sqlite {
    public static Connection con;

    public Sqlite() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:MainDB.db");
    }

    public static ResultSet doCommandAndReturn(String sql) throws SQLException {
        Statement state = Sqlite.con.createStatement();
        ResultSet res = state.executeQuery(sql);
        return res;
    }

    public static void doCommand(String sql) throws SQLException {
        Statement state = Sqlite.con.createStatement();
        state.executeUpdate(sql);
        state.close();
    }

}
