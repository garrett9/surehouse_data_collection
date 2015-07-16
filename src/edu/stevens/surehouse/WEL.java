package edu.stevens.surehouse;

import java.util.ArrayList;

/**
 * A class representation of a WEL. This class extends the Gateway class.
 */
public class WEL extends Gateway{
	/**
	 * The list of {@link edu.stevens.surehouse.OneWire} devices associated to this WEL.
	 */
	private ArrayList<OneWire> devices;
	
	/**
	 * Instantiate a new instance of a WEL object.
	 * 
	 * @param id The gateway ID associated to the WEL.
	 * @param name The name associated to the WEL.
	 * @param ip The IP address associated to the WEL.
	 */
	public WEL(int id, String name, String ip){
		super(id,ip,"wel.xml",name);
		devices = new ArrayList<OneWire>();
	}
	
	/**
	 * Add a new device to the WEL object.
	 * 
	 * @param name The name of the device.
	 */
	public void addDevice(String name){
		OneWire newDevice = new OneWire(name, this);
		devices.add(newDevice);
	}
	
	/**
	 * Retrieve a {@link edu.stevens.surehouse.OneWire} associated to this WEL object.
	 * 
	 * @param name The name of the device.
	 */
	public OneWire getDevice(String name){
		for(int i=0;i<devices.size();i++){
			if(devices.get(i).getName().equals(name)){
				return devices.get(i);
			}
		}
		System.out.println("WEL device '" + name + "' not found!");
		return null;
	}
}
