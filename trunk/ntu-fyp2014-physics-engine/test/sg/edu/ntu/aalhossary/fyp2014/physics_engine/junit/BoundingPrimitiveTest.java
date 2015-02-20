package sg.edu.ntu.aalhossary.fyp2014.physics_engine.junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.BoundingPrimitive;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.BoundingSphere;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.BoundingBox;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.BoundingCube;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.Vector3D;
public class BoundingPrimitiveTest {

	private BoundingPrimitive bp1, bp2, bp3;
	
	@Before
	public void setUp() throws Exception {
		bp1 = new BoundingSphere(1,new Vector3D());
		bp2 = new BoundingCube(2, new Vector3D(5,5,5));
		bp3 = new BoundingBox(3,4,5, new Vector3D (2,2,2));
	}

	@Test
	public void test() {
		assertEquals(bp1.overlap(bp2), false);
		assertEquals(bp1.overlap(bp3), true);
		assertEquals(bp2.overlap(bp3), true);
	}

}
