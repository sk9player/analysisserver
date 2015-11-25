package analysis.calc;

import setting.SettingValues;
import sub.Debug;
import analysis.calc.spectrum.FFT.FFT;

/**
 * データに対して計算処理を行うクラス
 * @author OZAKI
 *
 */
public class Calculate {

	private static double[] windowfunc_sp = new double[SettingValues.ANALYSIS_SMART_PHONE_DATA_NUM];
	private static boolean set_flg_sp = false;
	private static double[] windowfunc_s = new double[SettingValues.ANALYSIS_SENSOR_DATA_NUM];
	private static boolean set_flg_s = false;


	public static void set_window_func_sp(){
		if(set_flg_sp)
			return;

		double a = 0.995;
		windowfunc_sp[0]=0;
		windowfunc_sp[1]=0;
		windowfunc_sp[2]=2*a*windowfunc_sp[2]-a*a*windowfunc_sp[1]+1;

		for(int i=3;i<SettingValues.ANALYSIS_SMART_PHONE_DATA_NUM;i++){
			windowfunc_sp[i]=2*a*windowfunc_sp[i-1]-a*a*windowfunc_sp[i-2];
		}
		set_flg_sp=true;
	}

	public static double[] mul_window_func_sp(double[] data){
		for(int i=0;i<SettingValues.ANALYSIS_SMART_PHONE_DATA_NUM;i++){
			data[i]*=windowfunc_sp[i];
		}
		return data;
	}

	public static void set_window_func_s(){
		if(set_flg_s)
			return;

		double a = 0.999;
		windowfunc_s[0]=0;
		windowfunc_s[1]=0;
		windowfunc_s[2]=2*a*windowfunc_s[1]-a*a*windowfunc_s[0]+1;

		for(int i=3;i<SettingValues.ANALYSIS_SENSOR_DATA_NUM;i++){
			windowfunc_s[i]=2*a*windowfunc_s[i-1]-a*a*windowfunc_s[i-2];
		}
		set_flg_sp=true;
	}

	public static double[] mul_window_func_s(double[] data){
		double[] data2 = new double[SettingValues.ANALYSIS_SENSOR_DATA_NUM * SettingValues.ANALYSIS_SENSOR_DATA_MUL_NUM];
		for(int i=0;i<SettingValues.ANALYSIS_SENSOR_DATA_NUM;i++){
			data2[i]=data[i]*windowfunc_s[i];
		}
		return data2;
	}


/*------------------------------ローパスフィルター-----------------------------*/
	/**
	 * 下記の式のローパスフィルタをかける
	 * data[n-1] × mul + data[n] × ( 1 - mul )　　　　※nはデータインデックス
	 * num回繰り返す
	 *
	 * @param data
	 * @param mul
	 * @param num
	 * @return
	 */
	public static double[] pre_value_low_pass_filter(double[] data,double mul,int num){
		int loop = data.length;
		for(int i=1;i<loop;i++){
			data[i]=(data[i-1]*mul)+(data[i]*(1-mul));
		}
		return data;
	}

	/**
	 * dataの先頭からnum番目までの値に遮断周波数frequencyのデジタルローパスフィルタをかける
	 *
	 * @param data　平滑化するデータ
	 * @param frequency　遮断周波数
	 * @param num　平滑化するデータ数
	 * @return
	 */
	public static double[] digital_low_pass_filter(double[] data,double frequency,int num){

		double f = frequency / SettingValues.SENSOR_DATA_FREQUENCY;
		double w = Math.tan(Math.PI*f);
		double w2j = w*w;
		double sq2w = Math.sqrt(2)*w;
		double b = 1 + sq2w + w2j;
		double c = 2 * (1-w2j) / b;
		double d = (sq2w - w2j -1) / b;
		double orig_pre1_data = data[1];
		double orig_pre2_data = data[0];

		for(int i=2;i<num;i++){
			double temp = data[i];
			data[i] = (data[i]+2*orig_pre1_data+orig_pre2_data) * w2j / b + c * data[i-1] + d * data[i-2];
			orig_pre2_data = orig_pre1_data;
			orig_pre1_data = temp;

		}

		//逆方向平滑化
		int num_rev = num - 3;
		orig_pre1_data = data[num_rev-1];
		orig_pre2_data = data[num_rev-2];
		for(int i=num_rev;i>=0;i--){
			double temp = data[i];
			data[i] = (data[i]+2*orig_pre1_data+orig_pre2_data) * w2j / b + c * data[i+1] + d * data[i+2];
			orig_pre2_data = orig_pre1_data;
			orig_pre1_data = temp;
		}

		return data;
	}

