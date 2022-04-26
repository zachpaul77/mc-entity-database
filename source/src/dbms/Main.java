package dbms;

import dbms.SQL.Sqlite;
import dbms.SQL.EntitySQL;
import dbms.SQL.InGameSQL;
import dbms.SQL.LivingSQL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static EntitySQL entitySQL;
    public static InGameSQL inGameSQL;
    public static LivingSQL livingSQL;

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            // Connect to sql
            new Sqlite();
            entitySQL = new EntitySQL();
            inGameSQL = new InGameSQL();
            livingSQL = new LivingSQL();

        } catch (Exception e){
            e.printStackTrace();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("manage.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Databased");
        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.show();

    }

    public static void main(String[] args) { launch(args); }

}
