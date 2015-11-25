package analysis.calc.spectrum.FFT;

import java.text.DecimalFormat;


public class Complex implements Field {
	private double re;

	private double im;

	private static final double EPS = 1.0e-7;

	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Complex) {
			Complex c = (Complex) obj;
			return (Math.abs(this.re - c.re) < EPS && Math.abs(this.im - c.im) < EPS);
		}
		return false;
	}

	/**
	 * 追加　絶対値を返却
	 */
	public double abs(){
		return Math.sqrt(re*re+im*im);
	}

	public double real() {
		return re;
	}

	public double imag() { // @
		return im;
	}

	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat(" ##0.000;-##0.000");
		StringBuffer sb = new StringBuffer();
		sb.append(df.format(re));
		sb.append(" ");
		sb.append(df.format(im));
		return sb.toString();
	}

	public Field copy() {
		return new Complex(re, im);
	}

	public Field set(Field f) {
		Complex c = (Complex) f;
		this.re = c.re;
		this.im = c.im;
		return this;
	}

	public boolean isZero() {
		return (this.re < EPS && this.im < EPS);
	}

	public Field setZero() {
		this.re = 0;
		this.im = 0;
		return this;
	}

	public Field addTo(Field f) {
		Complex c = (Complex) f;
		this.re += c.re;
		this.im += c.im;
		return this;
	}

	public Field timesBy(Field f) {
		Complex c = (Complex) f;
		double tmp = this.re * c.re - this.im * c.im;
		this.im = this.im * c.re + this.re * c.im;
		this.re = tmp;
		return this;
	}

	public Field negate() {
		this.re = -this.re;
		this.im = -this.im;
		return this;
	}

	public Field div(int n) {
		this.re /= n;
		this.im /= n;
		return this;
	}

	private static Field[] wtbl = null;
	private static int last_n = 0;

	public Field[] make_wtbl(int n) {
		if (last_n == n)
			return wtbl;
		int n2 = (n >> 1);
		wtbl = new Field[n2 + 1];
		wtbl[0] = new Complex(1, 0);
		for (int i = 1; i < n2; i++) {
			double t = 2.0 * Math.PI * i / n;
			wtbl[i] = new Complex(Math.cos(t), Math.sin(t));
		}
		wtbl[n2] = new Complex(-1, 0);
		last_n = n;
		return wtbl;
	}
}
