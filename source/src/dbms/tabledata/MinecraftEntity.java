package dbms.tabledata;

import javafx.beans.property.SimpleStringProperty;

public class MinecraftEntity {
    private final SimpleStringProperty id;
    private final SimpleStringProperty type;

    public MinecraftEntity(String _id, String _type) {
        this.id = new SimpleStringProperty(_id);
        this.type = new SimpleStringProperty(_type);
    }

    public SimpleStringProperty idProperty() { return this.id; }
    public SimpleStringProperty typeProperty() { return this.type; }

    public String getId() { return id.get(); }
    public void setId(String _id) { id.set(_id); }

    public String getType() { return type.get(); }
    public void setType(String _type) { type.set(_type); }

}
