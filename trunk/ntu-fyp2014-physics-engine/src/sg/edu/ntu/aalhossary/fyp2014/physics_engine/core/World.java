package sg.edu.ntu.aalhossary.fyp2014.physics_engine.core;

import java.util.ArrayList;

import sg.edu.ntu.aalhossary.fyp2014.common.AbstractParticle;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.Units.*;


public class World {
	public static double machineEpsilon;
	
	public static double distance_metric = DISTANCE.m.value();
	public static double time_metric = TIME.as.value();
	public static double mass_metric = MASS.kg.value();
	
	
	private static ArrayList<AbstractParticle> abstractParticles = new ArrayList<>();;
	private static ForceRegistry registry = new ForceRegistry();
	private static BroadPhaseCollisionDetector detector = new BroadPhaseCollisionDetector();
	private static ContactResolver resolver = new IterativeContactResolver();


	public static void main (String[] args){
		
		machineEpsilon = calculateMachineEpsilon();
		AbstractParticle a1 = new Atom("011");	//Na
		AbstractParticle a2 = new Atom("017");	//Cl
		
		abstractParticles.add(a1);
		abstractParticles.add(a2);
		
		a1.setPosition(0, 0, 0);
		a1.setVelocity(0, 0, 0);
		a1.setAcceleration(0, 0, 0);
		a1.setNetCharge(1);		// find a way to get oxidation state/ net charge
		
		a2.setPosition(5e-10, 5e-10, 5e-10);
		a2.setVelocity(0, 0, 0);
		a2.setAcceleration(0, 0, 0);
		a2.setNetCharge(-1);
	
		// Registering forces for the first time
		Vector3D electricForce = Force.getElectricForce(a1, a2) ;
		System.out.println("Electric (Coulomb) Force between atoms is: " + electricForce.print());
		registry.add(a1, electricForce);
		registry.add(a2, electricForce.getNegativeVector());
		
		Vector3D vdwForce = Force.getLennardJonesPotential(a1, a2);
		System.out.println("vdW Force between atoms is: " + vdwForce.print());
		registry.add(a1, vdwForce);
		registry.add(a2, vdwForce.getNegativeVector());
	
		System.out.println("Time \t a1\t\t\t \t  a2\t\t\t");
		for(int i=0; i<10000; i++){
			
			// Applying Forces
			registry.applyForces();
			a1.integrate(i*time_metric);
			a2.integrate(i*time_metric);
			
			// Updating Forces
			Vector3D newElectricForce = Force.getElectricForce(a1, a2);
			registry.updateForce(a2, electricForce.getNegativeVector(), newElectricForce.getNegativeVector());
			registry.updateForce(a1, electricForce, newElectricForce);		// important! update the negative first. otherwise, old value will be lost and getNegativeVector will return error
			
			Vector3D newVdWForce = Force.getLennardJonesPotential(a1, a2);
			registry.updateForce(a2, vdwForce.getNegativeVector(), newVdWForce.getNegativeVector());
			registry.updateForce(a1, vdwForce, newVdWForce);

						
			System.out.print(i + "\t");
			printParticleStatus(a1);
			System.out.print("\t");
			printParticleStatus(a2);
			System.out.println();
		
			if(a1.getBoundingPrimitive().overlap(a2.getBoundingPrimitive())){
				//System.out.println("The two particles collided");
				//break;
				double bond_length = 0.28E-9;
				double distance_x = Math.abs(a1.getPosition().x - a2.getPosition().x);
				double distance_y = Math.abs(a1.getPosition().y - a2.getPosition().y);
				double distance_z = Math.abs(a1.getPosition().z - a2.getPosition().z);
				double min_dist = Math.min(distance_x, Math.min(distance_y, distance_z));
				if(min_dist <= bond_length){
					System.out.println("The two particles collided");
					
					// Assuming perfectly elastic collision (no KE is lost)
					// http://physics.stackexchange.com/questions/81959/perfect-elastic-collision-and-velocity-transfer/81997#comment247433_81997
					// v1 = u1*(m1-m2) + 2*m2*u2 / m1+m2
					// v2 = u2*(m2-m1) + 2*m1*u1 / m1+m2
					System.out.println("a1 velocity before: "+a1.getVelocity().print());
					System.out.println("a2 velocity before: "+a2.getVelocity().print());
					Vector3D v1 = a1.calculateVelocityChange(a2.getMass(), a2.getVelocity());
					Vector3D v2 = a2.calculateVelocityChange(a1.getMass(), a1.getVelocity());
					a1.setVelocity(v1.x, v1.y, v1.z);
					a2.setVelocity(v2.x, v2.y, v2.z);
					System.out.println("a1 velocity after: "+a1.getVelocity().print());
					System.out.println("a2 velocity after: "+a2.getVelocity().print());
					/*
					if(Math.abs(min_dist-distance_x) <= machineEpsilon){
						double dist = (bond_length - distance_x)/2;	//knockback dist
						
						if(a1.getPosition().x > a2.getPosition().x){
							a1.movePositionBy(dist, dist, dist);
							a2.movePositionBy(-dist, -dist, -dist);
						}
						else {
							a2.movePositionBy(dist, dist, dist);
							a1.movePositionBy(-dist, -dist, -dist);
						}
					}
						
					else if(Math.abs(min_dist-distance_y) <= machineEpsilon){
						double dist = (bond_length - distance_y)/2;	//knockback dist
						if(a1.getPosition().y > a2.getPosition().y){
							a1.movePositionBy(dist, dist, dist);
							a2.movePositionBy(-dist, -dist, -dist);
						}
						else {
							a2.movePositionBy(dist, dist, dist);
							a1.movePositionBy(-dist, -dist, -dist);
						}
					}
					
					else if(Math.abs(min_dist-distance_z) <= machineEpsilon){
						double dist = (bond_length - distance_z)/2;	//knockback dist
						if(a1.getPosition().z > a2.getPosition().z){
							a1.movePositionBy(dist, dist, dist);
							a2.movePositionBy(-dist, -dist, -dist);
						}
						else {
							a2.movePositionBy(dist, dist, dist);
							a1.movePositionBy(-dist, -dist, -dist);
						}
	
					}
	*/
					System.out.print(i + "\t");
					printParticleStatus(a1);
					System.out.print("\t");
					printParticleStatus(a2);
					System.out.println();
				}
				
			}
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		
		
	}
	
	private static void printParticleStatus(AbstractParticle p){
		//System.out.print(p.getPosition().print()+"\t");
		System.out.println(p.getVelocity().print());	
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