package sg.edu.ntu.aalhossary.fyp2014.physics_engine;
import java.util.ArrayList;

public class Matrix4 {
	private double [] data = new double [12];
	
	public void add(Matrix4 aM) {
		throw new UnsupportedOperationException();
	}

	public void multiply(Matrix4 aM) {
		throw new UnsupportedOperationException();
	}

	public void multiply(double aScale) {
		throw new UnsupportedOperationException();
	}

	public Vector3D transform(Vector3D v) {
		return new Vector3D ( 	v.x*data[0] + v.y*data[1] + v.z*data[2] + data[3],
								v.x*data[4] + v.y*data[5] + v.z*data[6] + data[7],
								v.x*data[8] + v.y*data[9] + v.z*data[10] + data[11]);
	}

	public Matrix4 transform(Matrix4 aM) {
		throw new UnsupportedOperationException();
	}

	public void setInverse(Matrix4 aM) {
		throw new UnsupportedOperationException();
	}

	public double getDeterminant() {
		throw new UnsupportedOperationException();
	}

	public Matrix4 inverse() {
		throw new UnsupportedOperationException();
	}

	public void invert() {
		throw new UnsupportedOperationException();
	}

	public void setOrientationAndPos(Quaternion aQ, Vector3D aPos) {
		throw new UnsupportedOperationException();
	}

	public Vector3D transformInverse(Vector3D aV) {
		throw new UnsupportedOperationException();
	}

	public Vector3D transformDirection(Vector3D aV) {
		throw new UnsupportedOperationException();
	}

	public Vector3D transformInverseDirection(Vector3D aV) {
		throw new UnsupportedOperationException();
	}
}