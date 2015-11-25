package analysis.calc.spectrum.FFT;

public interface Field {

	public Field copy();

	public Field set(Field f);

	public boolean isZero();

	public Field setZero();

	public Field addTo(Field f);

	public Field timesBy(Field f);

	public Field negate();

	public Field div(int n);

	public Field[] make_wtbl(int n);

}
