package dbms.tabledata;

import javafx.beans.property.SimpleStringProperty;

public class Living {
    private final SimpleStringProperty lLivingID;
    private final SimpleStringProperty lInGameID;
    private final SimpleStringProperty lHealth;
    private final SimpleStringProperty lAttackDamage;
    private final SimpleStringProperty lBurnsInSunlight;
    private final SimpleStringProperty lHostility;

    public Living(String _lLivingID, String _lInGameID, String _lHealth, String _lAttackDamage, String _lBurnsInSunlight, String _lHostility) {
        this.lLivingID = new SimpleStringProperty(_lLivingID);
        this.lInGameID = new SimpleStringProperty(_lInGameID);
        this.lHealth = new SimpleStringProperty(_lHealth);
        this.lAttackDamage = new SimpleStringProperty(_lAttackDamage);
        this.lBurnsInSunlight = new SimpleStringProperty(_lBurnsInSunlight);
        this.lHostility = new SimpleStringProperty(_lHostility);
    }

    public SimpleStringProperty lLivingIDProperty() { return this.lLivingID; }
    public SimpleStringProperty lInGameIDProperty() { return this.lInGameID; }
    public SimpleStringProperty lHealthProperty() { return this.lHealth; }
    public SimpleStringProperty lAttackDamageProperty() { return this.lAttackDamage; }
    public SimpleStringProperty lBurnsInSunlightProperty() { return this.lBurnsInSunlight; }
    public SimpleStringProperty lHostilityProperty() { return this.lHostility; }

    public String getlHostility() { return lHostility.get(); }
    public void setlHostility(String _lHostility) { lHostility.set(_lHostility); }

    public String getlBurnsInSunlight() { return lBurnsInSunlight.get(); }
    public void setlBurnsInSunlight(String _lBurnsInSunlight) { lBurnsInSunlight.set(_lBurnsInSunlight); }

    public String getlAttackDamage() { return lAttackDamage.get(); }
    public void setlAttackDamage(String _lAttackDamage) { lAttackDamage.set(_lAttackDamage); }

    public String getlHealth() { return lHealth.get(); }
    public void setlHealth(String _lHealth) { lInGameID.set(_lHealth); }

    public String getlInGameID() { return lInGameID.get(); }
    public void setlInGameID(String _lInGameID) { lInGameID.set(_lInGameID); }

    public String getlLivingID() { return lLivingID.get(); }
    public void setlLivingID(String _lLivingID) { lLivingID.set(_lLivingID); }

}
