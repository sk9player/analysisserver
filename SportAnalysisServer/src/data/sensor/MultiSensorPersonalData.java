package data.sensor;

import setting.SettingValues;
import sub.Debug;
import data.PersonalData;

/**
 * �����Z���T���̓��[�h���̃��[�U�f�[�^
 *
 * ������ASensorPersonalData���Z���T�P��MultiSensorPersonalData�Ƃ��ċ@�\��������\��iMultiSensorActionData�ɂ��Ă����l�j
 *
 * @author OZAKI
 *
 */
public class MultiSensorPersonalData extends PersonalData{

	/**
	 * �Z���T�̐�
	 */
	public int sensor_num=0;

	/**
	 * �Z���T���̃Z���T�^���f�[�^�i���Ԑ��K����j�̔z��
	 */
	public SensorActionData[][] multi_act_data_normalized_array;

	/**
	 * �Z���T���̃Z���T���Ԃ̍��i�X�}�[�g�t�H���̎��ԂƂ̍��ŎZ�o�j
	 */
	public long[] sensorDiffTimes;

	/**
	 * �^���f�[�^�i���Ԑ��K����j�i���o�[
	 */
	public int[] normalized_data_num;

	/**
	 * �^���f�[�^�i���Ԑ��K����j�̍ŐV�ʐM�i���o�[
	 */
	public int[] normalized_data_commu_num;


	/**
	 * �Z���T�^���p�����[�^
	 */
	public MultiSensorActionFeature features = new MultiSensorActionFeature();


	/**
	 * �Z���T���̉^���f�[�^�i���Ԑ��K����j������
	 */
	public int[] count_index;

	/**
	 * �^���f�[�^�i���Ԑ��K����j�o�̓e�L�X�g�C���f�b�N�X
	 */
	public int[] txt_index;

	/**
	 * ���͉�
	 */
	public int analysis_count = 0;

	/**
	 * �L�����u���[�V�����p�����Z���T�f�[�^�i�X�^�[�g�n�_�Œ������~���Čv���j
	 */
	public SensorActionData first_act_data = new SensorActionData(1);


	/**
	 * �l�f�[�^�I�u�W�F�N�g�R���X�g���N�^
	 * @param user_name ���[�U��
	 * @param act_data �^���f�[�^
	 */
	public MultiSensorPersonalData(String name,String coach_mode,int sensor_num){
		super(name,coach_mode);
		init(sensor_num);
	}

	/**
	 * �l�f�[�^�I�u�W�F�N�g�R���X�g���N�^
	 * @param user_name ���[�U��
	 * @param act_data �^���f�[�^
	 */
	public MultiSensorPersonalData(String name,int coach_mode,int sensor_num){
		super(name,Integer.toString(coach_mode));
		init(sensor_num);
	}

	private void init(int sensor_num){
		this.sensor_num=sensor_num;
		multi_act_data_normalized_array = new SensorActionData[sensor_num][SettingValues.STORE_NORMALIZED_DATA_NUM];
		count_index = new int[sensor_num];
		sensorDiffTimes = new long[sensor_num];
		normalized_data_num = new int[sensor_num];
		normalized_data_commu_num = new int[sensor_num];
		txt_index = new int[sensor_num];
	}

	/**
	 * �����Z���T�̎��ԍ������Z�o���Đݒ�
	 * @param byte_data
	 */
	public void set_sensor_diff_times(byte[] byte_data) {


		final int LONG_NUM=8;

		for(int i=0;i<byte_data.length/LONG_NUM/2;i++){
			byte[] sensor_b= new byte[LONG_NUM];
			byte[] android_b= new byte[LONG_NUM];

			System.arraycopy(byte_data, i*LONG_NUM*2, sensor_b, 0, LONG_NUM);
			System.arraycopy(byte_data, i*LONG_NUM*2+LONG_NUM, android_b, 0, LONG_NUM);

			long android_time = CalcSensorData.byte_to_long(android_b);
			Debug.debug_print("android_time:"+android_time,99);
			long sensor_time = CalcSensorData.byte_to_long(sensor_b);
			Debug.debug_print("sensor_time:"+sensor_time,99);

			sensorDiffTimes[i] = android_time-sensor_time;
		}
	}


