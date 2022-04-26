package dbms;

import javafx.scene.control.Alert;

public class UtilDB {

    public static String checkInvalidField(String[][] fieldText) {
        if (Controller.currentTable.equals("entity")) {
            for (int i = 0; i < fieldText.length; i++) {
                if (!fieldText[i][0].equals("")) {
                    if (!fieldText[i][1].equals("entityID") && !fieldText[i][1].equals("type")) {
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setHeaderText("Invalid field found for this table.");
                        return "";
                    }
                }
            }
            return "entity";
        } else if (Controller.currentTable.equals("inGame")) {
            for (int i = 0; i < fieldText.length; i++) {
                if (!fieldText[i][0].equals("")) {
                    if (fieldText[i][1].contains("livingID") || fieldText[i][1].contains("health") || fieldText[i][1].contains("attackDamage") || fieldText[i][1].contains("burnsInLight") || fieldText[i][1].contains("hostility") || fieldText[i][1].contains("type")) {
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setHeaderText("Invalid field found for this table.");
                        error.show();
                        return "";
                    }
                }
            }
            return "inGame";
        } else if (Controller.currentTable.equals("living")) {
            for (int i = 0; i < fieldText.length; i++) {
                if (!fieldText[i][0].equals("")) {
                    if (!fieldText[i][1].contains("livingID") && !fieldText[i][1].contains("inGameID") && !fieldText[i][1].contains("health") && !fieldText[i][1].contains("attackDamage") && !fieldText[i][1].contains("burnsInLight") && !fieldText[i][1].contains("hostility")) {
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setHeaderText("Invalid field found for this table.");
                        error.show();
                        return "";
                    }
                }
            }
            return "living";
        }
        return "";
    }


}
