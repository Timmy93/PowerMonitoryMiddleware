package core;

import house.House;

import java.io.IOException;
import java.util.ArrayList;

public class Start {

	/*
	 * ###BUG
	 */
	public static void main(String[] args) {
		String pathToGenerator = "/home/timmy/Documenti/Progetti_Eclipse/Middleware/OpenMPITest/src/data-generator.jar";
		Integer secOfCollection = 1500;
		long startTime = System.currentTimeMillis();
		ArrayList<House> myHouses = new ArrayList<House>();
		
		//Here I generate data and prepare the reader.
		Reader myReader = new Reader( secOfCollection, pathToGenerator );
		long runningTime = System.currentTimeMillis();
		
		//Generate the world
		generateWorld(myReader, myHouses);
		
		
		long creatingClassesTime = System.currentTimeMillis();
		System.out.println("Creation finished");
		System.out.println("Creation data time: "+ (runningTime-startTime) + " ms.");
		System.out.println("Creation world time: "+ (creatingClassesTime-runningTime) + " ms.");
		System.out.println("Total generation time: "+ (creatingClassesTime-startTime) + " ms.");
	}

	/**
	 * Read data from stdErr and generates the world.
	 * @param myReader	Where I take information.
	 * @param myHouses	Where I collect the houses read.
	 */
	private static void generateWorld(Reader myReader, ArrayList<House> myHouses) {
		String readStuff = "";
		while( !finishedToRead( readStuff ) ){
			try {
				readStuff = myReader.readOneErrLine();
				if( !finishedToRead( readStuff ) ){
					myHouses.add( new House( readStuff ) );
				}
			} catch (IOException e) { 
				System.err.println("Cannot read any line");
			}
		}
	}

	/**
	 * Evaluates if the end of file is reached.
	 * @param readStuff	What has been read.
	 * @return	true -> There isn't nothing to read.
	 */
	private static boolean finishedToRead(String readStuff) {
		return readStuff == null;
	}

}