	/**
	 * �Z���T�f�[�^�̔z���ԋp
	 *  �Z���T�f�[�^�d�l
	 *  0:���x
	 *  1:z���I�C���[�p�i�㔼�g�̌����j
	 *  2:x���I�C���[�p�i�㔼�g�̌X�΁j
	 *  3:z��(�㉺����)�����x
	 *
	 *
	 *
	 * @return
	 */
	public double[][][] get_sensor_datas() {
		double[][][] sensor_datas= new double[sensor_num][SettingValues.ANALYSIS_DATA_AXIS_NUM][SettingValues.ANALYSIS_SENSOR_DATA_NUM * SettingValues.ANALYSIS_SENSOR_DATA_MUL_NUM];

		for(int i = 0;i<sensor_num;i++){
			sensor_datas[i][0] = get_eurx_array(i);
			sensor_datas[i][1] = get_eury_array(i);
			sensor_datas[i][2] = get_eurz_array(i);

		}

		//��ƂȂ�Z���T�ԍ��ƕ��ʂɂ�镪�̓f�[�^�̐؂�o��


		return sensor_datas;
	}

	/**
	 * x���I�C���[�p�̃f�[�^�z����擾
	 * @param per_data
	 * @return
	 */
	private double[] get_eurx_array(int sensor_id){
		Debug.debug_print("CreateSensorParameterAnalysis.get_eurz_array(SensorPersonalData per_data)",1);

		// ���g�����͂��s��y�������x�f�[�^�z��
		double[] data = new double[SettingValues.ANALYSIS_SENSOR_DATA_NUM * SettingValues.ANALYSIS_SENSOR_DATA_MUL_NUM];
		// ���n��g�`�̌������Ƀf�[�^�⊮����ׂɕێ�����A
		double pre_time_data = 0.0;

		// �ŐV�ʐM�i���o�[���擾
		int late_commu_num_index = normalized_data_commu_num[sensor_id];
		// �ŐV�ʐM�i���o�[�̉^���f�[�^�̐����擾
		int late_commu_data_num_index = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
				.getMax_index() - 1;

		// ���g�����͂��s���^���f�[�^�iy�������x�j�z��ɒl���i�[
		for (int i = 0; i < SettingValues.ANALYSIS_SENSOR_DATA_NUM;) {
			// �l���i�[
			data[i] = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
					.getEulx()[late_commu_data_num_index--];
			//�l���i�[�����̂ŃC���N�������g
			i++;

			// �ʐM�i���o�[�̉^���f�[�^�����ׂĊi�[������A�P�O�̒ʐM�i���o�[�̉^���f�[�^���擾
			if (late_commu_data_num_index == 0) {
				//�O��̎��ԃf�[�^���擾
				pre_time_data = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
						.getTime()[late_commu_data_num_index];
				if(late_commu_num_index==0)
					late_commu_num_index=255;
				else
					late_commu_num_index--;
				late_commu_data_num_index = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
						.getMax_index() - 1;
				// ���n��g�`�̊Ԃ̎��ԍ����擾
				double diff_time=pre_time_data-multi_act_data_normalized_array[sensor_id][late_commu_num_index]
						.getTime()[late_commu_data_num_index];

				int last_index=i-1;
				double last_data = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
						.getEulx()[late_commu_data_num_index];
				// ���n��g�`�̊Ԃ���
				for(int j=0;j<diff_time;j++){
					if(i < SettingValues.ANALYSIS_SENSOR_DATA_NUM)break;
					data[i] = data[last_index] - (data[last_index]-last_data) * (double)((double)(j+1)/diff_time);
					//�l���i�[�����̂ŃC���N�������g
					i++;
				}
			}
		}

		return(data);
	}


