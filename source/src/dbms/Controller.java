package dbms;

import dbms.SQL.Sqlite;
import dbms.tabledata.InGame;
import dbms.tabledata.Living;
import dbms.tabledata.MinecraftEntity;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Controller {
    @FXML
    public Button clear, add, search, remove, edit;
    @FXML
    public TextField entityID, inGameID, livingID, entityType, customName, posx, posy, posz, dimension, health, attackDamage, burnsInSunlight, hostility;
    @FXML
    public ComboBox comboBox;
    @FXML
    public TableColumn eid, etype, gInGameID, gEntityID, gCustomName, gPosX, gPosY, gPosZ, gDimension, lLivingID, lInGameID, lHealth, lAttackDamage, lBurnsInSunlight, lHostility;
    @FXML
    public TableView tableEntity, tableInGame, tableLiving;

    public static String currentTable = "";

    public void initialize() throws SQLException {
        rowSelect();
        searchButton();
        addButton();
        removeButton();
        editButton();

        clear.setOnAction((e) -> {
            clearAll();
        });

        comboBox.getSelectionModel().select(0);
        currentTable = "entity";
        showCurrentTable();
    }

    private void showCurrentTable() throws SQLException {
        if (currentTable.equals("entity")) {
            tableEntity.toFront();
            final ObservableList<MinecraftEntity> data = FXCollections.observableArrayList();
            ResultSet rs = Main.entitySQL.getAll();
            while (rs.next()) {
                data.add(new MinecraftEntity(rs.getString("entityID"), rs.getString("type")));
            }
            eid.setCellValueFactory(new PropertyValueFactory<MinecraftEntity, String>("id"));
            etype.setCellValueFactory(new PropertyValueFactory<MinecraftEntity, String>("type"));
            tableEntity.setItems(data);
        } else if (currentTable.equals("inGame")) {
            tableInGame.toFront();
            final ObservableList<InGame> data = FXCollections.observableArrayList();
            ResultSet rs = Main.inGameSQL.getAll();
            while (rs.next()) {
                data.add(new InGame(rs.getString("inGameID"), rs.getString("entityID"), rs.getString("customName"), rs.getString("posX"), rs.getString("posY"), rs.getString("posZ"), rs.getString("dimension")));
            }
            gInGameID.setCellValueFactory(new PropertyValueFactory<InGame, String>("gInGameID"));
            gEntityID.setCellValueFactory(new PropertyValueFactory<InGame, String>("gEntityID"));
            gCustomName.setCellValueFactory(new PropertyValueFactory<InGame, String>("gCustomName"));
            gPosX.setCellValueFactory(new PropertyValueFactory<InGame, String>("gPosX"));
            gPosY.setCellValueFactory(new PropertyValueFactory<InGame, String>("gPosY"));
            gPosZ.setCellValueFactory(new PropertyValueFactory<InGame, String>("gPosZ"));
            gDimension.setCellValueFactory(new PropertyValueFactory<InGame, String>("gDimension"));
            tableInGame.setItems(data);
        } else if (currentTable.equals("living")) {
            tableLiving.toFront();
            final ObservableList<Living> data = FXCollections.observableArrayList();
            ResultSet rs = Main.livingSQL.getAll();
            while (rs.next()) {
                data.add(new Living(rs.getString("livingID"), rs.getString("inGameID"), rs.getString("health"), rs.getString("attackDamage"), rs.getString("burnsInLight"), rs.getString("hostility")));
            }
            lLivingID.setCellValueFactory(new PropertyValueFactory<Living, String>("lLivingID"));
            lInGameID.setCellValueFactory(new PropertyValueFactory<Living, String>("lInGameID"));
            lHealth.setCellValueFactory(new PropertyValueFactory<Living, String>("lHealth"));
            lAttackDamage.setCellValueFactory(new PropertyValueFactory<Living, String>("lAttackDamage"));
            lBurnsInSunlight.setCellValueFactory(new PropertyValueFactory<Living, String>("lBurnsInSunlight"));
            lHostility.setCellValueFactory(new PropertyValueFactory<Living, String>("lHostility"));
            tableLiving.setItems(data);
        }
    }

    private void addButton() {
        add.setOnAction((btn) -> {
            String[][] fieldText = getUsedFields();
            String alertText = "";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            String table = UtilDB.checkInvalidField(fieldText);
            if (table.equals("")) {
                return;
            }

            for (int i = 0; i < fieldText.length; i++) {
                if (!fieldText[i][0].equals("")) {
                    alertText += fieldText[i][0] + "\n";
                }
            }

            alert.setContentText("Will add a new " + table + " entry with these values:\n" + alertText);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                String sqlCommand = "INSERT INTO " + table + " (";

                for (int i = 0; i < fieldText.length; i++) {
                    if (!fieldText[i][0].equals("")) {
                        sqlCommand += fieldText[i][1] + ", ";
                    }
                }
                sqlCommand += ") VALUES (";
                sqlCommand = sqlCommand.replace(", )", ")");
                for (int i = 0; i < fieldText.length; i++) {
                    if (!fieldText[i][0].equals("")) {
                        sqlCommand += "'" + fieldText[i][2] + "', ";
                    }
                }
                sqlCommand += ");";
                sqlCommand = sqlCommand.replace(", );", ");");

                try {
                    Sqlite.doCommand(sqlCommand);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setHeaderText("Failed to add.");
                    error.setContentText("Make sure there are no duplicate primary keys, and fields are filled in correctly.");
                    error.show();
                }
                try {
                    showCurrentTable();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
    }

    private void removeButton() {
        remove.setOnAction((btn) -> {
            List<String> sqlCommandList = new ArrayList<>();
            String[][] fieldText = getUsedFields();
            String alertText = "";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            String table = UtilDB.checkInvalidField(fieldText);
            if (table.equals("")) {
                return;
            }

            for (int i = 0; i < fieldText.length; i++) {
                if (!fieldText[i][0].equals("")) {
                    alertText += fieldText[i][0] + "\n";
                    sqlCommandList.add(fieldText[i][1] + "='" + fieldText[i][2] + "'");
                }
            }

            alert.setContentText("Warning: will remove all matching:\n" + alertText);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                String sqlCommand = "DELETE FROM " + table + " WHERE ";
                for (String selector : sqlCommandList) {
                    sqlCommand += selector + " AND ";
                }
                sqlCommand += ";";
                sqlCommand = sqlCommand.replace(" AND ;", ";");

                try {
                    Sqlite.doCommand(sqlCommand);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setHeaderText("Failed to remove.");
                    error.setContentText("Make sure fields are filled in correctly.");
                    error.show();
                }
                try {
                    showCurrentTable();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
    }

    private void editButton() {
        edit.setOnAction((btn) -> {
            // Check to make sure the entry is valid
            String[][] fieldList = getUsedFields();
            String table = UtilDB.checkInvalidField(fieldList);
            if (table.equals("")) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("Invalid row.");
                error.setContentText("Click on a row to auto-fill the correct values!");
                error.show();
                return;
            }

            List<TextField> textFieldList = new ArrayList<>();
            String[] text = {"entityID: ", "inGameID: ", "livingID: ", "type: ", "customName: ", "posX: ", "posY: ", "posZ: ", "dimension: ", "health: ", "attackDamage: ", "burnsInLight: ", "hostility: "};
            VBox vbox = new VBox();
            vbox.setPadding(new Insets(10));
            vbox.setSpacing(5);
            vbox.getChildren().add(new Label("Fill in the attribute you want to change."));
            vbox.getChildren().add(new Label("(Only change one, leave the rest blank!!)"));

            GridPane gridPane = new GridPane();
            gridPane.setVgap(3);
            vbox.getChildren().add(gridPane);

            for (int i = 0; i < text.length; i++) {
                TextField textField = new TextField();
                textFieldList.add(textField);
                gridPane.add(new Label(text[i]), 0, i + 1);
                gridPane.add(textField, 1, i + 1);
                text[i] = text[i].replace(": ", ""); // get attribute for sql command
            }
            Button editButton = new Button("Edit");
            gridPane.add(editButton, 1, 14);

            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            Scene dialogScene = new Scene(vbox, 300, 475);
            dialog.setScene(dialogScene);
            dialog.show();

            editButton.setOnAction((e) -> {
                String sqlCommand = "UPDATE " + currentTable + " SET ";
                for (int i = 0; i < text.length; i++) {
                    if (!textFieldList.get(i).getText().equals("")) {
                        sqlCommand += text[i] + " = '" + textFieldList.get(i).getText() + "' AND ";
                        break;
                    }
                }
                sqlCommand += "WHERE";
                sqlCommand = sqlCommand.replace(" AND WHERE", " WHERE ");
                for (int i = 0; i < fieldList.length; i++) {
                    if (!fieldList[i][0].equals("")) {
                        sqlCommand += fieldList[i][1] + "='" + fieldList[i][2] + "' AND ";
                    }
                }
                sqlCommand += ";";
                sqlCommand = sqlCommand.replace(" AND ;", ";");

                try {
                    System.out.println(sqlCommand);
                    Sqlite.doCommand(sqlCommand);
                    dialog.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setHeaderText("Failed to edit.");
                    error.setContentText("Make sure there are no duplicate primary keys, and the correct field is edited.");
                    error.show();
                    return;
                }
                try {
                    showCurrentTable();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

        });
    }

    private void searchButton() throws SQLException {
        search.setOnAction((e) -> {

            // Reset search if all fields are empty

            TextField[] tfList = {entityID, inGameID, livingID, entityType, customName, posx, posy, posz, dimension, health, attackDamage, burnsInSunlight, hostility};
            boolean reset = true;
            for (TextField tf : tfList) {
                if (!tf.getText().equals("")) {
                    reset = false;
                    break;
                }
            }
            if (reset) {
                try {
                    showCurrentTable();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                return;
            }

            // Do search

            String[][] fields = getUsedFields();
            String table = UtilDB.checkInvalidField(fields);
            String sqlCommand = "SELECT * FROM " + table + " WHERE ";
            if (table.equals("")) {
                return;
            }
            for (int i = 0; i < fields.length; i++) {
                if (!fields[i][0].equals("")) {
                    sqlCommand += fields[i][1] + "='" + fields[i][2] + "' AND ";
                }
            }
            sqlCommand += ";";
            sqlCommand = sqlCommand.replace(" AND ;", ";");

            ResultSet rs = null;
            try {
                rs = Sqlite.doCommandAndReturn(sqlCommand);
                if (table.equals("entity")) {
                    final ObservableList<MinecraftEntity> data = FXCollections.observableArrayList();
                    while (rs.next()) {
                        data.add(new MinecraftEntity(rs.getString("entityID"), rs.getString("type")));
                    }
                    eid.setCellValueFactory(new PropertyValueFactory<MinecraftEntity, String>("id"));
                    etype.setCellValueFactory(new PropertyValueFactory<MinecraftEntity, String>("type"));
                    tableEntity.setItems(data);
                } else if (table.equals("inGame")) {
                    final ObservableList<InGame> data = FXCollections.observableArrayList();
                    while (rs.next()) {
                        data.add(new InGame(rs.getString("inGameID"), rs.getString("entityID"), rs.getString("customName"), rs.getString("posX"), rs.getString("posY"), rs.getString("posZ"), rs.getString("dimension")));
                    }
                    gInGameID.setCellValueFactory(new PropertyValueFactory<InGame, String>("gInGameID"));
                    gEntityID.setCellValueFactory(new PropertyValueFactory<InGame, String>("gEntityID"));
                    gCustomName.setCellValueFactory(new PropertyValueFactory<InGame, String>("gCustomName"));
                    gPosX.setCellValueFactory(new PropertyValueFactory<InGame, String>("gPosX"));
                    gPosY.setCellValueFactory(new PropertyValueFactory<InGame, String>("gPosY"));
                    gPosZ.setCellValueFactory(new PropertyValueFactory<InGame, String>("gPosZ"));
                    gDimension.setCellValueFactory(new PropertyValueFactory<InGame, String>("gDimension"));
                    tableInGame.setItems(data);
                } else {
                    final ObservableList<Living> data = FXCollections.observableArrayList();
                    while (rs.next()) {
                        data.add(new Living(rs.getString("livingID"), rs.getString("inGameID"), rs.getString("health"), rs.getString("attackDamage"), rs.getString("burnsInLight"), rs.getString("hostility")));
                    }
                    lLivingID.setCellValueFactory(new PropertyValueFactory<Living, String>("lLivingID"));
                    lInGameID.setCellValueFactory(new PropertyValueFactory<Living, String>("lInGameID"));
                    lHealth.setCellValueFactory(new PropertyValueFactory<Living, String>("lHealth"));
                    lAttackDamage.setCellValueFactory(new PropertyValueFactory<Living, String>("lAttackDamage"));
                    lBurnsInSunlight.setCellValueFactory(new PropertyValueFactory<Living, String>("lBurnsInSunlight"));
                    lHostility.setCellValueFactory(new PropertyValueFactory<Living, String>("lHostility"));
                    tableLiving.setItems(data);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("Search failed.");
                error.setContentText("Make sure fields are filled in correctly!");
                error.show();
            }
        });


    }

    private String[][] getUsedFields() {

        String[][] fieldString = new String[13][3]; // String[ alert text ][ sqlIdentifier ][ text ]

        for (int i = 0; i < fieldString.length; i++) {
            fieldString[i][0] = "";
            fieldString[i][1] = "";
            fieldString[i][2] = "";
        }

        int i = 0;
        try {
            if (!entityID.getText().equals("")) {
                fieldString[i][0] = "EntityID: " + entityID.getText();
                fieldString[i][1] = "entityID";
                fieldString[i][2] = entityID.getText();
                i++;
            }
            if (!inGameID.getText().equals("")) {
                fieldString[i][0] = "InGameID: " + inGameID.getText();
                fieldString[i][1] = "inGameID";
                fieldString[i][2] = inGameID.getText();
                i++;
            }
            if (!livingID.getText().equals("")) {
                fieldString[i][0] = "LivingID: " + livingID.getText();
                fieldString[i][1] = "livingID";
                fieldString[i][2] = livingID.getText();
                i++;
            }
            if (!entityType.getText().equals("")) {
                fieldString[i][0] = "Type: " + entityType.getText();
                fieldString[i][1] = "type";
                fieldString[i][2] = entityType.getText();
                i++;
            }
            if (!customName.getText().equals("")) {
                fieldString[i][0] = "CustomName: " + customName.getText();
                fieldString[i][1] = "customName";
                fieldString[i][2] = customName.getText();
                i++;
            }
            if (!posx.getText().equals("")) {
                fieldString[i][0] = "posX: " + posx.getText();
                fieldString[i][1] = "posX";
                fieldString[i][2] = posx.getText();
                i++;
            }
            if (!posy.getText().equals("")) {
                fieldString[i][0] = "posY: " + posy.getText();
                fieldString[i][1] = "posY";
                fieldString[i][2] = posy.getText();
                i++;
            }
            if (!posz.getText().equals("")) {
                fieldString[i][0] = "posZ: " + posz.getText();
                fieldString[i][1] = "posZ";
                fieldString[i][2] = posz.getText();
                i++;
            }
            if (!dimension.getText().equals("")) {
                fieldString[i][0] = "Dimension: " + dimension.getText();
                fieldString[i][1] = "dimension";
                fieldString[i][2] = dimension.getText();
                i++;
            }
            if (!health.getText().equals("")) {
                fieldString[i][0] = "Health: " + health.getText();
                fieldString[i][1] = "health";
                fieldString[i][2] = health.getText();
                i++;
            }
            if (!attackDamage.getText().equals("")) {
                fieldString[i][0] = "AttackDamage: " + attackDamage.getText();
                fieldString[i][1] = "attackDamage";
                fieldString[i][2] = attackDamage.getText();
                i++;
            }
            if (!burnsInSunlight.getText().equals("")) {
                fieldString[i][0] = "burnsInLight: " + burnsInSunlight.getText();
                fieldString[i][1] = "burnsInLight";
                fieldString[i][2] = burnsInSunlight.getText();
                i++;
            }
            if (!hostility.getText().equals("")) {
                fieldString[i][0] = "Hostility: " + hostility.getText();
                fieldString[i][1] = "hostility";
                fieldString[i][2] = hostility.getText();
            }
        } catch (NullPointerException e) { }

        return fieldString;
    }

    public void handler(ActionEvent event) throws SQLException {
        if (event.getSource().toString().contains("ComboBox")) {
            ComboBox cb = (ComboBox) event.getSource();
            if (cb.getValue().toString().contains("Entity")) {
                currentTable = "entity";
            } else if (cb.getValue().toString().contains("In Game")) {
                currentTable = "inGame";
            } else if (cb.getValue().toString().contains("Living")) {
                currentTable = "living";
            }
            showCurrentTable();
        }

    }

    private void rowSelect() {
        tableEntity.getSelectionModel().selectedItemProperty().addListener((ChangeListener<MinecraftEntity>) (observable, oldValue, newValue) -> {
            clearAll();
            entityID.setText(newValue.getId());
            entityType.setText(newValue.getType());
        });
        tableInGame.getSelectionModel().selectedItemProperty().addListener((ChangeListener<InGame>) (observable, oldValue, newValue) -> {
            clearAll();
            inGameID.setText(newValue.getgInGameID());
            entityID.setText(newValue.getgEntityID());
            customName.setText(newValue.getgCustomName());
            posx.setText(newValue.getgPosX());
            posy.setText(newValue.getgPosY());
            posz.setText(newValue.getgPosZ());
            dimension.setText(newValue.getgDimension());

            //newValue.get
        });
        tableLiving.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Living>) (observable, oldValue, newValue) -> {
            clearAll();
            livingID.setText(newValue.getlLivingID());
            inGameID.setText(newValue.getlInGameID());
            health.setText(newValue.getlHealth());
            attackDamage.setText(newValue.getlAttackDamage());
            burnsInSunlight.setText(newValue.getlBurnsInSunlight());
            hostility.setText(newValue.getlHostility());
        });

    }

    private void clearAll() {
        entityID.clear();
        inGameID.clear();
        livingID.clear();
        entityType.clear();
        customName.clear();
        posx.clear();
        posy.clear();
        posz.clear();
        dimension.clear();
        health.clear();
        attackDamage.clear();
        burnsInSunlight.clear();
        hostility.clear();
    }
}