	/**
	 * デジタルローパスフィルタの値を引くことで擬似的に実現するハイパスフィルタ
	 *
	 * @param data　平滑化するデータ
	 * @param frequency　遮断周波数
	 * @param num　平滑化するデータ数
	 * @return
	 */
	public static double[] high_pass_filter(double[] data,double frequency,int num){

		double[] filter_data = Calculate.digital_low_pass_filter(data, frequency, SettingValues.ANALYSIS_SENSOR_DATA_NUM);


		for(int i=0;i<num;i++){
			data[i]=data[i]-filter_data[i];
		}

		return data;
	}


	/**
	 * dataの先頭からnum番目までの値に遮断周波数frequencyのデジタルハイパスフィルタをかける
	 *
	 *
	 * @param data
	 * @param frequency
	 * @param num
	 * @return
	 */
	public static double[] digital_high_pass_filter(double[] data,double frequency,int num){

		double[] output=new double[num];
		double q=0.5;

		double omega = 2.0 * 3.14159265 *  frequency/SettingValues.SENSOR_DATA_FREQUENCY;
		double alpha = Math.sin(omega) / (2.0f * q);
		double a0 =   1.0 + alpha;
		double a1 =  -2.0 * Math.cos(omega);
		double a2 =   1.0 - alpha;
		double b0 =  (1.0 + Math.cos(omega)) / 2.0f;
		double b1 = -(1.0 + Math.cos(omega));
		double b2 =  (1.0 + Math.cos(omega)) / 2.0f;




		// フィルタ計算用のバッファ変数。
		double in1  = 0.0;
		double in2  = 0.0;
		double out1 = 0.0;
		double out2 = 0.0;

		// フィルタを適応
		for(int i = 0; i < num; i++)
		{
			// 入力信号にフィルタを適応し、出力信号として書き出す。
			output[i] = b0/a0 * data[i] + b1/a0 * in1  + b2/a0 * in2
			                             - a1/a0 * out1 - a2/a0 * out2;

			in2  = in1;       // 2つ前の入力信号を更新
			in1  = data[i];  // 1つ前の入力信号を更新

			out2 = out1;      // 2つ前の出力信号を更新
			out1 = output[i]; // 1つ前の出力信号を更新
		}


		return output;
	}


	/**
	 * 3点移動平均の平滑化を行う
	 *
	 * @param data
	 * @param num
	 * @return
	 */
	public static double[] numAverage_low_pass_filter(double[] data){
		int loop = data.length-1;
		data[0]=(data[1]+data[0]*2)/3;
		for(int i=1;i<loop;i++){
			data[i]=(data[i-1]+data[i]*2+data[i+1])/4;
		}
		data[loop]=(data[loop-1]+data[loop]*2)/3;
		return data;
	}

/*------------------------------平均・合計・最大・最小-----------------------------*/
	/**
	 * dataの先頭からnum番目までのデータの絶対値の合計値を取得
	 * @param data
	 * @param num
	 * @return
	 */
	public static double getAbsSum(double[] data,int num){
		double sum = 0;
		for (int i = 0; i < num; i++) {
			if(data[i]>0)
				sum+=data[i];
			else
				sum-=data[i];
		}
		return sum;
	}

	/**
	 * 以下作成中！！！！！！！！！！！！！！！！！！！！！！！！！！
	 * ドリフトが大きい為使えない？？
	 * 現在未使用
	 * 時間で割り算して、運動エネルギーの時間平均値とすること
	 *
	 * dataの先頭からnum番目までのデータの積分値の2乗を取得
	 * 加速度データ⇒運動エネルギー/重さ
	 * @param data
	 * @param num
	 * @return
	 */
	public static double getSumSquare(double[] data,int num){
		double sum = 0;
		double v = 0;
		for (int i = 0; i < num; i++) {
			for(int j = 0;j < i+1;i++){
				v+=data[j];
			}
			sum+=Math.abs(v);
		}
		return sum*sum;
	}

	/**
	 * 2つのdataの先頭からnum番目までのデータの積分値の2乗を取得
	 * 2方向加速度データ⇒運動エネルギー/重さ
	 * @param data
	 * @param num
	 * @return
	 */
	public static double getSumSquare2(double[] data,double[] data2,int num){
		double sum = 0;
		double v1 = 0;
		double v2 = 0;

		for (int i = 0; i < num; i++) {
			for(int j = 0;j < i+1;i++){
				v1+=data[j];
				v2+=data2[j];
			}
				sum+=Math.sqrt(v1*v1+v2*v2);
		}
		return sum*sum;
	}

