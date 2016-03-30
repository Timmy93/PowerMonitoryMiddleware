package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Javadoc dani
 * @author daniele
 *
 */
public class Reader {

	private final static String output = "output";
	private final static String error = "error";
	private String secondsOfGeneration;
    private String jarName;
    private Process dataGenerator;
    private File outputFile;
    private File errorFile;
    private BufferedReader brStd;
    private BufferedReader brErr;
    
	public Reader(Integer seconds, String pathToGenerator){
		/*
		 * Set false to read directly from the output of the the process of data generation.
		 * Current bug:
		 * - With the waitFor -> Process never ends.
		 * - Without waitFor -> We never reach the end of the stream.
		 */
		boolean dataOnFile = true;
		
		this.secondsOfGeneration = seconds.toString();
		this.jarName = pathToGenerator;
		
		//Create temporary file to save outputs
		try {
			outputFile = File.createTempFile( output, "txt");
			errorFile = File.createTempFile( error, "txt");
			System.out.println( "Output file: " + outputFile.getAbsolutePath() );
		} catch (IOException e1) {
			System.err.println("Cannot create temporary Files\nTerminate execution");
			System.exit(1);
		}
		
		//Executing and wait the data generator
		ProcessBuilder pb = new ProcessBuilder( "java", "-jar", jarName, secondsOfGeneration );
		if( dataOnFile ){
			pb.redirectOutput( this.outputFile );
			pb.redirectError( this.errorFile );
		}
		try {
			this.dataGenerator = pb.start();
			if( dataOnFile ){
				this.dataGenerator.waitFor();
			}
		} catch (IOException e) {
			System.err.println("Cannot execute: " + jarName + "\nTerminate execution" );
			System.exit(2);
		} catch (InterruptedException e) {
			System.err.println("Cannot wait: " + jarName + "\nTerminate execution" );
			System.exit(3);
		}
		System.out.println("Data creation - Terminated");
		
		//Prepare reader for generated output
		if( dataOnFile ){
			try {
				this.brStd = new BufferedReader( new FileReader( outputFile ) );
				this.brErr = new BufferedReader( new FileReader( errorFile ) );
			} catch (FileNotFoundException e) {
				System.err.println("Temporary files not found\nTerminate execution" );
				System.exit(4);
			}
		} else {
			this.brStd = new BufferedReader( new InputStreamReader( dataGenerator.getInputStream() ) );
			this.brErr = new BufferedReader( new InputStreamReader( dataGenerator.getErrorStream() ) );
		}
	}
	
	public String readOneErrLine() throws IOException{
		return this.brErr.readLine();
	}
	
	public String readOneStdLine() throws IOException{
		return this.brStd.readLine();
	}
	
//	/**
//	 * Reader without buffered reader - Still has the bug
//	 * @return
//	 * @throws IOException
//	 */
//	public String readOneLine() throws IOException{
//		String myString = "";
//		char[] cbuf = {'a'};
//		while(cbuf[0] != '\n'){
//			isr.read(cbuf);
//			myString = myString + cbuf[0];
//		}
//		System.out.println(myString);
//		return myString.trim();
//	}
}
