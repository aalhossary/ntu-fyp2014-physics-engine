package sg.edu.ntu.aalhossary.fyp2014.physics_engine;
import java.util.ArrayList;

public class Matrix3 {
	private double [] data = new double [9];
	
	public Matrix3(double [] data){
		this.data = data;
	}
	
	public void add(Matrix3 aM) {
		throw new UnsupportedOperationException();
	}

	
	public void multiply(Matrix3 aM) {
		
	}

	public void multiply(double aScale) {
		throw new UnsupportedOperationException();
	}

	// Transform the given vector by this matrix
	public Vector3D transform(Vector3D v) {
		return new Vector3D ( 	v.x*data[0] + v.y*data[1] + v.z*data[2],
								v.x*data[3] + v.y*data[4] + v.z*data[5],
								v.x*data[6] + v.y*data[7] + v.z*data[8] );
	}
	
	// Transform the given matrix by this matrix
	public Matrix3 transform (Matrix3 o) {
		double temp [] = new double [9];
		temp [0] = data[0]*o.data[0] + data[1]*o.data[3] + data[2]*o.data[6];
		temp [1] = data[0]*o.data[1] + data[1]*o.data[4] + data[2]*o.data[7];
		temp [2] = data[0]*o.data[2] + data[1]*o.data[5] + data[2]*o.data[8];
		temp [3] = data[3]*o.data[0] + data[4]*o.data[3] + data[5]*o.data[6];
		temp [4] = data[3]*o.data[1] + data[4]*o.data[4] + data[5]*o.data[7];
		temp [5] = data[3]*o.data[2] + data[4]*o.data[5] + data[5]*o.data[8];
		temp [6] = data[6]*o.data[0] + data[7]*o.data[3] + data[8]*o.data[6];
		temp [7] = data[6]*o.data[1] + data[7]*o.data[4] + data[8]*o.data[7];
		temp [8] = data[6]*o.data[2] + data[7]*o.data[5] + data[8]*o.data[8];
		return new Matrix3 (temp);
	}

	public void setInverse(Matrix3 aM) {
		throw new UnsupportedOperationException();
	}

	public Matrix3 inverse() {
		throw new UnsupportedOperationException();
	}

	public void invert() {
		throw new UnsupportedOperationException();
	}

	public void setTranspose(Matrix3 aM) {
		throw new UnsupportedOperationException();
	}

	public Matrix3 transpose() {
		throw new UnsupportedOperationException();
	}

	public void setOrientation(Quaternion aQ) {
		throw new UnsupportedOperationException();
	}
}