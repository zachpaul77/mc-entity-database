package dbms.tabledata;

import javafx.beans.property.SimpleStringProperty;

public class InGame {
    private final SimpleStringProperty gInGameID;
    private final SimpleStringProperty gEntityID;
    private final SimpleStringProperty gCustomName;
    private final SimpleStringProperty gPosX;
    private final SimpleStringProperty gPosY;
    private final SimpleStringProperty gPosZ;
    private final SimpleStringProperty gDimension;

    public InGame(String _gInGameID, String _gEntityID, String _gCustomName, String _gPosX, String _gPosY, String _gPosZ, String _gDimension) {
        this.gInGameID = new SimpleStringProperty(_gInGameID);
        this.gEntityID = new SimpleStringProperty(_gEntityID);
        this.gCustomName = new SimpleStringProperty(_gCustomName);
        this.gPosX = new SimpleStringProperty(_gPosX);
        this.gPosY = new SimpleStringProperty(_gPosY);
        this.gPosZ = new SimpleStringProperty(_gPosZ);
        this.gDimension = new SimpleStringProperty(_gDimension);
    }

    public SimpleStringProperty gInGameIDProperty() { return this.gInGameID; }
    public SimpleStringProperty gEntityIDProperty() { return this.gEntityID; }
    public SimpleStringProperty gCustomNameProperty() { return this.gCustomName; }
    public SimpleStringProperty gPosXProperty() { return this.gPosX; }
    public SimpleStringProperty gPosYProperty() { return this.gPosY; }
    public SimpleStringProperty gPosZProperty() { return this.gPosZ; }
    public SimpleStringProperty gDimensionProperty() { return this.gDimension; }

    public String getgDimension() { return gDimension.get(); }
    public void setgDimension(String _gDimension) { gDimension.set(_gDimension); }

    public String getgPosZ() { return gPosZ.get(); }
    public void setgPosZ(String _gPosZ) { gPosZ.set(_gPosZ); }

    public String getgPosY() { return gPosY.get(); }
    public void setgPosY(String _gPosY) { gPosY.set(_gPosY); }

    public String getgPosX() { return gPosX.get(); }
    public void setgPosX(String _gPosX) { gPosX.set(_gPosX); }

    public String getgCustomName() { return gCustomName.get(); }
    public void setgCustomName(String _gCustomName) { gCustomName.set(_gCustomName); }

    public String getgInGameID() { return gInGameID.get(); }
    public void setgInGameID(String _gInGameID) { gInGameID.set(_gInGameID); }

    public String getgEntityID() { return gEntityID.get(); }
    public void setgEntityID(String _gEntityID) { gEntityID.set(_gEntityID); }

}
