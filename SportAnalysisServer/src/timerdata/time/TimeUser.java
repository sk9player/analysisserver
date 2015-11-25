package timerdata.time;

public class TimeUser {

	private long start_time=0;
	private boolean start_sate_flg=false;
	public long[] time_record=new long[10];
	int index=0;
	public String user_name="";
	
	
	public TimeUser(String user_name){
		this.user_name=user_name;
		
	}
	
	/**
	 * 
	 * @param start_time
	 * @return
	 */
	public int set_start_time(long start_time){
		if(this.start_time==0){
			this.start_time = start_time;
		}
		return 0;
	}

	/**
	 * 
	 * @param start_time
	 * @return
	 */
	public long get_time(long goal_time,long diff_time){
		if(this.start_time!=0){
			time_record[index] = goal_time - start_time - diff_time;
			this.start_time=0;
			System.out.println(time_record[index]+":"+goal_time+":"+start_time+":"+diff_time);
			return time_record[index];
			
			//index‰ÁZ‚Ìˆ—‚ğ’Ç‰Á‚·‚é‚±‚Æ
		}
		return 0;
	}
	
	
}
