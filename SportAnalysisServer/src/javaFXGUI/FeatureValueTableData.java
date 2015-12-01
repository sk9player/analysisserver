package javaFXGUI;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FeatureValueTableData {
    private LongProperty last_time;
    private DoubleProperty eurz_diff_ave;
    private DoubleProperty eurx_ave;
	private DoubleProperty eury_ave;
	private FloatProperty now_latitude;
	private FloatProperty now_longitude;
	private DoubleProperty move_z;
	/**
	 * 水平方向運動量（から質量を除いた値） 比較用に用いるので質量は無視する
	 */
	private double move_xy;
	/**
	 * 左右方向運動量（から質量を除いた値） 比較用に用いるので質量は無視する センサ座標系加速度から算出
	 */
	private double move_x;
	/**
	 * 行動の１周期の長さ
	 */
	private double cycle;
	/**
	 * 平均速度(m/s)/行動の１周期の長さ=ストライド？
	 */
	private double stride;

	/**
	 * 平均速度(km/h)
	 */
	private double ave_speed;

    public FeatureValueTableData(String name, double value,String new_value) {
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
