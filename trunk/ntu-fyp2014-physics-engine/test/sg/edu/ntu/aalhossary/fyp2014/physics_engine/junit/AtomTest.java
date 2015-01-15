package sg.edu.ntu.aalhossary.fyp2014.physics_engine.junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.Atom;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.BoundingSphere;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.Force;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.Vector3D;

public class AtomTest {

	private Atom a1; 	// Na
	private Atom a2; 	// Cl
	
	@Before
	public void setUp() throws Exception {
		a1 = new Atom("011");
		a2 = new Atom("017");
		
		a1.setPosition(0, 0, 0);
		a1.setVelocity(0, 0, 0);
		a1.setAcceleration(0, 0, 0);
		a1.setNetCharge(1);		// find a way to get oxidation state/ net charge
		
		a2.setPosition(3, 5, 5, -10);
		a2.setVelocity(0, 0, 0);
		a2.setAcceleration(0, 0, 0);
		a2.setNetCharge(-1);
	}

	@Test
	public void testAtomicProperties(){
		double expected_mass = 3.82e-26;
		double expected_radius = 2.27e-10;
		BoundingSphere bs = (BoundingSphere) a1.getBoundingPrimitive();
		assertEquals(a1.getMass(),expected_mass,1e-26);
		assertEquals(bs.getRadius(), expected_radius, 1e-10);
	}
	
	

}
