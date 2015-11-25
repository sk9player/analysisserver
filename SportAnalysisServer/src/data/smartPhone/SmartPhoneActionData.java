package data.smartPhone;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import data.ActionData;

import setting.SettingValues;
import sub.Base64;
import sub.Debug;

/**
 * �^���f�[�^�̃N���X
 * �e��f�[�^�Ƃ��̊i�[�@�\��L����
 * @author OZAKI
 *
 */
public class SmartPhoneActionData extends ActionData{
	
	/**
	 * ����
	 */
	private long[] time;
	/**
	 * x�������x
	 */
	private int[] accx;
	/**
	 * y�������x
	 */
	private int[] accy;
	/**
	 * z�������x
	 */
	private int[] accz;
	/**
	 * ����
	 */
	private int[] argmuki;
	/**
	 * �c�����̊p�x
	 */
	private int[] argtate;
	/**
	 * �������̊p�x
	 */
	private int[] argyoko;
	/**
	 * ���x
	 */
	private int[] speed;
	/**
	 * �o�x
	 */
	private float[] longitude;
	/**
	 * �ܓx
	 */
	private float[] latitude;
	
	/**
	 * �f�[�^���i�e�f�[�^�̊i�[���jdata_num�@��ActionData�I�u�W�F�N�g�𐶐�
	 * 
	 * @param data_num
	 */
	public SmartPhoneActionData(int data_num) {
		Debug.debug_print("ActionData(int data_num)",1);
		max_index = data_num;
		time = new long[max_index];
		speed = new int[max_index];
		latitude = new float[max_index];
		longitude = new float[max_index];
		accx = new int[max_index];
		accy = new int[max_index];
		accz = new int[max_index];
		argmuki = new int[max_index];
		argtate = new int[max_index];
		argyoko = new int[max_index];
	}

