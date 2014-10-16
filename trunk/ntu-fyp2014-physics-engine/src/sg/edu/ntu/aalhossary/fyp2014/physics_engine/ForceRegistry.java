package sg.edu.ntu.aalhossary.fyp2014.physics_engine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class ForceRegistry {
	private HashMap<Particle, ArrayList<Force>> registrations = new HashMap<Particle,ArrayList<Force>>();
	
	public void add(Particle particle, Force force) {
		
		if(registrations.get(particle) == null){
			ArrayList<Force> forces = new ArrayList<Force>();
			forces.add(force);
			registrations.put(particle, forces);
		}
		else
			registrations.get(particle).add(force);
	}

	public void remove(Particle particle, Force force) {
		ArrayList<Force> forces = registrations.get(particle);
		if(forces!=null){
			forces.remove(force);
			Force cancelForce = new Force (-force.x, -force.y, -force.z);
			particle.addForce(cancelForce);
		}	
	}

	public void removeAllForceFrom (Particle particle) {
		ArrayList<Force> forces = registrations.get(particle);
		if(forces!=null)
			forces.clear();
		particle.clearAccumulator();
	}
	
	public void clear(){
		registrations.clear();
	}
	
	public void updateForce(Particle particle){
		ArrayList<Force> forces = registrations.get(particle);
		if(forces != null)
			for (Force force: forces)
				particle.addForce(force);
	}
	
	public void updateAllForces(){
		for (Entry<Particle, ArrayList<Force>> entry: registrations.entrySet()){
			Particle particle = entry.getKey();
			updateForce(particle);
		}
	}

}