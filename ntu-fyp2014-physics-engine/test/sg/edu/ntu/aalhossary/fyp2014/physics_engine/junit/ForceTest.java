package sg.edu.ntu.aalhossary.fyp2014.physics_engine.junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sg.edu.ntu.aalhossary.fyp2014.common.AbstractParticle;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.Atom;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.ElectricForce;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.Force;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.LennardJonesForce;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.Vector3D;

public class ForceTest {

	private AbstractParticle p1, p2;
	
	@Before
	public void setUp() throws Exception {
		p1 = new Atom ("Na");
		p2 = new Atom ("Cl");
		p1.setPosition(0, 0, 0);
		p2.setPosition(5, 5, 5);
	}

	@Test
	public void testElectricForce() {
		Force electricForce = new ElectricForce(p1,p2);
		Vector3D expectedElectricForce = new Vector3D(-8.87798778017725E-30,-8.87798778017725E-30,-8.87798778017725E-30);
		assertEquals(electricForce.getForce(), expectedElectricForce);
		assertEquals(electricForce.getNegativeForce(), expectedElectricForce.getNegativeVector());
	}
	
	@Test
	public void testLennardJonesForce(){
		
		Force lennardJonesForce = new LennardJonesForce (p1, p2);
		Vector3D expectedLJForce = new Vector3D(2.637933006073083E-72,2.637933006073083E-72,2.637933006073083E-72);
		assertEquals(lennardJonesForce.getForce(), expectedLJForce);
		assertEquals(lennardJonesForce.getNegativeForce(), expectedLJForce.getNegativeVector());
	}
}
