package sg.edu.ntu.aalhossary.fyp2014.physics_engine.core;


public class BoundingCube implements BoundingPrimitive {

	private double half_size;
	private Vector3D centre;
	
	public BoundingCube(double half_size, Vector3D centre){
		this.half_size = half_size;
		centre = new Vector3D(centre);
	}
	
	public void updateCentre (double x, double y, double z){
		this.centre.x = x;
		this.centre.y = y;
		this.centre.z = z;
	}
	
	public boolean overlap(BoundingPrimitive other) {
		
		// The primitives overlap if the position difference (distance) is less than the sum of two half_sizes
		
		if(other instanceof BoundingCube) {
			BoundingCube bCube = (BoundingCube)other; 
			Vector3D temp = new Vector3D(centre);
			temp.subtract(bCube.centre);
			double distanceSquared = temp.getSquaredMagnitude();
			return distanceSquared <= (this.half_size + bCube.half_size) * (this.half_size + bCube.half_size);
		}
		
		else if(other instanceof BoundingSphere) {
			BoundingSphere bSphere = (BoundingSphere)other; 
			Vector3D temp = new Vector3D(centre);
			temp.subtract(bSphere.getCentre());
			double distanceSquared = temp.getSquaredMagnitude();
			return distanceSquared <= (this.half_size + bSphere.getRadius()) * (this.half_size + bSphere.getRadius());
		}
		
		else if(other instanceof BoundingBox){
			BoundingBox bBox = (BoundingBox)other; 
			Vector3D temp = new Vector3D(centre);
			temp.subtract(bBox.getCentre());
			double distanceSquared = temp.getSquaredMagnitude();
			boolean x_con = distanceSquared <= Math.pow(this.half_size + bBox.getXLength(), 2);
			boolean y_con = distanceSquared <= Math.pow(this.half_size + bBox.getYLength(), 2);
			boolean z_con = distanceSquared <= Math.pow(this.half_size + bBox.getZLength(), 2);
			return x_con || y_con || z_con;
		}
		
		return false;
	}
	
	public Vector3D getCentre(){
		return centre;
	}
	
	public double getHalfSize(){
		return half_size;
	}
}