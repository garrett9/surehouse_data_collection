package edu.stevens.surehouse;

/**
 * A class representation of a Sensor.
 */
public class Sensor {
	
	/**
	 * The name assocaited to the sensor.
	 */
	private String name;
	
	/**
	 * The ID associated to the sensor.
	 */
	private int sensor_id;

	/**
	 * The Gateway this sensor is associated to.
	 */
	private Gateway gw;
	
	/**
	 * Insantiate a new instance of a Sensor.
	 * 
	 * @param sensor_id The ID associated to this Sensor.
	 * @param name The name associated to this Sensor.
	 * @param gw The {@link edu.stevens.surehouse.Gateway} class this sensor belongs to.
	 */
	public Sensor(int sensor_id, String name, Gateway gw){
		this.name = name;
		this.sensor_id=sensor_id;
		this.gw=gw;
		gw.addDevice(name);
	}
	
	/**
	 * Get the name of the sensor.
	 * 
	 * @return The name of the sensor.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Get the gateway this sensor belongs to.
	 * 
	 * @return The Gateway this sensor belongs to.
	 */
	public Gateway getGateway(){
		return gw;
	}
	
	/**
	 * Retrieve a single log associated to this sensor.
	 * 
	 * @return The {@link edu.stevens.surehouse.Log} retrieved from the sensor.
	 */
	public Log getLog(){
		/*
		 * This function should check if this Sensor is an eGauge, WEL, or other sensor than use
		 * those classes to extract the data needed to create a log and return it.
		 */
		Log newLog = null;
		float value;
		if(gw instanceof eGauge){
			CT newCT = (CT) gw.getDevice(name);
			value = newCT.getWatts();
			newLog = new Log(sensor_id,value);
			newLog.setTimeUnix(newCT.getLastTimeStamp());
		}else{
			OneWire newOW = (OneWire) gw.getDevice(name);
			value = newOW.getValue();
			newLog = new Log(sensor_id,value);
			newLog.setTime(newOW.getLastTimestamp());
		}
		return newLog;
	}

	@Override
	public String toString() {
		return "Sensor [name=" + name + ", sensor_id=" + sensor_id + "]";
	}
}