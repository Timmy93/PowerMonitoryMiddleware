package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Reader {

	private String secondsOfGeneration;
    private String jarName;
    private Process dataGenerator;
    
    private BufferedReader brStd;
    private BufferedReader brErr;
    
	public Reader(Integer seconds, String pathToGenerator){
		this.secondsOfGeneration = seconds.toString();
		this.jarName = pathToGenerator;
		//Executing the data generator
		ProcessBuilder pb = new ProcessBuilder( "java", "-jar", jarName, secondsOfGeneration );
		try {
			this.dataGenerator = pb.start();
		} catch (IOException e) {
			System.err.println("Cannot start: " + jarName );
			System.exit(0);
		}

		//Reading the standard output and the standard error
		this.brStd = new BufferedReader( new InputStreamReader( dataGenerator.getInputStream() ) );
		this.brErr = new BufferedReader( new InputStreamReader( dataGenerator.getErrorStream() ) );
	}
	
	public String readOneLine() throws IOException{
		return this.brErr.readLine();
	}
	
}
