package sg.edu.ntu.aalhossary.fyp2014.physics_engine.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class Atom extends sg.edu.ntu.aalhossary.fyp2014.common.Atom implements ActiveParticle{

	private double radius; 
	private double mass = 0, atomicRadius=0, covalentRadius=0, vdwRadius=0;
	private int valence = 0;
	
	public Atom(){
		super();
		this.fetchAtomicData("001");
		this.setMass(this.mass);
		this.setRadius(covalentRadius);
		this.setNetCharge(valence);
		this.boundingPrimitive = new BoundingSphere(radius, this.position);
		
	}
	
	public Atom(String atomicNumber){
		super();
		this.fetchAtomicData(atomicNumber);
		this.setMass(this.mass);
		this.setRadius(covalentRadius);
		this.setNetCharge(valence);
		this.boundingPrimitive = new BoundingSphere(radius, this.position);
	}
	
	public void setNetCharge(int charge){
		this.netCharge = charge;
	}
	
	private void setRadius (double radius){
		this.radius = radius;
	}

	private void fetchAtomicData (String atomicNumber){
		String html = "http://periodictable.com/Elements/" + atomicNumber + "/data.html";
		String output = "";
		
		try{
			Document doc = Jsoup.connect(html).get();
			
			// The website does not provide a specific header div/class id for mining. 
			// Absolute positioning is used. 
			
			Element table1 = doc.select("table").get(7);
			Element table2 = doc.select("table").get(9);
			Elements rows1 = table1.select("tr"); 
			Elements rows2 = table2.select("tr");
			
			for (int i=0; i < rows1.size(); i++) {
		        Element row = rows1.get(i);
		        Elements columns = row.select("td");
		        
		        if(columns.first().text().equals("Atomic Weight"))
		        	mass = Double.parseDouble(columns.last().text());		//mass in amu, 1 amu = 1.66053892e-24 grams
		        
		        if(columns.first().text().equals("Valence")) {
		        	valence = Integer.parseInt(columns.last().text());	
		        	break;
		        }
		        
		        // disable break and enable the following code for full data from web
		        /*
		        if(columns.size()>1)
		        	output += columns.get(0).text() + ": " + columns.get(1).text() + "\n";
		        */
		    }
			
			for (int j=0; j<rows2.size(); j++){
				 Element row = rows2.get(j);
			     Elements columns = row.select("td");
			     
			     // All radii are in pm.
			     
			     if(columns.first().text().equals("Atomic Radius"))
			    	 atomicRadius = Double.parseDouble(columns.last().text().split(" ")[0]);
			     
			     if(columns.first().text().equals("Covalent Radius"))
			    	 covalentRadius = Double.parseDouble(columns.last().text().split(" ")[0]);
			     
			     if(columns.first().text().equals("Van der Waals Radius")) {
			    	 vdwRadius = Double.parseDouble(columns.last().text().split(" ")[0]);		//mass in amu, 1 amu = 1.66053892e-24 grams
			    	 break;
			     }
			     
			     // disable break and enable the following code for full data from web
			     /*
			     if(columns.size()>1)
			     	output += columns.get(0).text() + ": " + columns.get(1).text() + "\n";
			     */
			}
			
			System.out.println(output);
			System.out.println(mass);
			System.out.println(valence);
			System.out.println(atomicRadius);
			System.out.println(covalentRadius);
			System.out.println(vdwRadius);
		}
		catch (IOException e){
			
		}
	}
	

	@Override
	public float getStretchibility() {
		return 0;
	}
}
