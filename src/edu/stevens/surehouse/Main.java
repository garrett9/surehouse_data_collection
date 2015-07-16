package edu.stevens.surehouse;

import java.util.ArrayList;

/**
 * The Main class that starts the DataLogger application.
 */
public class Main {

	/**
	 * The path to the default configuration file.
	 */
	private static String CONFIG = "config.properties";
	
	/**
	 * The Main function. to the program. 
	 * 
	 * @param args Arguments to the program. The program only accepts one optional argument, which is the 
	 * path to the custom .properties file holding the database information. Otherwise, the program will 
	 * look in the same folder for a file called "config.properties".
	 */
	public static void main(String[] args) {
		/*
		 * The first step is to see if a configuration was given.
		 * If so, then we override the default configuration file.
		 */
		String config = CONFIG;
		if(args.length > 0)
			config = args[0];
		
		System.out.println("Starting the DataLogger application...");
		
		/*
		 * Next, we connect to the database through the configuration's properties.
		 * If the connection failed, we end here. Otherwise, we go query the sensors.
		 */
		Configurator myConfig = new Configurator(config);
		Database myDb = myConfig.getDatabase();
		if(myDb == null) {
			System.out.println("Failed to connect to the database!");
			return;
		}
		
		/*
		 * Then, we get the gateways and active sensors from the database to
		 * start logging.
		 */
		ArrayList<Gateway> gateways = myDb.getGateways();
		ArrayList<Sensor> sensors = myDb.getSensors(gateways);

		System.out.println("Gateways and sensors queried.");
		
		/*
		 * Only if sensors were queried, then we can continue to log the information
		 * from the sensors. Otherwise, we print a message that there were no sensors
		 * to log information for.
		 */
		if(sensors.size() > 0) {
			for(int i=0;i<gateways.size();i++){
				Logger logger = new Logger(gateways.get(i),sensors, myDb);
				logger.run();
			}
			System.out.println("Done!");
		}
		else
			System.out.println("There are no active sensors to process.");
		
		/*
		 * Finally, disconnect from the database and end execution of the program.
		 */
		myDb.disconnect();
	}
}
