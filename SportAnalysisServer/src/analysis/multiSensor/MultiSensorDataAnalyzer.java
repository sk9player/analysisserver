package analysis.multiSensor;

import data.multiSensor.MultiSensorPersonalData;

public interface MultiSensorDataAnalyzer {
	public void analysis(MultiSensorPersonalData per_data,double[][][] sensor_datas);

}