	/**
	 * y���I�C���[�p�̃f�[�^�z����擾
	 * @param per_data
	 * @return
	 */
	private double[] get_eury_array(int sensor_id){
		Debug.debug_print("CreateSensorParameterAnalysis.get_eurz_array(SensorPersonalData per_data)",1);

		// ���g�����͂��s��y�������x�f�[�^�z��
		double[] data = new double[SettingValues.ANALYSIS_SENSOR_DATA_NUM * SettingValues.ANALYSIS_SENSOR_DATA_MUL_NUM];
		// ���n��g�`�̌������Ƀf�[�^�⊮����ׂɕێ�����A
		double pre_time_data = 0.0;

		// �ŐV�ʐM�i���o�[���擾
		int late_commu_num_index = normalized_data_commu_num[sensor_id];
		// �ŐV�ʐM�i���o�[�̉^���f�[�^�̐����擾
		int late_commu_data_num_index = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
				.getMax_index() - 1;

		// ���g�����͂��s���^���f�[�^�iy�������x�j�z��ɒl���i�[
		for (int i = 0; i < SettingValues.ANALYSIS_SENSOR_DATA_NUM;) {
			// �l���i�[
			data[i] = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
					.getEuly()[late_commu_data_num_index--];
			//�l���i�[�����̂ŃC���N�������g
			i++;

			// �ʐM�i���o�[�̉^���f�[�^�����ׂĊi�[������A�P�O�̒ʐM�i���o�[�̉^���f�[�^���擾
			if (late_commu_data_num_index == 0) {
				//�O��̎��ԃf�[�^���擾
				pre_time_data = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
						.getTime()[late_commu_data_num_index];
				if(late_commu_num_index==0)
					late_commu_num_index=255;
				else
					late_commu_num_index--;
				late_commu_data_num_index = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
						.getMax_index() - 1;
				// ���n��g�`�̊Ԃ̎��ԍ����擾
				double diff_time=pre_time_data-multi_act_data_normalized_array[sensor_id][late_commu_num_index]
						.getTime()[late_commu_data_num_index];

				int last_index=i-1;
				double last_data = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
						.getEuly()[late_commu_data_num_index];
				// ���n��g�`�̊Ԃ���
				for(int j=0;j<diff_time;j++){
					if(i < SettingValues.ANALYSIS_SENSOR_DATA_NUM)break;
					data[i] = data[last_index] - (data[last_index]-last_data) * (double)((double)(j+1)/diff_time);
					//�l���i�[�����̂ŃC���N�������g
					i++;
				}
			}
		}

		return(data);
	}


	private int eulz_correct_num = 0;

