package sg.edu.ntu.aalhossary.fyp2014.physics_engine.core;


// Bounding Sphere is to be used for atoms and molecules
public class BoundingSphere implements BoundingPrimitive {

	private double radius;
	private Vector3D centre;
	
	public BoundingSphere(double radius, Vector3D centre){
		this.radius = radius;
		this.centre = new Vector3D(centre);
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
			temp.subtract(bCube.getCentre());
			double distanceSquared = temp.getSquaredMagnitude();
			return distanceSquared <= (this.radius + bCube.getHalfSize()) * (this.radius + bCube.getHalfSize());
		}
		
		else if(other instanceof BoundingSphere) {
			BoundingSphere bSphere = (BoundingSphere)other; 
			Vector3D temp = new Vector3D(centre);
			temp.subtract(bSphere.getCentre());
			double distanceSquared = temp.getSquaredMagnitude();
			//return distanceSquared <= (this.radius + bSphere.getRadius()) * (this.radius + bSphere.getRadius());
			
			if(distanceSquared <= (this.radius + bSphere.getRadius()) * (this.radius + bSphere.getRadius())){
				System.out.println("distance between: " + Math.sqrt(distanceSquared));
				System.out.println("radii diff: " + (this.radius + bSphere.getRadius()));
				return true;	
				}
			return false;
		}
		
		else if(other instanceof BoundingBox){
			BoundingBox bBox = (BoundingBox)other; 
			Vector3D temp = new Vector3D(centre);
			temp.subtract(bBox.getCentre());
			double distanceSquared = temp.getSquaredMagnitude();
			boolean x_con = distanceSquared <= Math.pow(this.radius + bBox.getXLength(), 2);
			boolean y_con = distanceSquared <= Math.pow(this.radius + bBox.getYLength(), 2);
			boolean z_con = distanceSquared <= Math.pow(this.radius + bBox.getZLength(), 2);
			return x_con || y_con || z_con;
		}
		
		return false;
	}
	
	public Vector3D getCentre(){
		return centre;
	}
	
	public double getRadius(){
		return radius;
	}
}