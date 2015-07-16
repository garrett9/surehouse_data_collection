package edu.stevens.surehouse;

import org.w3c.dom.NodeList;

/**
 * A class representation of a OneWire sensor.
 */
public class OneWire {
	/**
	 * The name of the sensor.
	 */
	private String name;
	
	/**
	 * The WEL this sensor belongs to.
	 */
	private WEL myWEL;
	
	/**
	 * The last time stamp recorded from the sensor.
	 */
	private String lastTimestamp;
	
	/**
	 * Insantiate a new instance of the OneWire clas.
	 * 
	 * @param name The name of the OneWire instance.
	 * @param myWEL The WEL this OneWire belongs to.
	 */
	public OneWire(String name, WEL myWEL){
		this.name = name;
		this.myWEL = myWEL;
	}
	
	/**
	 * Get the name associated to this OneWire.
	 * 
	 * @return The name of this OneWire.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Get the last time stamp recorded from the OneWire.
	 * 
	 * @return The last time stamp recorded from the OneWire.
	 */
	public String getLastTimestamp(){
		return lastTimestamp;
	}
	
	/**
	 * Get the value of the OneWire sensor.
	 * 
	 * @return The value of the OneWire sensor.
	 */
	public float getValue(){
		XML response = myWEL.getInstantaneousData();
		
		NodeList devices = response.getWelDevices();
		lastTimestamp = response.getWelTimeStampValue();
		for(int i=0;i<devices.getLength();i++){
			if(devices.item(i).getChildNodes().item(1).getTextContent().equals(name)){
				System.out.println(devices.item(i).getChildNodes().item(1).getTextContent());
				System.out.println(devices.item(i).getChildNodes().item(3).getTextContent());
				return Float.parseFloat(devices.item(i).getChildNodes().item(3).getTextContent());
			}
		}
		System.out.println("Could not find device '"+name+"' in XML response!");
		return -1;
	}
}
