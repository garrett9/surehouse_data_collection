package edu.stevens.surehouse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * A class for handling configuration options of the application.
 */
public class Configurator {
	/**
	 * The .properties file where the configurations are stored.
	 */
	private String databaseConfigFileName;
	
	/**
	 * The Database instance which will handles database interactions.
	 */
	private Database db;
	
	/**
	 * Given a filename of a .properties file, this function will parse the configurations in the file,
	 * and on success, connect to the database.
	 * 
	 * @param databaseConfigFileName The filename of hte .properties file.
	 */
	public Configurator(String databaseConfigFileName) {
		this.db = null;
		this.databaseConfigFileName=databaseConfigFileName;
		parseDatabaseConfigFile();
	}

	/**
	 * Retrieve the Database instance after instantiating the Configurator.
	 * 
	 * @return The Database instance.
	 */
	public Database getDatabase(){
		return db;
	}

	/**
	 * Parse the configuration file passed in the class's constructor.
	 */
	private void parseDatabaseConfigFile(){
		Properties props = new Properties();
		InputStream in = null;
		
		try {
			in = new FileInputStream(this.databaseConfigFileName);
			props.load(in);
			
			String name = props.getProperty("DB_NAME");
			String host = props.getProperty("DB_HOST");
			String user = props.getProperty("DB_USER");
			String password = props.getProperty("DB_PASSWORD");
			
			host = "jdbc:mysql://" + host + "/" + name;
			
			this.db = new Database(host, user, password);
		} catch(IOException e) {
			System.out.println("Error opening .properties file: " + databaseConfigFileName);
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch(IOException e) {
				}
			}
		}
	}
}
