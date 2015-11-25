package timerdata.time;

import java.util.HashMap;


public class TimeManager {
	
	
	/**
	 * �^�C���v���C�x���g�̃n�b�V���}�b�v
	 */
	public static HashMap<String,TimeEvent> time_event_map = new HashMap<String,TimeEvent>();	


	/**
	 * �^�C���v��
	 * @param name
	 * @return true:�����@false:�쐬�ς�
	 */
	public static boolean createTimeEvent(String name){
		if(time_event_map.containsKey(name))
			return false;
		
		time_event_map.put(name, new TimeEvent(name));
		return true; 
	}
	
	
	
}
