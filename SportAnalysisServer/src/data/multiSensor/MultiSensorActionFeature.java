package data.multiSensor;

/**
 * �^���p�����[�^�̃N���X �^���f�[�^���番�͂����p�����[�^���i�[����@�\��L����
 * 
 * @author OZAKI
 * 
 */
public class MultiSensorActionFeature {

	/**
	 * �����̐�
	 */
	public static final int FEATURE_NUM=6;
	/**
	 * ���ϒl�̃C���f�b�N�X
	 */
	public static final int AVERAGE_FEATURE_INDEX=0;
	/**
	 * �ő�l�̃C���f�b�N�X
	 */
	public static final int MAX_FEATURE_INDEX=1;
	/**
	 * �ŏ��l�̃C���f�b�N�X
	 */
	public static final int MIN_FEATURE_INDEX=2;
	/**
	 * �l��i�ő�-�ŏ��j�̃C���f�b�N�X
	 */
	public static final int RANGE_FEATURE_INDEX=3;
	/**
	 * �g���i1�����̎��ԁj�̃C���f�b�N�X
	 */
	public static final int FREQ_FEATURE_INDEX=4;
	/**
	 * �ʑ��̃C���f�b�N�X
	 */
	public static final int PHASE_FEATURE_INDEX=5;
	
	/**
	 * �Z���T�̐�
	 */
	private int sensor_num=0;
	
	/**
	 * �����l
	 */
	private double[][] feature_data;
	

	/**
	 * �R���X�g���N�^
	 */
	public MultiSensorActionFeature(int sensor_num) {
		this.sensor_num=sensor_num;
		feature_data=new double[sensor_num][FEATURE_NUM];
	}

	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int isensor=0;isensor<sensor_num;isensor++){
			for(int ifeature=0;ifeature<FEATURE_NUM;ifeature++){
				sb.append(feature_data[isensor][ifeature]);
				sb.append("\t");
			}
		}

		return sb.toString();
	}


	public double[][] getAllFeature_data() {
		return feature_data;
	}


	public void setAllFeature_data(double[][] feature_data) {
		this.feature_data = feature_data;
	}

	/**
	 * 
	 * @param sensor
	 * @param feature_index
	 * @return
	 */
	public double getFeature(int sensor,int feature_index) {
		return feature_data[sensor][feature_index];
	}


	/**
	 * 
	 * @param feature_data
	 * @param sensor
	 * @param feature_index
	 */
	public void setFeature(double feature,int sensor,int feature_index) {
		this.feature_data[sensor][feature_index] = feature;
	}


	
}
