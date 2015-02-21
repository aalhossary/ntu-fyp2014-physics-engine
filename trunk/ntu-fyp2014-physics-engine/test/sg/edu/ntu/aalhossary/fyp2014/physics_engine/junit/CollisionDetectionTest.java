package sg.edu.ntu.aalhossary.fyp2014.physics_engine.junit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import sg.edu.ntu.aalhossary.fyp2014.common.AbstractParticle;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.Atom;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.NarrowCollisionDetector;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.OctTree;

public class CollisionDetectionTest {
	
	private OctTree octTree;
	private NarrowCollisionDetector detector ;
	private AbstractParticle a1, a2, a3;
	
	@Before
	public void setUp() throws Exception {
		octTree = new OctTree();
		detector = new NarrowCollisionDetector();
		a1 = new Atom ("Na");
		a2 = new Atom ("Cl");
		a3 = new Atom ("Na");
		octTree.insert(a1);
		octTree.insert(a2);
		octTree.insert(a3);
		
		a1.setPosition(0, 0, 0);
		a2.setPosition(2e-10, 2e-10, 2e-10);
		a3.setPosition(5e-10, 5e-10, 5e-10);
	}

	@Test
	public void testCollisionDetection() {
		ArrayList<AbstractParticle[]> expectedContacts = new ArrayList<>();
		AbstractParticle [] expectedContact = {a1,a2};
		expectedContacts.add(expectedContact);
		
		ArrayList<AbstractParticle[]> actualContacts = new ArrayList<AbstractParticle[]>();
		detector.detectCollision(octTree, octTree.getAllParticles(), actualContacts);
		
		assertEquals(expectedContacts.size(), actualContacts.size());
		for (int i=0; i< expectedContacts.size(); i++) {
			assertEquals(expectedContacts.get(i)[0], actualContacts.get(i)[0]);
			assertEquals(expectedContacts.get(i)[1], actualContacts.get(i)[1]);
		}
	}

}
