package sub;

public class Debug {

	/**
	 * ���̒l�ȉ��̃f�o�b�O���x���̃��b�Z�[�W��\��
	 */
	public static int debug_level = 5;
	
	/**
	 * �f�o�b�O���b�Z�[�W���o��
	 * 1�͊֐��Ăяo���g���[�X
	 * 11�̓T�[�r�X�Ăяo���g���[�X
	 * @param message�@�o�̓��b�Z�[�W
	 * @param level �o�̓��x���i�������傫�����o�͂���₷���j
	 */
	public static void debug_print(String message, int level){
		if(level >= debug_level){
			System.out.println("debug:"+message);
		}
	}
}