	/**
	 * 3つのdataの先頭からnum番目までのデータの積分値の2乗を取得
	 * 3方向加速度データ⇒運動エネルギー/重さ
	 * @param data
	 * @param num
	 * @return
	 */
	public static double getSumSquare3(double[] data,double[] data2,double[] data3,int num){
		double sum = 0;
		double v1 = 0;
		double v2 = 0;
		double v3 = 0;

		for (int i = 0; i < num; i++) {
			for(int j = 0;j < i+1;i++){
				v1+=data[j];
				v2+=data2[j];
				v3+=data3[j];
			}
				sum+=Math.sqrt(Math.sqrt(v1*v1+v2*v2)+v3*v3);
		}
		return sum*sum;
	}



	/**
	 * dataの先頭からnum番目までのデータの合計値を取得
	 * @param data
	 * @param num
	 * @return
	 */
	public static double getSum(double[] data,int num){
		double sum = 0;
		for (int i = 0; i < num; i++) {
			sum+=data[i];
		}
		return sum;
	}

	/**
	 * dataの先頭からnum番目までのデータの平均値を取得
	 * @param data
	 * @param num
	 * @return
	 */
	public static double getAverage(double[] data,int num) {
		return getSum(data,num) / num;
	}

	/**
	 * dataの平均値を取得
	 * @param data
	 * @return
	 */
	public static double getAverage(double[] data) {
		return getAverage(data,data.length);
	}

	/**
	 * 最大値のインデックスを取得(ただし0は除く)
	 *
	 * @param data
	 * @return
	 */
	public static double getMaxIndex(double[] data) {
		double max = Double.NEGATIVE_INFINITY;
		int index = 0;
		int index_num = data.length / 2;
		for (int i = 1; i < index_num; i++) {
			if (max < data[i]) {
				max = data[i];
				index = i;
			}
		}
		return index;
	}

	/**
	 * 最大値のインデックスを取得(ただしfindex以下は除く)
	 *
	 * @param data
	 * @return
	 */
	public static double getMaxIndex(double[] data,int findex) {
		double max = Double.NEGATIVE_INFINITY;
		int index = 0;
		int index_num = data.length / 2;
		for (int i = findex; i < index_num; i++) {
			if (max < data[i]) {
				max = data[i];
				index = i;
			}
		}
		return index;
	}


	/**
	 * 最大値と最小値の差分を取得
	 * @param data
	 * @param start
	 * @param num
	 * @return
	 */
	public static double getRange(double[] data, int start, int num) {

		int num_max=start+num;
		double max = data[start];
		double min = data[start];
		start++;
		for(int i=start;i<num_max;i++){
			if(data[i]>max)max=data[i];
			if(data[i]<min)min=data[i];
		}

		return max-min;
	}

	/**
	 * 最大値と最小値の差分を取得
	 * @param data
	 * @return
	 */
	public static double getRange(double[] data) {
		return getRange(data,0,data.length);
	}

	/**
	 * 最大値と最小値の差分を取得
	 * @param data
	 * @return
	 */
	public static double getRange(double[] data,int num) {
		return getRange(data,0,num);
	}



	/**
	 * 最大値を取得
	 *
	 * @param data
	 * @return
	 */
	private double getMax(double[] data) {
		double max = 0;
		for (int i = 0; i < data.length; i++) {
			if (max < data[i]) {
				max = data[i];
			}
		}
		return max;
	}


/*------------------------------その他-----------------------------*/

	/**
	 * dataの先頭からnum番目までの値からdataの先頭からnum番目までの値の平均の値を減算
	 * @param data
	 * @param num
	 * @return
	 */
	public static double[] subAverage(double[] data,int num){
		Debug.debug_print("Calculate.subAverage(double[] data,int num)",1);
		double average = getAverage(data,num);
		for(int i = 0;i<num;i++)
			data[i]-=average;
		return data;
	}



	/**
	 * 作成中！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
	 * 最大値から最小値の差分の平均を出力
	 * @param data　データの配列
	 * @param num　1周期のデータ数
	 * @return
	 */
	public static double getDiffAve(double[] data,int num){
		Debug.debug_print("Calculate.getDiffAve(double[] data,int num)",1);
		double d = 0;



		return d;
	}


	/**
	 * データから周期を取得
	 *
	 * @param data
	 * @return
	 */
	public static double getCycle(double[] data) {
		Debug.debug_print("Calculate.getCycle(double[] data)",1);

		// 周波数分析を行い周波数特徴の絶対値の配列を取得
		data = FFT.fftAnalysis(data);
		Debug.debug_print("最大特徴=" + Calculate.getMaxIndex(data,10),4);
		// 最大特徴を返却
		double cycle = 1 / (Calculate.getMaxIndex(data,10) * 1000 / (SettingValues.ANALYSIS_SENSOR_DATA_NUM * SettingValues.ANALYSIS_SENSOR_DATA_MUL_NUM));
		Debug.debug_print("ピッチ(歩/秒)=" + cycle,4);

		return cycle;
	}





}
