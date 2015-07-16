package edu.stevens.surehouse;

import java.util.ArrayList;

/**
 * A class representation of an eGuage gateway.
 */
public class eGauge extends Gateway{
	/**
	 * A list of CTs associated to this gateway.
	 */
	private ArrayList<CT> CTs;
	
	/**
	 * Instantiate a new instance of the eGuage instance.
	 * 
	 * @param id The ID associated to this instance.
	 * @param name The name of the eGuage device.
	 * @param ip The IP address of where the eGuage is located.
	 */
	public eGauge(int id, String name, String ip){
		super(id,ip,"cgi-bin/egauge?inst",name);
		CTs = new ArrayList<CT>();
	}
	
	/**
	 * Add a device to the Gateway.
	 * 
	 * @param name The name of the device.
	 */
	public void addDevice(String name){
		CT newCT = new CT(name,this);
		CTs.add(newCT);
	}
	
	/**
	 * Get a device from the gateway.
	 * 
	 * @param name The name of the device.
	 */
	public CT getDevice(String name){
		for(int i=0;i<CTs.size();i++){
			if(CTs.get(i).getName().equals(name)){
				return CTs.get(i);
			}
		}
		return null;
	}
}
