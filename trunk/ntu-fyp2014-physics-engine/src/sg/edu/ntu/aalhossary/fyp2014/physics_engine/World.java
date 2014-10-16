package sg.edu.ntu.aalhossary.fyp2014.physics_engine;
import java.util.ArrayList;
import java.util.Vector;

public class World {
	public static double machineEpsilon;
	
	private static ArrayList<Particle> particles = new ArrayList<>();;
	private static ForceRegistry registry = new ForceRegistry();
	private static BroadPhaseCollisionDetector detector = new BroadPhaseCollisionDetector();
	private static ContactResolver resolver = new IterativeContactResolver();

	public void generateContacts() {
		
	}

	public static void main (String[] args){
		
		
		machineEpsilon = calculateMachineEpsilon();
		Particle p = new Particle();
		p.setMass(1);
		p.setVelocity(0, 2, 0);
		p.setAcceleration(0, 0, 0);
		
		registry.add(p, new Force(0.0, -1.0, 0.0));
		registry.add(p, new Force(0.0, -1.0, 0.0));
	
		registry.updateAllForces();
		System.out.println(p.forceAccumulated.print());

		registry.remove(p, new Force(0,-1,0));
	
		System.out.println(p.forceAccumulated.print());
		/*
		System.out.println(p.getMass());
		p.integrate(3);
		System.out.println("iteration ");
		printParticleStatus(p);
		*/
		
	
	}
	
	private static void printParticleStatus(Particle p){
		System.out.println("Position: " + p.position.print());
		System.out.println("Velocity: " + p.velocity.print());
		System.out.println("Acceleration: " + p.acceleration.print());

	}
	
	private static double calculateMachineEpsilon(){
		double machEps = 1.0;
		do{
           machEps /= 2.0;	// operator / is used instead of bit shifting since we do not know the CPU architecture
		} while ((double) (1.0 + (machEps / 2.0)) != 1.0);
        return (machEps);
	}
}