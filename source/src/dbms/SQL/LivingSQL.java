package dbms.SQL;

import java.sql.*;

public class LivingSQL {

    public LivingSQL() throws SQLException {
        initialize();
    }

    public ResultSet getAll() throws SQLException {
        Statement state = Sqlite.con.createStatement();
        ResultSet res = state.executeQuery("SELECT livingID, inGameID, health, attackDamage, burnsInLight, hostility FROM living");
        return res;
    }

    private void initialize() throws SQLException {
            Statement state = Sqlite.con.createStatement();
            ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='living'");
            if (!res.next()) {
                System.out.println("Building the living table.");
                // build table
                Statement state2 = Sqlite.con.createStatement();
                state2.execute("CREATE TABLE living(livingID integer,"
                        + "inGameID integer,"
                        + "health float,"
                        + "attackDamage float,"
                        + "burnsInLight bit,"
                        + "hostility varchar(20),"
                        + "FOREIGN KEY(inGameID) REFERENCES inGame(inGameID),"
                        + "primary key(livingID));");
            }
    }

}
