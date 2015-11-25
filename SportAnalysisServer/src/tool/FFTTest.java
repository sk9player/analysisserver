package tool;

import analysis.calc.Calculate;
import analysis.calc.spectrum.FFT.FFT;

public class FFTTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int data_len=1000;
		int window_len=8192;
		
		
		double[] d= new double[window_len];
		
		
		for(int i=0;i<data_len;i++)
			d[i]=Math.sin((double)i/5)+Math.cos((double)i/2);
			
		
		d[100]=5;
		d[110]=5;
		d[120]=5;
		
		Calculate.set_window_func_s();
		d = Calculate.mul_window_func_s(d);
		
		System.out.println("”z—ñ‚Ì’l");
		for(int i=0;i<data_len+100;i++)
			System.out.println(d[i]);
			
		d=FFT.fftAnalysis(d);
		
		System.out.println("Žü”g”“Á’¥");
		for(int i=0;i<data_len;i++)
			System.out.println(d[i]);

	}

}
