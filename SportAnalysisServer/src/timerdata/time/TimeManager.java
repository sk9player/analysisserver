package timerdata.time;

import java.util.HashMap;


public class TimeManager {
	
	
	/**
	 * タイム計測イベントのハッシュマップ
	 */
	public static HashMap<String,TimeEvent> time_event_map = new HashMap<String,TimeEvent>();	


	/**
	 * タイム計測
	 * @param name
	 * @return true:成功　false:作成済み
	 */
	public static boolean createTimeEvent(String name){
		if(time_event_map.containsKey(name))
			return false;
		
		time_event_map.put(name, new TimeEvent(name));
		return true; 
	}
	
	
	
}
