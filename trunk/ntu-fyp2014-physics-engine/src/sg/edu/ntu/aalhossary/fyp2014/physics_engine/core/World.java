package sg.edu.ntu.aalhossary.fyp2014.physics_engine.core;

import java.util.ArrayList;

public class World {
	public static double machineEpsilon;
	
	private static ArrayList<AbstractParticle> abstractParticles = new ArrayList<>();;
	private static ForceRegistry registry = new ForceRegistry();
	private static BroadPhaseCollisionDetector detector = new BroadPhaseCollisionDetector();
	private static ContactResolver resolver = new IterativeContactResolver();


	public static void main (String[] args){
		
		
		
		machineEpsilon = calculateMachineEpsilon();
		AbstractParticle a1 = new Atom("011");	//covalent radius for Na
		AbstractParticle a2 = new Atom("017");	//covalent radius for Cl
		
		
		a1.setPosition(0, 0, 0);
	//	a1.setVelocity(2e-13, 2e-13, 2e-13);
		a1.setAcceleration(0, 0, 0);
		// find a way to get oxidation state/ net charge
		a1.setNetCharge(1);
	
		// actual is 564.02 pm
		a2.setPosition(700e-12, 700e-12, 700e-12);
		a2.setVelocity(0, 0, 0);
		a2.setAcceleration(0, 0, 0);
		a2.setNetCharge(-1);
	
		Vector3D eF = Force.getElectricForce(a1, a2) ;
		System.out.println("Electric (Coulomb) Force between atoms is: " + eF.print());
		registry.add(a1, eF);
		registry.add(a2, eF.getNegativeVector());
		
	
		System.out.println("Time \t Particle \t Position \t Velocity");
		for(int i=0; i<9; i++){
			registry.updateAllForces();
			System.out.print(i + "\t");
			a1.integrate(i);
			a2.integrate(i);
			System.out.print(" a1 \t\t");
			printParticleStatus(a1);
			System.out.print("\t a2 \t\t");
			printParticleStatus(a2);
			System.out.println();
		}
		
		
	}
	
	private static void printParticleStatus(AbstractParticle p){
		System.out.println(p.position.print()+"\t");
		//System.out.println(p.velocity.print());	
		//System.out.println("Acceleration: " + p.acceleration.print());

	}
	
	private static double calculateMachineEpsilon(){
		double machEps = 1.0;
		do{
           machEps /= 2.0;	// operator / is used instead of bit shifting since we do not know the CPU architecture
		} while ((double) (1.0 + (machEps / 2.0)) != 1.0);
        return (machEps);
	}
}