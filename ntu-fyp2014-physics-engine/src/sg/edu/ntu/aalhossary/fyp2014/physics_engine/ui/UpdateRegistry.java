package sg.edu.ntu.aalhossary.fyp2014.physics_engine.ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.io.PDBFileReader;
import org.jmol.api.JmolViewer;
import org.jmol.java.BS;
import org.jmol.viewer.ActionManager;
import org.jmol.viewer.DataManager;
import org.jmol.viewer.Viewer;

import sg.edu.ntu.aalhossary.fyp2014.common.AbstractParticle;
import sg.edu.ntu.aalhossary.fyp2014.common.Atom;
import sg.edu.ntu.aalhossary.fyp2014.common.Bond;
import sg.edu.ntu.aalhossary.fyp2014.common.Model;
import sg.edu.ntu.aalhossary.fyp2014.common.Molecule;
import sg.edu.ntu.aalhossary.fyp2014.moleculeeditor.core.MoleculeEditor;
import sg.edu.ntu.aalhossary.fyp2014.moleculeeditor.ui.ToolPanel;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.Vector3D;
import sg.edu.ntu.aalhossary.fyp2014.physics_engine.core.World;

public class UpdateRegistry {
	Viewer viewer;
	ToolPanel toolPanel;
	List<Model> modelList;
	DataManager dataMgr;
	private ArrayList<Atom> selectedAtoms;
	protected int dragMode;
	
	public UpdateRegistry(Viewer viewer, ToolPanel toolPanel, List<Model> models){
		this.viewer = viewer;
		this.modelList = models;
		this.toolPanel = toolPanel;
		this.dataMgr = new DataManager();
		this.dragMode = -1;
	}
	
	public UpdateRegistry(Viewer viewer, List<Model> models){
		this.viewer = viewer;
		this.modelList = models;
		this.dataMgr = new DataManager();
		this.dragMode = -1;
	}
	
	public UpdateRegistry(){
		this.viewer = null;
		this.modelList = new ArrayList<Model>();
		this.toolPanel = null;
		this.dataMgr = new DataManager();
		this.dragMode = -1;
	}
	
	public void displayParticles(ArrayList<AbstractParticle> list){
		DecimalFormat decformat = new DecimalFormat("#.###");
		int index = 1;
		String[] coord;
		String pdb = "MODEL       1\n";
		for(int i=0; i<list.size();i++){
			if(list.get(i) instanceof Atom){
				double scale = Math.pow(10, 10 + list.get(i).getPosition().metric);
				coord = new String[3];
				coord[0] = decformat.format(list.get(i).getPosition().x);
				coord[1] = decformat.format(list.get(i).getPosition().y);
				coord[2] = decformat.format(list.get(i).getPosition().z);
				Vector3D position = list.get(i).getPosition();
				String coords= String.format("%8.3f%8.3f%8.3f", position.x*scale,position.y*scale,position.z*scale);
				String elementSymbol = ((Atom)list.get(i)).getElementSymbol().toUpperCase();
				pdb += "HETATM    "+index+" "+elementSymbol+"   TST A   1    "+coords+"  1.00  0.00\n";
				index++;
			}
			else if (list.get(i) instanceof Molecule){
				Molecule m = (Molecule)list.get(i);
				if(World.simulationLvlAtomic == true) {
					for(Atom a: m.getChains().get(0).atomSeq){
						double scale = Math.pow(10, 10 + list.get(i).getPosition().metric);
						coord = new String[3];
						coord[0] = decformat.format(a.getPosition().x);
						coord[1] = decformat.format(a.getPosition().y);
						coord[2] = decformat.format(a.getPosition().z);
						Vector3D position = a.getPosition();
						String coords= String.format("%8.3f%8.3f%8.3f", position.x*scale,position.y*scale,position.z*scale);
						String elementSymbol = a.getElementSymbol().toUpperCase();
						pdb += "HETATM    "+index+" "+elementSymbol+"   TST A   1    "+coords+"  1.00  0.00\n";
						index++;
					}
				
					/*
					pdb += "HETATM    1 NA   TST A   1       5.000   5.000   5.000  1.00  0.00\n"+
						   "HETATM    2 CL   TST A   1       6.400   6.400   6.400  1.00  0.00\n"+
						   "CONECT    1      2\n" +
						   "CONECT    2      1\n";
						   */
				}
				else {
					double scale = Math.pow(10, 10 + list.get(i).getPosition().metric);
					coord = new String[3];
					coord[0] = decformat.format(list.get(i).getPosition().x);
					coord[1] = decformat.format(list.get(i).getPosition().y);
					coord[2] = decformat.format(list.get(i).getPosition().z);
					Vector3D position = list.get(i).getPosition();
					String coords= String.format("%8.3f%8.3f%8.3f", position.x*scale,position.y*scale,position.z*scale);
					pdb += "HETATM    1 AR   TST A   1    "+coords+"  1.00  0.00\n";
				}
			}
		}
	//	pdb += "CONECT    1      2\n";
	//	pdb += "CONECT    2      1\n";
		pdb += "ENDMDL";
		viewer.openStringInline(pdb);
		try {
			System.out.println("here hehrehr hehrehrhehr");
			BufferedWriter writer = new BufferedWriter(new FileWriter("temp.pdb"));
			writer.write(pdb);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createUserModel(readPDBFile("temp.pdb"));
	}
	
	

	/******************************************/
	/*** Private methods used in class only ***/
	/******************************************/
	
	public void createUserModel(Structure struc) {
		// read default MMFFtypes for atoms
		
		if(modelList==null)
			modelList = new ArrayList<Model>();
		Model model=null;
		for(int i=0;i<struc.nrModels();i++){
			// for each model, create and add to model list
			model = new Model();
			model.setModelName("Model"+(i+1));
			model.setMolecule(struc.getModel(i));	// set the model
			modelList.add(model);
		}
	}
	
	public List<Model> getModelList() {
		return modelList;
	}
	
	public static Structure readPDBFile(String fileName) {
		// wrapper class for parsing a PDB file.
		PDBFileReader pdbreader = new PDBFileReader();
		Structure struc = null; 
		//Model model = null;
		
		try{
			// Access to the data of a PDB file.
			struc = pdbreader.getStructure(fileName);	    
		 } catch (Exception e){
			 java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MoleculeEditor.class.getName());
			 logger.log(Level.SEVERE, "Error converting to structure object from file path");
		 }
		return struc;
	}
}
