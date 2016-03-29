package house;

import java.util.ArrayList;

public class House {

	private Integer id;
	private ArrayList<Household> hH;
	
	public House(String info){
		//Split info
		info = purgeInfo( info );
//		System.out.println(info);
		String[] splittedStuff = info.split("-");
		//Create the house and relatives household
		this.hH = new ArrayList<Household>();
		
		for( int i = 0; i < splittedStuff.length; i++ ){
			manageString( splittedStuff[i] );
		}
		
	}
	
	/**
	 * Clean the string before use it
	 * @param info	The string to be cleared.
	 * @return	The cleared string
	 */
	private String purgeInfo(String info) {
		info = info.replace("}, ", "");
		info = info.replace("}", "");
		info = info.replace(", ", "-");
		info = info.replace(" {", "-");
		return info;
	}

	/**
	 * Check the splitted string.
	 * @param entry	A clean string.
	 */
	private void manageString(String entry){
		if( entry.startsWith("house ") ){
			extractHouseId(entry);
//			System.out.println( "house: " + entry.split(" ")[1] );
		}
		else if( entry.startsWith("household") ){
			extractHouseholdId(entry);
//			System.out.println( "household : " + entry.split(" ")[1] );
		}
		else if( entry.startsWith("plug") ){
			extractPlugId( entry );
//			System.out.println( "plug : " + entry.split(" ")[1] );
		}
		else{
			System.err.println("Error: ["+ entry + "]");
		}
	}
	
	/**
	 * Add the new plugId to the last household extracted.
	 * @param entry
	 */
	private void extractPlugId(String entry) {
		Integer myId = extractId( entry );
		Household lastHH = this.hH.get( hH.size()-1 );
		lastHH.addPlug( myId );
	}

	/**
	 * Add a new household to the house
	 * @param entry
	 */
	private void extractHouseholdId(String entry) {
		Integer myId = extractId( entry );
		hH.add( new Household(myId) );
	}

	/**
	 * Extract the house id.
	 * @param entry
	 */
	private void extractHouseId(String entry ){
		Integer myId = extractId( entry );
		this.id = myId;
	}
	
	/**
	 * From the cleaned string extract the integer.
	 * @param cleanString	The string containing the id.
	 * @return	The id ad an Integer.
	 */
	private Integer extractId(String cleanString){
		String numericalString = cleanString.split(" ")[1];
		return Integer.parseInt( numericalString );
	}
}
