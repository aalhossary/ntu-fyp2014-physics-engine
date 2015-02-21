package sg.edu.ntu.aalhossary.fyp2014.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.biojava.bio.structure.Structure;

public class Model {

	protected String modelName = null;
	protected Structure structure;
	protected ArrayList<Molecule> molecules;
	protected HashMap<String, Atom> atomHash;
	protected Bond[] bonds;
	
	public Model() {
		molecules = new ArrayList<Molecule>();
	}
	
	public ArrayList<Molecule> getMolecules() {
		return this.molecules;
	}

	public void setMolecule(List<org.biojava.bio.structure.Chain> list) {
		AbstractParticle molecule;
		molecule = new Molecule();
		molecules.add(((Molecule)molecule));
		((Molecule)molecule).setName(list.get(0).getParent().getPdbId());
		((Molecule)molecule).setParent(this);
		((Molecule)molecule).setChains(list);
		atomHash = new HashMap<String,Atom>();
		((Molecule)molecule).setAtomHash(atomHash, modelName);
	}
	
	public void setModelName(String name){
		modelName = name;
	}

	public java.lang.String getModelName() {
		return this.modelName;
	}

	public Object[] getModelDetailList() {
		ArrayList<String> modelList = new ArrayList<String>();
		for(int i=0;i<molecules.size();i++){
			Molecule molecule = molecules.get(i);
			modelList.add(molecule.getName());
			for(int j=0;j<molecule.getChains().size();j++){
				Chain chain = molecule.getChains().get(j);
				modelList.add(chain.getName());
			}
		}
		return modelList.toArray();
	}
	
	public HashMap<String, Atom> getAtomHash(){
		return atomHash;
	}
	
	public void setBonds(ArrayList<Bond> ownbonds){
		bonds = new Bond[ownbonds.size()];
		for(int i=0;i<ownbonds.size();i++){
			bonds[i] = ownbonds.get(i);
		}
	}
	
	public Bond[] getBonds(){
		return bonds;
	}
}