package sg.edu.ntu.aalhossary.fyp2014.physics_engine.core;

import java.io.PrintStream;


/**
 * @author waiyan
 * Initialization Class for the Physics Engine
 * Periodic Table used for construction of the Atom
 */
public class Init {
	public static double machineEpsilon;
	public static PrintStream originalStream;
	public static PrintStream dummyStream;
	public static String [] periodicTable =  {
		"H" , "He", "Li", "Be", "B" , "C" , "N" , "O" , "F" , "Ne",
		"Na", "Mg", "Al", "Si", "P" , "S" , "Cl", "Ar", "K" , "Ca",
		"Sc", "Ti", "V",  "Cr", "Mn", "Fe", "Co", "Ni", "Cu", "Zn",
		"Ga", "Ge", "As", "Se", "Br", "Kr", "Rb", "Sr", "Y" , "Zr",	
		"Nb", "Mo", "Tc", "Ru", "Rh", "Pd", "Ag", "Cd", "In", "Sn",	
		"Sb", "Te", "I" , "Xe", "Cs", "Ba", "La", "Ce", "Pr", "Nd",	
		"Pm", "Sm", "Eu", "Gd",	"Tb", "Dy", "Ho", "Er",	"Tm", "Yb",	
		"Lu", "Hf", "Ta", "W" ,	"Re", "Os", "Ir", "Pt",	"Au", "Hg",
		"Tl", "Pb", "Bi", "Po", "At", "Rn", "Fr", "Ra",	"Ac", "Th",	
		"Pa", "U" , "Np", "Pu",	"Am", "Cm", "Bk", "Cf", "Es", "Fm",	
		"Md", "No", "Lr", "Rf", "Db", "Sg", "Bh", "Hs", "Mt", "Ds",	
		"Rg", "Cn", "Uut", "Uuq", "Uup", "Uuh", "Uus", "Uuo"
	};
	
	public static void init(){
		machineEpsilon = calculateMachineEpsilon();
	}

	private static double calculateMachineEpsilon(){
		double machEps = 1.0;
		do{
           machEps /= 2.0;	// operator / is used instead of bit shifting since we do not know the CPU architecture
		} while ((double) (1.0 + (machEps / 2.0)) != 1.0);
        return (machEps);
	}
}
