package sg.edu.ntu.aalhossary.fyp2014.physics_engine.junit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.ntu.aalhossary.fyp2014.common.AbstractParticle;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.Atom;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.ContactResolver;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.ForceRegistry;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.Molecule;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.NarrowCollisionDetector;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.OctTree;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.Vector3D;

public class IntegrationTest {
	
	private ArrayList<AbstractParticle> activeParticles;
	private ArrayList<Vector3D> oldPositions;
	private ArrayList<AbstractParticle[]> potentialContacts;
	private ForceRegistry registry;
	private OctTree octTree;
	private NarrowCollisionDetector detector;
	private ContactResolver resolver;
	private AbstractParticle a1, a2, a3, m1;

	@Before
	public void setUp() throws Exception {
		activeParticles = new ArrayList<>();
		oldPositions= new ArrayList<>();
		potentialContacts = new ArrayList<>();
		registry = new ForceRegistry();
		octTree = new OctTree();
		detector = new NarrowCollisionDetector();
		resolver = new ContactResolver();
		
		a1 = new Atom ("Na");
		a2 = new Atom ("Cl");
		a3 = new Atom ("Na");
		a1.setPosition(0, 0, 0);
		a2.setPosition(3e-10, 3e-10, 3e-10);
		a3.setPosition(10e-10, 10e-10, 10e-10);
		a1.setNetCharge(1);	
		a2.setNetCharge(-1);	
		a3.setNetCharge(1);
		
		octTree.insert(a1);
		octTree.insert(a2);
		octTree.insert(a3);

	}
	
	@After
	public void tearDown(){
		activeParticles.clear();
		oldPositions.clear();
		potentialContacts.clear();
		registry.clear();
		octTree.clear();
	}

	@Test
	public void testIntegration() {
		
		for(int i= 0; i <= 500; i++){
			activeParticles = octTree.getAllParticles();
			registry.updateAllForces(activeParticles);
			for(AbstractParticle p: activeParticles){
				p.integrate(i*1e-18);	
			}
			//octTree.updateAllActiveParticles();
		}
	
		assertEquals(new Vector3D(-2.2308972415268442E-11,-2.2308972415268442E-11,-2.2308972415268442E-11), activeParticles.get(0).getPosition());
		assertEquals(new Vector3D(3.2508764163462386E-10,3.2508764163462386E-10,3.2508764163462386E-10), activeParticles.get(1).getPosition());
		assertEquals(new Vector3D(9.836208011031002E-10,9.836208011031002E-10,9.836208011031002E-10), activeParticles.get(2).getPosition());
		
	}
	
	@Test
	public void testCollisionDetection() {
		
		for(int i= 0; i < 65535; i++){
			activeParticles = octTree.getAllParticles();
			registry.updateAllForces(activeParticles);
		
			for(AbstractParticle p: activeParticles){
				p.integrate(i*1e-18);
			}
		
		//	octTree.updateAllActiveParticles();
			detector.detectCollision(octTree, activeParticles, potentialContacts);
			
			if(potentialContacts.size()>0){
				break;
			}
		}
		
		ArrayList<AbstractParticle[]> expectedContacts = new ArrayList<>();
		AbstractParticle [] expectedContact = {a1,a2};
		expectedContacts.add(expectedContact);
		
		assertEquals(expectedContacts.size(), potentialContacts.size());
		for (int i=0; i< expectedContacts.size(); i++) {
			assertEquals(expectedContacts.get(i)[0], potentialContacts.get(i)[0]);
			assertEquals(expectedContacts.get(i)[1], potentialContacts.get(i)[1]);
		}
	}
	
	@Test
	public void testCollisionResolution() {
		
		for(int i= 0; i < 65535; i++){
			activeParticles = octTree.getAllParticles();
			registry.updateAllForces(activeParticles);
		
			for(AbstractParticle p: activeParticles){
				p.integrate(i*1e-18);
			}
		
			octTree.updateAllActiveParticles();
			detector.detectCollision(octTree, activeParticles, potentialContacts);
			
			if(potentialContacts.size()>0){
				resolver.resolveContacts(potentialContacts);
				break;
			}
		}
		
		assertEquals(new Vector3D(-800.3733667791594,-800.3733667791594,-800.3733667791594), potentialContacts.get(0)[0].getVelocity());
		assertEquals(new Vector3D(566.5114585208407,566.5114585208407,566.5114585208407), potentialContacts.get(0)[1].getVelocity());
	}

}
