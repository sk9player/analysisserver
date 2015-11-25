package data.sensor;

import setting.ModeValues;

/**
 * �^���p�����[�^�̃N���X �^���f�[�^���番�͂����p�����[�^���i�[����@�\��L����
 *
 * @author OZAKI
 *
 */
public class SensorActionParameter {


	/**
	 * �������(�����͕��͎��s���)
	 */
	private int state=ModeValues.FAIL_STATE;

	/**
	 * �p�����[�^���擾��������
	 */
	private long last_time;

	/**
	 * z���I�C���[�p�i�㔼�g�̌����j�̍ő�l����ŏ��l�̍����̕���
	 */
	private double eurz_diff_ave;
	/**
	 * x���I�C���[�p�i�㔼�g�̌X�΁j�̕���
	 */
	private double eurx_ave;
	/**
	 * y���I�C���[�p�i�㔼�g�̍��E�X�΁j�̕���
	 */
	private double eury_ave;

	/**
	 * ���݈ܓx
	 */
	private float now_latitude;
	/**
	 * ���݌o�x
	 */
	private float now_longitude;

	/**
	 * z�������^���ʁi���玿�ʂ��������l�j ��r�p�ɗp����̂Ŏ��ʂ͖�������
	 */
	private double move_z;
	/**
	 * ���������^���ʁi���玿�ʂ��������l�j ��r�p�ɗp����̂Ŏ��ʂ͖�������
	 */
	private double move_xy;
	/**
	 * ���E�����^���ʁi���玿�ʂ��������l�j ��r�p�ɗp����̂Ŏ��ʂ͖������� �Z���T���W�n�����x����Z�o
	 */
	private double move_x;
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
	public SensorActionParameter() {
	}

	/**
	 * �R���X�g���N�^�i�l��ݒ�j
	 *
	 * @param action_para
	 */
	public SensorActionParameter(SensorActionParameter action_para) {
		last_time = action_para.getLast_time();
		cycle = action_para.getCycle();
		eurz_diff_ave = action_para.getEurz_diff_ave();
		eurx_ave = action_para.getEurx_ave();
		eury_ave = action_para.getEury_ave();
		ave_speed = action_para.getAve_speed();
		move_z = action_para.getMove_z();
		move_xy = action_para.getMove_xy();
		now_latitude = action_para.getNow_latitude();
		now_longitude = action_para.getNow_longitude();
	}

	/**
	 * �l��0�Ƀ��Z�b�g
	 */
	public void resetParamater(){
		cycle = 0;
		eurz_diff_ave = 0;
		eurx_ave = 0;
		eury_ave = 0;
		ave_speed = 0;
		move_z = 0;
		move_xy = 0;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(last_time);
		sb.append("\t");
		sb.append(cycle);
		sb.append("\t");
		sb.append(eurz_diff_ave);
		sb.append("\t");
		sb.append(eurx_ave);
		sb.append("\t");
		sb.append(eury_ave);
		sb.append("\t");
		sb.append(ave_speed);
		sb.append("\t");
		sb.append(now_latitude);
		sb.append("\t");
		sb.append(now_longitude);
		sb.append("\t");

		return sb.toString();
	}

	/**
	 *
	 * @return
	 */
	public String toComString() {
		StringBuilder sb = new StringBuilder();
		sb.append(last_time);
		sb.append(",");
		sb.append(String.format("%.2f", cycle));
		sb.append(",");
		sb.append(String.format("%.2f",eurz_diff_ave));
		sb.append(",");
		sb.append(String.format("%.2f",eurx_ave));
		sb.append(",");
		sb.append(String.format("%.2f",eury_ave));
		sb.append(",");
		sb.append(ave_speed);
		sb.append(",");
		sb.append(String.format("%.2f",move_z));
		sb.append(",");
		sb.append(String.format("%.2f",move_xy));
		sb.append(",");
		sb.append(String.format("%.2f",move_x));
		sb.append(",");
		sb.append(now_latitude);
		sb.append(",");
		sb.append(now_longitude);

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

	public double getEurz_diff_ave() {
		return eurz_diff_ave;
	}

	public void setEurz_diff_ave(double eurz_diff_ave) {
		this.eurz_diff_ave = eurz_diff_ave;
	}

	public double getEurx_ave() {
		return eurx_ave;
	}

	public void setEurx_ave(double eurx_ave) {
		this.eurx_ave = eurx_ave;
	}

	public long getLast_time() {
		return last_time;
	}

	public void setLast_time(long last_time) {
		this.last_time = last_time;
	}

	public double getMove_z() {
		return move_z;
	}

	public void setMove_z(double move_z) {
		this.move_z = move_z;
	}

	public double getMove_xy() {
		return move_xy;
	}

	public void setMove_xy(double move_xy) {
		this.move_xy = move_xy;
	}

	public double getMove_x() {
		return move_x;
	}

	public void setMove_x(double move_x) {
		this.move_x = move_x;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public double getEury_ave() {
		return eury_ave;
	}

	public void setEury_ave(double eury_ave) {
		this.eury_ave = eury_ave;
	}

}
