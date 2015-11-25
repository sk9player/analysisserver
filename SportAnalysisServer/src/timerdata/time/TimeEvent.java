package timerdata.time;

import java.util.HashMap;

import sub.Debug;

public class TimeEvent {

	//キャリブレーション用時間
	public long diff_time=0;
	public long start_time=0;
	public long goal_time=0;
	public String name="";
	
	/**
	 * ユーザのハッシュマップ
	 */
	public HashMap<String,TimeUser> user_event_map = new HashMap<String,TimeUser>();	

	public TimeUser current_user;
	
	public TimeEvent(String name){
		this.name=name;
	}
	
	/**
	 * タイムイベントのキャリブレーションデータを設定
	 * @param type
	 * @param time
	 */
	public void prepareTimeEvent(String type,long time){
		if(type.equals("start"))
			start_time=time;
		if(type.equals("goal"))
			goal_time=time;
		
		diff_time=goal_time-start_time;
		Debug.debug_print("キャリブレーション値："+diff_time,11);
	}
	
	/**
	 * ユーザを取得
	 * @param user_name
	 */
	public TimeUser getUser(String user_name){
		if(user_event_map.containsKey(user_name))
			return user_event_map.get(user_name);
		
		user_event_map.put(user_name, new TimeUser(user_name));
		return user_event_map.get(user_name); 
		
	}
	
	public long get_time(long goal_time){
		return current_user.get_time(goal_time, diff_time);
	}
}
