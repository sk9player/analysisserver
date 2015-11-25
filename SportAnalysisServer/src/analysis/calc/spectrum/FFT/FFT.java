package analysis.calc.spectrum.FFT;


public class FFT {

	/**
	 * 周波数分析
	 *
	 * @param data　分析するデータ
	 */
	public static double[] fftAnalysis(double[] data) {

		// データ数の取得
		int data_num=data.length;

		// データの複素数
		Complex[] fft_data = new Complex[data_num];

		// 複素数化
		for (int i = 0; i < data_num; i++) {
			fft_data[i] = new Complex(data[i], 0.0);
		}

		// FFT変換
		execute(false, fft_data);

//		for(int i=0;i<fft_data.length;i++)
//			System.out.println(fft_data[i]);

		//周波数分析後絶対値データ
		double[] fftedData=new double[data_num];

		for(int i=0;i<data_num;i++){
			fftedData[i]=fft_data[i].abs();
		}

		return fftedData;
	}


	/**
	 * FFT実行
	 * @param inverse
	 * @param f
	 */
	private static void execute(boolean inverse, Field[] f) {
		int n = f.length;
		int n2 = n >> 1;
		Field[] wtbl = f[0].make_wtbl(n);

		for (int i = 0, j = 0;;) {
			if (i < j) {
				Field t = f[i];
				f[i] = f[j];
				f[j] = t;
			}
			if (++i >= n)
				break;
			int k = n2;
			while (k <= j) {
				j -= k;
				k >>= 1;
			}
			j += k;
		}

		Field t = f[0].copy();

		for (int k = 1; k < n;) {
			int h = 0, k2 = k << 1, d = n / k2;
			for (int j = 0; j < k; j++) {
				if (inverse)
					for (int i = j; i < n; i += k2) {
						int ik = i + k;
						f[ik].timesBy(wtbl[h]);
						t.set(f[ik]).addTo(f[i]);
						f[ik].negate().addTo(f[i]);
						f[i].set(t);
					}
				else
					for (int i = j; i < n; i += k2) {
						int ik = i + k;
						f[ik].timesBy(wtbl[n2 - h]);
						t.set(f[ik]).negate().addTo(f[i]);
						f[ik].addTo(f[i]);
						f[i].set(t);
					}
				h += d;
			}
			k = k2;
		}
//		if (!inverse)
		if (inverse)
			for (int i = 0; i < n; i++)
				f[i].div(n);
	}

}
