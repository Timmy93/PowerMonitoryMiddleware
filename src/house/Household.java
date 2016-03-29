package house;

import java.util.ArrayList;

public class Household {

	private Integer id;
	private ArrayList<Plug> plugs;
	
	public Household(Integer householdId){
		//Create the household and relatives Plug
		this.plugs = new ArrayList<Plug>();
		this.id = householdId;
	}
	
	/**
	 * Add a plug to the house hold
	 * @param plug A string of this kind: "plug #"
	 */
	protected void addPlug(Integer plug){
		plugs.add( new Plug(plug) );
	}
	
	public Integer getHouseholdId(){
		return this.id;
	}
}
