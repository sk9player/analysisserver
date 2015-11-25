package sub;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class OutPutText {
	/**
	 * テキスト出力
	 * @param file_name　出力ファイル名
	 * @param txt　出力する文字列
	 * @param index　ファイル名につけるインデックス番号
	 * @param update_flg　ファイルに上書きする(true)か、新しくファイルを作り直すか(false)のフラグ
	 * @return
	 */
	public static boolean output_txt(String file_name,String txt,int index,boolean update_flg){
		//出力ファイル名を設定（時間なども設定するようにすること！！！！！！！！！）
		DecimalFormat dformat = new DecimalFormat("0000");


		File file = new File("C:/Users/honlab/Desktop/server_data/"+file_name+dformat.format(index)+".log");
		try {
			FileWriter filewriter = new FileWriter(file,update_flg);
			filewriter.write(txt);
			filewriter.flush();
			filewriter.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * テキスト出力
	 * @param file_name　出力ファイル名
	 * @param data　出力するデータ
	 * @param index　ファイル名につけるインデックス番号
	 * @param update_flg　ファイルに上書きする(true)か、新しくファイルを作り直す(false)かのフラグ
	 * @return
	 */
	public static boolean output_txt(String file_name,double[] data,int index,boolean update_flg){

		//出力ファイル名を設定（時間なども設定するようにすること！！！！！！！！！）
		File file = new File("C:/Users/honlab/Desktop/server_data/"+file_name+index+".log");
		try {
			FileWriter filewriter = new FileWriter(file,update_flg);
			filewriter.write(double_array_to_string(data));
			filewriter.flush();
			filewriter.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * テキスト出力
	 * @param file_name　出力ファイル名
	 * @param data　出力するデータ
	 * @param index　ファイル名につけるインデックス番号
	 * @param update_flg　ファイルに上書きする(true)か、新しくファイルを作り直す(false)かのフラグ
	 * @return
	 */
	public static boolean output_txt(String file_name,long[] data,int index,boolean update_flg){

		//出力ファイル名を設定（時間なども設定するようにすること！！！！！！！！！）
		File file = new File("C:/Users/honlab/Desktop/server_data/"+file_name+index+".log");
		try {
			FileWriter filewriter = new FileWriter(file,update_flg);
			filewriter.write(long_array_to_string(data));
			filewriter.flush();
			filewriter.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}


	private static String double_array_to_string(double[] data){
		StringBuilder sb = new StringBuilder();
		int length = data.length;
		for(int i = 0;i<length;i++){
			sb.append(data[i]);
			sb.append("\n");
		}

		return sb.toString();

	}

	private static String long_array_to_string(long[] data){
		StringBuilder sb = new StringBuilder();
		int length = data.length;
		for(int i = 0;i<length;i++){
			sb.append(data[i]);
			sb.append("\n");
		}

		return sb.toString();

	}

}
