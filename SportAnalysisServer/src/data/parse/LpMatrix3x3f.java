package data.parse;

public class LpMatrix3x3f {
	public float[][] data = new float[4][4];

	public void fromQuaternion(float[] q)
	{	
		float tmp1;
		float tmp2;

		float sqw = q[0] * q[0];
		float sqx = q[1] * q[1];
		float sqy = q[2] * q[2];
		float sqz = q[3] * q[3];

		float invs = 1 / (sqx + sqy + sqz + sqw);
		
		data[0][0] = ( sqx - sqy - sqz + sqw) * invs;
		data[1][1] = (-sqx + sqy - sqz + sqw) * invs;
		data[2][2] = (-sqx - sqy + sqz + sqw) * invs;
		
		tmp1 = q[1] * q[2];
		tmp2 = q[3] * q[0];
		
		data[1][0] = 2.0f * (tmp1 + tmp2) * invs;
		data[0][1] = 2.0f * (tmp1 - tmp2) * invs;
		
		tmp1 = q[1] * q[3];
		tmp2 = q[2] * q[0];
		
		data[2][0] = 2.0f * (tmp1 - tmp2) * invs;
		data[0][2] = 2.0f * (tmp1 + tmp2) * invs;
		
		tmp1 = q[2] * q[3];
		tmp2 = q[1] * q[0];
		
		data[2][1] = 2.0f * (tmp1 + tmp2) * invs;
		data[1][2] = 2.0f * (tmp1 - tmp2) * invs;
	}

	public float det3x3() {
		return data[0][0]*(data[2][2]*data[1][1]-data[2][1]*data[1][2])
		- data[1][0]*(data[2][2]*data[0][1]-data[2][1]*data[0][2])
		+ data[2][0]*(data[1][2]*data[0][1]-data[1][1]*data[0][2]);
	}	
	
	public LpMatrix3x3f inverse()
	{
		float det;
		LpMatrix3x3f invM = new LpMatrix3x3f();

		det = det3x3();

		invM.data[0][0] = (data[2][2]*data[1][1]-data[2][1]*data[1][2])/det;
		invM.data[0][1] = -(data[2][2]*data[0][1]-data[2][1]*data[0][2])/det;
		invM.data[0][2] = (data[1][2]*data[0][1]-data[1][1]*data[0][2])/det;

		invM.data[1][0] = -(data[2][2]*data[1][0]-data[2][0]*data[1][2])/det;
		invM.data[1][1] = (data[2][2]*data[0][0]-data[2][0]*data[0][2])/det;
		invM.data[1][2] = -(data[1][2]*data[0][0]-data[1][0]*data[0][2])/det;

		invM.data[2][0] = (data[2][1]*data[1][0]-data[2][0]*data[1][1])/det;
		invM.data[2][1] = -(data[2][1]*data[0][0]-data[2][0]*data[0][1])/det;
		invM.data[2][2] = (data[1][1]*data[0][0]-data[1][0]*data[0][1])/det;

		return invM;
	}
	
	public float[] vectMult3(float[] v)
	{
		float[] r = new float[3];
	
		r[0] = data[0][0]*v[0] + data[0][1]*v[1] + data[0][2]*v[2];
		r[1] = data[1][0]*v[0] + data[1][1]*v[1] + data[1][2]*v[2];
		r[2] = data[2][0]*v[0] + data[2][1]*v[1] + data[2][2]*v[2];
		
		return r;
	}
}