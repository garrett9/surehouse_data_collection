package edu.stevens.surehouse;

import java.util.ArrayList;

/**
 * The Logger class performs the logging of sensor information for the sensors related
 * to a gateway.
 */
public class Logger {
	
	/**
	 * The Database instance that would allow the class to insert a log into the database.
	 */
	private Database mydb;
	
	/**
	 * The {@link edu.stevens.surehouse.Sensor} instances to log.
	 */
	private ArrayList<Sensor> sensors = new ArrayList<Sensor>();

	/**
	 * Set up a logger instance with a Gateway, Sensors, and a database object to perform database interactions.
	 * 
	 * @param gw The Gateway this logger is for.
	 * @param sensors The Sensors to log.
	 * @param mydb The Database object to perform database interactions.
	 */
	public Logger(Gateway gw, ArrayList<Sensor> sensors, Database mydb){
		this.mydb=mydb;
		for(int i=0;i<sensors.size();i++){
			if(sensors.get(i).getGateway() == gw){
				this.sensors.add(sensors.get(i));
			}
		}
	}

	/**
	 * Gather all sensor logs, and insert them into the database.
	 */
	public void run(){
		for(int i=0;i<sensors.size();i++){
			try{
				Log newlog = sensors.get(i).getLog();
				mydb.addLog(newlog);
			}catch(NullPointerException ex){
				System.out.println("ERROR: Cannot retrieve log from "+sensors.get(i).getName()+", host not accessible.");
			}
		}
	}
}