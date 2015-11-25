package data;


/**
 * 運動データのクラス
 * 各種データとその格納機能を有する
 * @author OZAKI
 *
 */
public class ActionData {

	/**
	 * 通信ナンバー
	 */
	protected int commu_index = 0;

	/**
	 * 格納されているデータ数
	 */
	protected int index = 0;

	/**
	 * 格納できるデータ数
	 */
	protected int max_index = 0;



	public int getMax_index() {
		return max_index;
	}

	public void setMax_index(int max_index) {
		this.max_index = max_index;
	}

	public int getIndex() {
		return index;
	}

	public void incrementIndex() {
		index++;
	}

	public void incrementIndex(int i) {
		index+=i;
	}

	public int getCommu_index() {
		return commu_index;
	}

	public void setCommu_index(int commu_index) {
		this.commu_index = commu_index;
	}
}