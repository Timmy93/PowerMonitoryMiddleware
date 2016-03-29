package core;

import house.House;

import java.io.IOException;
import java.util.ArrayList;

public class Start {

	/*
	 * ###BUG
	 * - With more than 10 houses the BufferedReader stops to read.
	 */
	public static void main(String[] args) {
		String pathToGenerator = "/home/timmy/Documenti/Progetti_Eclipse/Middleware/OpenMPITest/src/data-generator.jar";
		Integer secOfCollection = 3;
		long startTime = System.currentTimeMillis();
		ArrayList<House> myHouses = new ArrayList<House>();
		Reader myReader = new Reader( secOfCollection, pathToGenerator );
		long runningTime = System.currentTimeMillis();
		String readStuff = "";
		int i = 0;
		while( true ){
			try {
				readStuff = myReader.readOneLine();
				if( readStuff == null ){
					break;
				}
				myHouses.add( new House( readStuff ) );
				
				i++;
				System.out.println("house num " + i);
				
			} catch (IOException e) { System.err.println("Cannot read any line"); }
		}
		long creatingClassesTime = System.currentTimeMillis();
		System.out.println("Creation finished");
		System.out.println("Creation data time: "+ (runningTime-startTime) + " ms.");
		System.out.println("Creation world time: "+ (creatingClassesTime-runningTime) + " ms.");
		System.out.println("Total generation time: "+ (creatingClassesTime-startTime) + " ms.");
	}

}
