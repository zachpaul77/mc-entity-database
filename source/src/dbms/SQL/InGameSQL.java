package dbms.SQL;

import java.sql.*;

public class InGameSQL {

    public InGameSQL() throws SQLException {
        initialize();
    }

    public ResultSet getAll() throws SQLException {
        Statement state = Sqlite.con.createStatement();
        ResultSet res = state.executeQuery("SELECT inGameID, entityID, customName, posX, posY, posZ, dimension FROM inGame");
        return res;
    }

    private void initialize() throws SQLException {
        Statement state = Sqlite.con.createStatement();
        ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='inGame'");
        if (!res.next()) {
            System.out.println("Building the inGame table.");
            // build table
            Statement state2 = Sqlite.con.createStatement();
            state2.execute("CREATE TABLE inGame(inGameID integer,"
                    + "entityID integer,"
                    + "customName varchar(40),"
                    + "posX float,"
                    + "posY float,"
                    + "posZ float,"
                    + "dimension varchar(40),"
                    + "FOREIGN KEY(entityID) REFERENCES entity(entityID),"
                    + "primary key(inGameID));");
        }
    }

}
