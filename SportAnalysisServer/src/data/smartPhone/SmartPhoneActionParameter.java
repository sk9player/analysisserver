package data.smartPhone;

/**
 * �^���p�����[�^�̃N���X
 * �^���f�[�^���番�͂����p�����[�^���i�[����@�\��L����
 * @author OZAKI
 *
 */
public class SmartPhoneActionParameter {

	/**
	 * �p�����[�^���擾��������
	 */
	private long last_time;	
	/**
	 * ���݈ܓx
	 */
	private float now_latitude;
	/**
	 * ���݌o�x
	 */
	private float now_longitude;
	/**
	 * �s���̂P�����̒���
	 */
	private double cycle;
	/**
	 * ���ϑ��x(m/s)/�s���̂P�����̒���=�X�g���C�h�H
	 */
	private double stride;
	/**
	 * ���ϑ��x(km/h)
	 */
	private double ave_speed;
	
	/**
	 * �R���X�g���N�^
	 */
	public SmartPhoneActionParameter(){		
	}
	
	/**
	 * �R���X�g���N�^�i�l��ݒ�j
	 * @param action_para
	 */
	public SmartPhoneActionParameter(SmartPhoneActionParameter action_para){
		last_time = action_para.getLast_time();
		now_latitude = action_para.getNow_latitude();
		now_longitude = action_para.getNow_longitude();
		cycle = action_para.getCycle();
		stride = action_para.getStride();
		ave_speed = action_para.getAve_speed();
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(getLast_time());
		sb.append("\t");
		sb.append(getNow_latitude());
		sb.append("\t");
		sb.append(getNow_longitude());
		sb.append("\t");
		sb.append(getCycle());
		sb.append("\t");
		sb.append(getAve_speed());
		sb.append("\t");
		sb.append(getStride());
		sb.append("\n");

		return sb.toString();
	}
	
	
	public float getNow_latitude() {
		return now_latitude;
	}
	public void setNow_latitude(float now_latitude) {
		this.now_latitude = now_latitude;
	}
	public float getNow_longitude() {
		return now_longitude;
	}
	public void setNow_longitude(float now_longitude) {
		this.now_longitude = now_longitude;
	}
	public double getCycle() {
		return cycle;
	}
	public void setCycle(double cycle) {
		this.cycle = cycle;
	}
	public double getAve_speed() {
		return ave_speed;
	}
	public void setAve_speed(double ave_speed) {
		this.ave_speed = ave_speed;
	}
	public double getStride() {
		return stride;
	}
	public void setStride(double stride) {
		this.stride = stride;
	}


	public long getLast_time() {
		return last_time;
	}


	public void setLast_time(long last_time) {
		this.last_time = last_time;
	}
	
}