	/**
	 * Base64�̃f�[�^�����̃I�u�W�F�N�g�ɑ}��
	 * 
	 * @param data
	 * @throws IOException
	 * @throws Base64DecodingException
	 */
	public void setData(String data) throws IOException {
		Debug.debug_print("ActionData.setData(String data)",1);
		byte[] byte_data = Base64.decode(data, Base64.URL_SAFE);
		for (int i = 0; i < byte_data.length-1;) {
			
			
			byte[] time_b = new byte[8];
			time_b[0]=0;
			time_b[1]=0;
			for(int j=2;j<8;j++){
				time_b[j] = byte_data[i++];
			}
			
			DataInputStream in = new DataInputStream(new ByteArrayInputStream(time_b));
	        try {
	        	time[index]=in.readLong();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			speed[index] = (int) byte_data[i++];
			byte b1[] = {byte_data[i++],byte_data[i++],byte_data[i++],byte_data[i++]};
			latitude[index] = byte_to_float(b1);
			byte b2[] = {byte_data[i++],byte_data[i++],byte_data[i++],byte_data[i++]};
			longitude[index] = byte_to_float(b2);	
			accx[index] = (int) byte_data[i++] * 128 + (int) byte_data[i++];
			accy[index] = (int) byte_data[i++] * 128 + (int) byte_data[i++];
			accz[index] = (int) byte_data[i++] * 128 + (int) byte_data[i++];
			argmuki[index] = (int) byte_data[i++] * 128
					+ (int) byte_data[i++];
			argtate[index] = (int) byte_data[i++];
			argyoko[index] = (int) byte_data[i++] * 128
					+ (int) byte_data[i++];
			index++;

		}
		if(byte_data[byte_data.length-1]>=0){
			commu_index = byte_data[byte_data.length-1];
		}else{
			commu_index = byte_data[byte_data.length-1]+256;
		}
		Debug.debug_print("�ʐM�i���o�[="+commu_index,99);

	}

	private float byte_to_float(byte[] b){
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.put(b);
		buffer.flip();
        return Float.intBitsToFloat(buffer.getInt());
	}
	
	
	/**
	 * ���̃A�N�V�����f�[�^�����̃I�u�W�F�N�g�ɑ}��
	 * 
	 * @param act_data
	 */
	public void setData(SmartPhoneActionData act_data) {
		Debug.debug_print("ActionData.setData(ActionData act_data)",1);
		for (int i = 0; i < act_data.getMax_index(); i ++) {
			time[index] = act_data.getTime()[i];
			speed[index] = act_data.getSpeed()[i];
			latitude[index] = act_data.getLatitude()[i];
			longitude[index] = act_data.getLongitude()[i];			
			accx[index] = act_data.getAccx()[i];
			accy[index] = act_data.getAccy()[i];
			accz[index] = act_data.getAccz()[i];
			argmuki[index] = act_data.getArgmuki()[i];
			argtate[index] = act_data.getArgtate()[i];
			argyoko[index] = act_data.getArgyoko()[i];
			index++;
		}
	}
	
	/**
	 * ���̃A�N�V�����f�[�^�����n���Ԃ��Ă��̃I�u�W�F�N�g�ɑ}��
	 * 
	 * @param act_data
	 */
	public void normalizeData(SmartPhoneActionData act_data) {
		Debug.debug_print("normalizeData(SmartPhoneActionData act_data)",1);
		//���[�v��
		int orig_index = 0;
		
		//���Ԃ�������������
		int same_time_count = 1;

		//���`�ۊǂׂ̈̕ۊǎ��Ԃ̃J�E���g
		int skip_count = 0;
		
		//����͂��̂܂ܒl��}��
		if(index==0){
			time[index] = act_data.getTime()[orig_index];
			speed[index] = act_data.getSpeed()[orig_index];
			latitude[index] = act_data.getLatitude()[orig_index];
			longitude[index] = act_data.getLongitude()[orig_index];			
			accx[index] = act_data.getAccx()[orig_index];
			accy[index] = act_data.getAccy()[orig_index];
			accz[index] = act_data.getAccz()[orig_index];
			argmuki[index] = act_data.getArgmuki()[orig_index];
			argtate[index] = act_data.getArgtate()[orig_index];
			argyoko[index] = act_data.getArgyoko()[orig_index];
			index++;
			orig_index++;
		}
		
			
		for (; orig_index < act_data.getMax_index()&&index < max_index;) {
			int pre_index = index-1;
			
			//����̎��Ԃ̒l���O��̎��Ԃ̒l�Ɠ����������ꍇ�A���ς̒l��ݒ�
			//���f�[�^�̃J�E���g���C���N�������g
			if(time[pre_index]==act_data.getTime()[orig_index]){
				same_time_count++;
				speed[pre_index] = (speed[pre_index]*(same_time_count-1) + act_data.getSpeed()[orig_index])/same_time_count;
				latitude[pre_index] = (latitude[pre_index]*(same_time_count-1) + act_data.getLatitude()[orig_index])/same_time_count;
				longitude[pre_index] = (longitude[pre_index]*(same_time_count-1) + act_data.getLongitude()[orig_index])/same_time_count;			
				accx[pre_index] = (accx[pre_index]*(same_time_count-1) + act_data.getAccx()[orig_index])/same_time_count;
				accy[pre_index] = (accy[pre_index]*(same_time_count-1) + act_data.getAccy()[orig_index])/same_time_count;
				accz[pre_index] = (accz[pre_index]*(same_time_count-1) + act_data.getAccz()[orig_index])/same_time_count;
				argmuki[pre_index] = (argmuki[pre_index]*(same_time_count-1) + act_data.getArgmuki()[orig_index])/same_time_count;
				argtate[pre_index] = (argtate[pre_index]*(same_time_count-1) + act_data.getArgtate()[orig_index])/same_time_count;
				argyoko[pre_index] = (argyoko[pre_index]*(same_time_count-1) + act_data.getArgyoko()[orig_index])/same_time_count;
				orig_index++;
				
			//����̎��Ԃ̒l���O��̎���+1�������ꍇ�A����̉^���f�[�^��ݒ�
			//���f�[�^�ƕۊǌ�f�[�^�̃J�E���g���C���N�������g
			}else if(time[pre_index]+1==act_data.getTime()[orig_index]){
				same_time_count=1;
				time[index] = act_data.getTime()[orig_index];
				speed[index] = act_data.getSpeed()[orig_index];
				latitude[index] = act_data.getLatitude()[orig_index];
				longitude[index] = act_data.getLongitude()[orig_index];			
				accx[index] = act_data.getAccx()[orig_index];
				accy[index] = act_data.getAccy()[orig_index];
				accz[index] = act_data.getAccz()[orig_index];
				argmuki[index] = act_data.getArgmuki()[orig_index];
				argtate[index] = act_data.getArgtate()[orig_index];
				argyoko[index] = act_data.getArgyoko()[orig_index];
				
				//�X�L�b�v�����l��⊮
				for (int j= 0;j < skip_count;j++){
					//�ۊǂ��鎞�Ԃ̒l���擾
					int comp_index = index-skip_count+j;
					float comp_val_coef = (float)(j + 1) / (float)(skip_count + 1);
					//�f�[�^��⊮
					speed[comp_index] = (int) (act_data.getSpeed()[orig_index-1] + ((act_data.getSpeed()[orig_index] - act_data.getSpeed()[orig_index-1]) * comp_val_coef));
					latitude[comp_index] = act_data.getLatitude()[orig_index-1] + ((act_data.getLatitude()[orig_index] - act_data.getLatitude()[orig_index-1]) * comp_val_coef);
					longitude[comp_index] = act_data.getLongitude()[orig_index-1] + ((act_data.getLongitude()[orig_index] - act_data.getLongitude()[orig_index-1]) * comp_val_coef);
					accx[comp_index] = (int) (act_data.getAccx()[orig_index-1] + ((act_data.getAccx()[orig_index] - act_data.getAccx()[orig_index-1]) * comp_val_coef));
					accy[comp_index] = (int) (act_data.getAccy()[orig_index-1] + ((act_data.getAccy()[orig_index] - act_data.getAccy()[orig_index-1]) * comp_val_coef));
					accz[comp_index] = (int) (act_data.getAccz()[orig_index-1] + ((act_data.getAccz()[orig_index] - act_data.getAccz()[orig_index-1]) * comp_val_coef));
					argmuki[comp_index] = (int) (act_data.getArgmuki()[orig_index-1] + ((act_data.getArgmuki()[orig_index] - act_data.getArgmuki()[orig_index-1]) * comp_val_coef));
					argtate[comp_index] = (int) (act_data.getArgtate()[orig_index-1] + ((act_data.getArgtate()[orig_index] - act_data.getArgtate()[orig_index-1]) * comp_val_coef));
					argyoko[comp_index] = (int) (act_data.getArgyoko()[orig_index-1] + ((act_data.getArgyoko()[orig_index] - act_data.getArgyoko()[orig_index-1]) * comp_val_coef));
				}
				
				index++;
				orig_index++;
				// ��Ԃ��s�����̂ŃX�L�b�v�J�E���g�����Z�b�g
				skip_count=0;
	
			//����̎��Ԃ̒l���O��̎���+1�ł͂Ȃ������ꍇ�A	
			}else if((time[pre_index]+1)!=act_data.getTime()[orig_index]){
				same_time_count=1;
				time[index] = time[pre_index]+1;
				skip_count++;
				index++;
			}
		}
	}	
	
	/**
	 * �f�[�^�𕶎���ɂ��ĕԋp�i�e�L�X�g�o�͗p�j
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i<index;i++){
			sb.append(time[i]);
			sb.append("\t");
			sb.append(latitude[i]);
			sb.append("\t");
			sb.append(longitude[i]);
			sb.append("\t");
			sb.append(speed[i]);
			sb.append("\t");
			sb.append(accx[i]);
			sb.append("\t");
			sb.append(accy[i]);
			sb.append("\t");
			sb.append(accz[i]);
			sb.append("\t");
			sb.append(argmuki[i]);
			sb.append("\t");
			sb.append(argtate[i]);
			sb.append("\t");
			sb.append(argyoko[i]);
			sb.append("\n");			
		}
		return sb.toString();
	}

	/**
	 * ���݂̈ܓx��ԋp
	 * @return
	 */
	public float getNowLatitude() {
		return latitude[index-1];
	}	
	/**
	 * ���݂̌o�x��ԋp
	 * @return
	 */
	public float getNowLongitude() {
		return longitude[index-1];
	}	

	public long[] getTime() {
		return time;
	}

	public void setTime(long[] time) {
		this.time = time;
	}

	public int[] getAccx() {
		return accx;
	}

	public void setAccx(int[] accx) {
		this.accx = accx;
	}

	public int[] getAccy() {
		return accy;
	}

	public void setAccy(int[] accy) {
		this.accy = accy;
	}

	public int[] getAccz() {
		return accz;
	}

	public void setAccz(int[] accz) {
		this.accz = accz;
	}

	public int[] getArgmuki() {
		return argmuki;
	}

	public void setArgmuki(int[] argmuki) {
		this.argmuki = argmuki;
	}

	public int[] getArgtate() {
		return argtate;
	}

	public void setArgtate(int[] argtate) {
		this.argtate = argtate;
	}

	public int[] getArgyoko() {
		return argyoko;
	}

	public void setArgyoko(int[] argyoko) {
		this.argyoko = argyoko;
	}

	public int[] getSpeed() {
		return speed;
	}

	public void setSpeed(int[] speed) {
		this.speed = speed;
	}

	public float[] getLongitude() {
		return longitude;
	}

	public void setLongitude(float[] longitude) {
		this.longitude = longitude;
	}

	public float[] getLatitude() {
		return latitude;
	}

	public void setLatitude(float[] latitude) {
		this.latitude = latitude;
	}

}