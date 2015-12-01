package javaFXGUI;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SettingValueTableData {
    private StringProperty name;
    private DoubleProperty value;
    private StringProperty new_value;

    public SettingValueTableData(String name, double value,String new_value) {
        this.name = new SimpleStringProperty(name);
    	this.value = new SimpleDoubleProperty(value);
        this.new_value = new SimpleStringProperty(new_value);
    }

    public StringProperty nameProperty() {
        return name;
    }
    public DoubleProperty valueProperty() {
        return value;
    }
    public StringProperty new_valueProperty() {
        return new_value;
    }

	public void setNewValue(String new_value) {
		this.new_value=new SimpleStringProperty(new_value);

	}


}
