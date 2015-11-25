package data.sensor;

import java.nio.ByteBuffer;

public class CalcSensorData {
	/**
	 * Converts received 32-bit word to float values by LP research
	 * 
	 * @param offset
	 * @param buffer
	 * @return
	 */
	public static float convertRxbytesToFloat(int offset, byte buffer[]) {
		int v = 0;
		byte[] t = new byte[4];

		for (int i = 0; i < 4; i++) {
			t[3 - i] = buffer[i + offset];
		}

		return Float.intBitsToFloat(ByteBuffer.wrap(t).getInt(0));
	}

	/**
	 * �o�C�g�z���float�^�ɕϊ�
	 * 
	 * @param b
	 * @return
	 */
	public static float byte_to_float(byte[] b) {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.put(b);
		buffer.flip();
		return Float.intBitsToFloat(buffer.getInt());
	}

	/**
	 * �I�C���[�p��180~-180�̒��т��C��
	 * @param pre_eurz
	 * @param now_eurz
	 * @return
	 */
	public static float correct_eulz(float pre_eurz,float now_eurz){
		if (pre_eurz - now_eurz > 60 && pre_eurz > 0) {
			now_eurz = now_eurz + 360;
		} else if (pre_eurz - now_eurz < -60
				&& pre_eurz < 0) {
			now_eurz = now_eurz - 360;
		}
		
		
		return now_eurz;
	}
	
	/**
	 * �o�C�g�z���long�^�ɕϊ�
	 * 
	 * @param b
	 * @return
	 */
	public static long byte_to_long(byte[] b) {
		ByteBuffer buf = ByteBuffer.wrap(b);
		return buf.getLong();
	}
	
	
}
