package graph;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class SwingGraph extends JComponent {
	

	/**
	 * シリアル番号
	 */
	private static final long serialVersionUID = 1L;
	
	private int value_rate;
	
	int value[] = new int[GraphManager.GraphWidth];
	int null_value[] = new int[GraphManager.GraphWidth];
	int index = 0;

	public SwingGraph(String title,int width, int height, int rate) {
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// クライアントエリアに、このコンポーネントを貼り付ける
		frame.getContentPane().add(this);
		frame.setSize(width, height);
		frame.setVisible(true);
		value_rate = rate;
	}

	public void addData(String data) {
		value[index] = Integer.parseInt(data);
		index++;
		repaint();
	}

	/**
	 * グラフにデータ（文字列型）を追加
	 * @param data
	 */	
	public void addData(String[] data) {
		int[] int_data = new int[data.length];
		for(int i = 0;i<data.length;i++){
			int_data[i]=Integer.parseInt(data[i]);
		}
		addData(int_data);
	}

	
	/**
	 * グラフにデータ（数値型）を追加
	 * @param data
	 */
	public void addData(int[] data){
		if (index + data.length >= GraphManager.GraphWidth) {
			index = 0;
			value = null_value;
		}
		for (int i = 0; i < data.length; i++) {
			value[index] = data[i];
			index++;
		}
		repaint();		
	}

	/**
	 * グラフにデータ（浮動小数点数値型）を追加
	 * @param data　データ
	 * @param mul 倍率
	 */
	public void addData(float[] data,int mul){
		if (index + data.length >= GraphManager.GraphWidth) {
			index = 0;
			value = null_value;
		}
		for (int i = 0; i < data.length; i++) {
			value[index] = (int)(data[i] * mul);
			index++;
		}
		repaint();		
	}
	
	
	protected void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawLine(0, GraphManager.GraphHeight / 2, GraphManager.GraphWidth,
				GraphManager.GraphHeight / 2);
		g.setColor(Color.RED);
		for (int i = 0; i < index; i++) {
			g.drawLine(i == 0 ? i : i - 1, (value[i == 0 ? i : i - 1]) / value_rate
					+ GraphManager.GraphHeight / 2, i, (value[i]) / value_rate
					+ GraphManager.GraphHeight / 2);
		}
	}

}
