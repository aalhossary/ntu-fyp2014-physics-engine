package sg.edu.ntu.aalhossary.fyp2014.physics_engine.junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sg.edu.ntu.aalhossary.fyp2014.common.AbstractParticle;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.Atom;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.Vector3D;

public class ParticleTest {

	private AbstractParticle p1;
	private AbstractParticle p2;
	private final double COEFFICIENT_OF_RESTITUTION = 1;
	
	@Before
	public void setUp() throws Exception {
		p1 = new Atom("011");
		p2 = new Atom("017");
		
		p1.setPosition(0, 0, 0);
		p1.setVelocity(2, 2, 2);
		p1.setAcceleration(0, 0, 0);
		p1.setNetCharge(1);		// find a way to get oxidation state/ net charge
		
		p2.setPosition(3, 5, 5, -10);
		p2.setVelocity(0, 0, 0);
		p2.setAcceleration(0, 0, 0);
		p2.setNetCharge(-1);
	}

	@Test
	public void testCalculateVelocityChange() {
		Vector3D new_v = p2.calculateVelocityChange(p1.getMass(), p1.getVelocity(),COEFFICIENT_OF_RESTITUTION);
		Vector3D expected_v = new Vector3D(0.7867447076858267,0.7867447076858267,0.7867447076858267);
		assertEquals(new_v,expected_v);
	}
	
	@Test
	public void testIntegrate(){
		p1.integrate(2);
		Vector3D new_position = new Vector3D(4,4,4);
		Vector3D new_velocity = new Vector3D(2,2,2);
		Vector3D new_acc = new Vector3D();
		assertEquals(p1.getPosition(), new_position);
		assertEquals(p1.getVelocity(), new_velocity);
		assertEquals(p1.getAcceleration(), new_acc);	
	}
}
