package sg.edu.ntu.aalhossary.fyp2014.physics_engine;
public class Contact {
	private PotentialContact _potentialContact;
	private double _penetration;
	private double _restitution;
	private double _friction;
	private Vector3D _contactNormal;
	private Vector3D _contactPoint;

	public void updateVelocity(Particle aParticle, Vector3D aVelocity) {
		throw new UnsupportedOperationException();
	}

	public void updateAcceleration(Particle aParticle, Vector3D aAcceleration) {
		throw new UnsupportedOperationException();
	}
}