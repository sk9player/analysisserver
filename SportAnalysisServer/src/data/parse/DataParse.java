package data.parse;

public class DataParse {

	private static float FLT_EPSILON = 1.192092896e-07f;

	/**
	 * ４元角をヨーピッチローに変更
	 * @param q
	 */
	public static float[] quaternionToEuler(float[] q)
	{
	        LpMatrix3x3f tM=new LpMatrix3x3f();
	        float[] r= new float[3];

	        double theta;
	        double cp;
	        float sp;

	        tM = quaternionToMatrix(q);
	        sp = tM.data[0][2];
	        if (sp > 1.0f) {
	                sp = 1.0f;
	        } else if (sp < -1.0f) {
	                sp = -1.0f;
	        }

	        theta = -Math.asin(sp);
	        cp = Math.cos(theta);
	        if (cp > (8192.0f * FLT_EPSILON)) {
	                r[1] = (float)theta;
	                r[2] = (float)(Math.atan2(tM.data[0][1], tM.data[0][0]));
	                r[0] = (float)(Math.atan2(tM.data[1][2], tM.data[2][2]));
	        } else {
	                r[1] = (float)theta;
	                r[2] = (float)(-Math.atan2(tM.data[1][0], tM.data[1][1]));
	                r[0] = 0;
	        }
	        
	        return r;
	}

	private static LpMatrix3x3f quaternionToMatrix(float[] q)
	{
		LpMatrix3x3f M =new LpMatrix3x3f();
	    float tmp1;
	    float tmp2;

	    float sqw = q[0] * q[0];
	    float sqx = q[1] * q[1];
	    float sqy = q[2] * q[2];
	    float sqz = q[3] * q[3];

	    float invs = 1 / (sqx + sqy + sqz + sqw);

	    M.data[0][0] = ( sqx - sqy - sqz + sqw) * invs;
	    M.data[1][1] = (-sqx + sqy - sqz + sqw) * invs;
	    M.data[2][2] = (-sqx - sqy + sqz + sqw) * invs;

	    tmp1 = q[1] * q[2];
	    tmp2 = q[3] * q[0];

	    M.data[1][0] = 2.0f * (tmp1 + tmp2) * invs;
	    M.data[0][1] = 2.0f * (tmp1 - tmp2) * invs;

	    tmp1 = q[1] * q[3];
	    tmp2 = q[2] * q[0];

	    M.data[2][0] = 2.0f * (tmp1 - tmp2) * invs;
	    M.data[0][2] = 2.0f * (tmp1 + tmp2) * invs;

	    tmp1 = q[2] * q[3];
	    tmp2 = q[1] * q[0];

	    M.data[2][1] = 2.0f * (tmp1 + tmp2) * invs;
	    M.data[1][2] = 2.0f * (tmp1 - tmp2) * invs;
	    return M;
	}
	
	/**
	 * 加速度データから線形加速度を取得
	 * @param q
	 * @param a
	 * @return
	 */
	public static float[] accToLinacc(float[] q,float[] a){

		float[] gWorld=new float[3];
		float[] gSensor=new float[3];
		float[] linAcc=new float[3];
		LpMatrix3x3f M=new LpMatrix3x3f();

		gWorld[0] = 0;
		gWorld[1] = 0;
		gWorld[2] = -1;

		M = quaternionToMatrix(q);
		gSensor = matVectMult3(M, gWorld);
		linAcc = vectSub3x1(a, gSensor);
		return linAcc;
	}

	/**
	 * 加速度データからワールド座標線形加速度を取得
	 * @param q
	 * @param a
	 * @return
	 */
	public static float[] accToWorldLinacc(float[] q,float[] a){
	
		float[] aWorld = new float[3];
		LpMatrix3x3f M = new LpMatrix3x3f();
		LpMatrix3x3f iM = new LpMatrix3x3f();

		M.fromQuaternion(q);
		iM = M.inverse();
		aWorld = iM.vectMult3(a);

		aWorld[2] = aWorld[2] + 1.0f;
		return aWorld;
	}

	private static float[] matVectMult3(LpMatrix3x3f matrix, float[] vector)
	{
		float[] result = new float[3];

	    result[0] = matrix.data[0][0] * vector[0] + matrix.data[0][1] * vector[1] + matrix.data[0][2] * vector[2];
	    result[1] = matrix.data[1][0] * vector[0] + matrix.data[1][1] * vector[1] + matrix.data[1][2] * vector[2];
	    result[2] = matrix.data[2][0] * vector[0] + matrix.data[2][1] * vector[1] + matrix.data[2][2] * vector[2];

	    return result;
	}
	
	private static float[] vectSub3x1(float[] vector1, float[] vector2)
	{
		float[] result = new float[3];

	    result[0] = vector1[0] -vector2[0];
	    result[1] = vector1[1] -vector2[1];
	    result[2] = vector1[2] -vector2[2];

	    return result;
	}
	
	
}