	/**
	 * z���I�C���[�p�̃f�[�^�z����擾
	 * @param per_data
	 * @return
	 */
	private double[] get_eurz_array(int sensor_id){
		Debug.debug_print("CreateSensorParameterAnalysis.get_eurz_array(SensorPersonalData per_data)",1);
		// ���g�����͂��s��y�������x�f�[�^�z��

		// ���g�����͂��s��y�������x�f�[�^�z��
		double[] data = new double[SettingValues.ANALYSIS_SENSOR_DATA_NUM * SettingValues.ANALYSIS_SENSOR_DATA_MUL_NUM];
		// ���n��g�`�̌������Ƀf�[�^�⊮����ׂɕێ�����A
		double pre_time_data = 0.0;

		// �ŐV�ʐM�i���o�[���擾
		int late_commu_num_index = normalized_data_commu_num[sensor_id];
		// �ŐV�ʐM�i���o�[�̉^���f�[�^�̐����擾
		int late_commu_data_num_index = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
				.getMax_index() - 1;

		// ���g�����͂��s���^���f�[�^�iy�������x�j�z��ɒl���i�[
		for (int i = 0; i < SettingValues.ANALYSIS_SENSOR_DATA_NUM;) {
			// �l���i�[
			data[i] = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
					.getEulz()[late_commu_data_num_index--]+360*eulz_correct_num;
			//�l���i�[�����̂ŃC���N�������g
			i++;

			// �ʐM�i���o�[�̉^���f�[�^�����ׂĊi�[������A�P�O�̒ʐM�i���o�[�̉^���f�[�^���擾
			if (late_commu_data_num_index == 0) {
				//�O��̎��ԃf�[�^���擾
				pre_time_data = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
						.getTime()[late_commu_data_num_index];
				if(late_commu_num_index==0)
					late_commu_num_index=255;
				else
					late_commu_num_index--;
				late_commu_data_num_index = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
						.getMax_index() - 1;
				// ���n��g�`�̊Ԃ̎��ԍ����擾
				double diff_time=pre_time_data-multi_act_data_normalized_array[sensor_id][late_commu_num_index]
						.getTime()[late_commu_data_num_index];

				int last_index=i-1;
				double last_data = multi_act_data_normalized_array[sensor_id][late_commu_num_index]
						.getEulz()[late_commu_data_num_index]+360*eulz_correct_num;
				last_data=correct_eulz(data[last_index],last_data);

				// ���n��g�`�̊Ԃ���
				for(int j=0;j<diff_time;j++){

					if(i < SettingValues.ANALYSIS_SENSOR_DATA_NUM)break;
					data[i] = data[last_index] - (data[last_index]-last_data) * (double)((double)(j+1)/diff_time);
					//�l���i�[�����̂ŃC���N�������g
					i++;
				}
			}
		}//debug
//		OutPutText.output_txt(per_data.name+"_correcteulz", data, txt_index,false);
//		eulz_correct_num=0;
		return(data);
	}

	/**
	 * �I�C���[�p��180~-180�̒��т��C��
	 * @param pre_eurz
	 * @param now_eurz
	 * @return
	 */
	private double correct_eulz(double pre_eurz,double now_eurz){
		if (pre_eurz - now_eurz > 60 && pre_eurz-360*eulz_correct_num > 0) {
			eulz_correct_num++;
			System.out.println("correct");
		} else if (pre_eurz - now_eurz < -60
				&& pre_eurz-360*eulz_correct_num < 0) {
			eulz_correct_num--;
			System.out.println("correct");
		}
		return now_eurz;
	}

	/**
	 * �ŐV�̒ʐM�i���o�[�̎��Ԑ��K���^���f�[�^����O�ɘA���������͉\�f�[�^�����J�E���g���A���͉\���ǂ�����ԋp
	 *
	 * @param per_data
	 * @return
	 */
	public boolean count_normalized_data_num() {
		Debug.debug_print("CreateSensorParameterAnalysis.count_normalized_data_num(PersonalData per_data)",1);
		int data_num = 0;


		Debug.debug_print("�ŐV�ʐM�i���o�["+normalized_data_commu_num,2);
		boolean[] enough_data_flgs=new  boolean[sensor_num];

		for(int isensor = 0;isensor<sensor_num;isensor++){
			int data_commu_num=normalized_data_commu_num[isensor];
			for (int i = data_commu_num; i >= 0; i--) {
				// �A���������͉\�ȃf�[�^���Ȃ��ꍇ�A���͂��s�킸�ɏI��
				if (multi_act_data_normalized_array[isensor][i] == null)
					return false;

				data_num += multi_act_data_normalized_array[isensor][i].getMax_index();
				if(data_num > SettingValues.ANALYSIS_SENSOR_DATA_NUM){
					enough_data_flgs[isensor]=true;
					break;
				}
			}
		}

		for (int isensor = 0;isensor<sensor_num;isensor++){
			if(!enough_data_flgs[isensor])
				return false;
		}
		return true;
	}


}
