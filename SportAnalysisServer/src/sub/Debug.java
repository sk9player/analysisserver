package sub;

public class Debug {

	/**
	 * この値以下のデバッグレベルのメッセージを表示
	 */
	public static int debug_level = 5;
	
	/**
	 * デバッグメッセージを出力
	 * 1は関数呼び出しトレース
	 * 11はサービス呼び出しトレース
	 * @param message　出力メッセージ
	 * @param level 出力レベル（数字が大きい程出力されやすい）
	 */
	public static void debug_print(String message, int level){
		if(level >= debug_level){
			System.out.println("debug:"+message);
		}
	}
}
