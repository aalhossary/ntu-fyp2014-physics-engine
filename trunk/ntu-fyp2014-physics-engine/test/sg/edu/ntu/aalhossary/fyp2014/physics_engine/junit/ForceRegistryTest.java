package sg.edu.ntu.aalhossary.fyp2014.physics_engine.junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sg.edu.ntu.aalhossary.fyp2014.common.AbstractParticle;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.Atom;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.ForceRegistry;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.Vector3D;

public class ForceRegistryTest {

	private ForceRegistry registry;
	private AbstractParticle p;
	
	@Before
	public void setUp() throws Exception {
		registry = new ForceRegistry();
		p = new Atom ("001");
	}

	@Test
	public void testAdd() {
		Vector3D test_force = new Vector3D(4,4,4);
		registry.add(p, test_force);
		
		fail("Not yet implemented");
	}

}
