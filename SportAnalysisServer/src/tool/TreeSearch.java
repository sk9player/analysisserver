package tool;

import java.io.BufferedReader;
import java.io.File;//java����������������Ă���
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import analysis.categorize.tree.SkatingStateTree;



public class TreeSearch {

	static int debugmode = 10;
	
	public static double th0 = 5;// GPS�̒�~����
	public static double th1 = 40;// �����x�̃X�^�[�g����40~60
	public static double th2 = 50;// �����x�̋x�ݔ���
	public static double th3 = 50;// �p�x�̃R�[�i�[����
	public static double th4 = 80;// �����x�̃X�P�[�g����
	public static double th5 = 2.5;// ���g���̃N���X����
	public static double th6 = 2.5;// ���g���̃N���X����

	public static void main(String[] args) {// TODO Auto-generated method
											// stub�T�O�̓N���X�A���̓I�u�W�F�N�g

		Data data = getData();// static������Aaa.getData()���ȗ����Ă�
		
		//Data��double�̔z��ɕύX
		double[][] data_matrix = data.getDoubleMatrix();
		SkatingStateTree sst = new SkatingStateTree();
		sst.init();
		System.out.println(sst.calc(data_matrix));
	}

	private static Data getData() {
		Data data = new Data();// ���X�g�̏ꏊ���m��

		File in = new File("E:\\java���K\\test\\����e�X�g01_2.txt");// in�Ƃ����t�@�C���ɂ��ẴI�u�W�F�N�g�����܂�,\t�͓���ȈӖ��Ȃ̂�\\t�ɂ���Anew�͎��ۂɍ���Ă�
		FileReader filereader;// �ǂݍ��݂܂��N���X���m�ہA�ǂݍ��ޔ\�͂�����filereader�Ƃ����N���X�����ׂ̏ꏊ��p�ӂ��܂���
		try {
			filereader = new FileReader(in);// in�̃t�@�C����ǂݍ��݋@�\�������
			BufferedReader br = new BufferedReader(filereader);
			// filereader���g�����Ďg���Bbuffred�̓e�L�X�g��01�i�o�C�i���j�Łi�܂Ƃ߂āj�ǂ߂�A�����Ƃ����y�n��������in�̃t�@�C����ǂݍ��ދ@�\�������Ă���filereader�������Ă���buffre��reader��������
			String s;// �O�����Đ錾���̏ꏊ���m��
			while ((s = br.readLine()) != null) {// br�͓ǂݍ��ދ@�\�BreadLine�͂P�s��ǂށA���݉��s�ǂ񂾂�������A�z��łP�ڂɎ��̏ꏊ�������Ă�Anull�͂��̎��̏ꏊ�������ĂȂ�
				// System.out.println(s);
				String[] ss = s.split("\t");// �����Ƃ����ꏊ�ɂ��̂��^�u�ŋ�؂������̂�ss�ɓ���A��؂��string����Ȃ��Ƃ���
				// System.out.println(ss[0]);//java�̔z��͂O����ss[0]�ɂ̓^�C�����X�g������
				double[] fs = new double[ss.length];// �z���ss.length�̏ꏊ��錾
				for (int i = 0; i < ss.length; i++) {
					fs[i] = Double.parseDouble(ss[i]);// ������𐔎��ɕϊ�����fs�ɓ����Ass[]�ɓ����Ă���̂�fs[](�z��)�ɓ���Ă�
				}

				data.time_list.add(fs[0]);// time_list�z��H
				data.x_angularvelocity_list.add(fs[1]);
				data.y_angularvelocity_list.add(fs[2]);
				data.z_angularvelocity_list.add(fs[3]);
				data.x_acc_list.add(fs[4]);
				data.y_acc_list.add(fs[5]);
				data.z_acc_list.add(fs[6]);
				data.x_magneticfield_list.add(fs[7]);
				data.y_magneticfield_list.add(fs[8]);
				data.z_magneticfield_list.add(fs[9]);
				data.x_absolute_angularvelocity_list.add(fs[10]);
				data.y_absolute_angularvelocity_list.add(fs[11]);
				data.z_absolute_angularvelocity_list.add(fs[12]);
				data.a_quaternion_angle_list.add(fs[13]);
				data.b_quaternion_angle_list.add(fs[14]);
				data.c_quaternion_angle_list.add(fs[15]);
				data.d_quaternion_angle_list.add(fs[16]);
				data.x_angle_list.add(fs[17]);
				data.y_angle_list.add(fs[18]);
				data.z_angle_list.add(fs[19]);
				data.x_linear_acceleration_list.add(fs[20]);
				data.y_linear_acceleration_list.add(fs[21]);
				data.z_linear_acceleration_list.add(fs[22]);
				data.atmospheric_pressure_list.add(fs[23]);
				data.altitude_list.add(fs[24]);
				data.system_time_list.add(fs[25]);
				data.velocity_list.add(fs[26]);
				data.latitude_list.add(fs[27]);
				data.longitude_list.add(fs[28]);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();// �t�@�C���ɃA�N�Z�X�ł��Ȃ��G���[���o�͂��Ă����
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();// �t�@�C���̓��o�͂̃G���[���o�͂��Ă����
		}
		return data;// data�N���X��Ԃ��B�Ԃ���͈̂�����A�N���X�̒��ɂ����ς�����Ƃ��΂���
	}

}
