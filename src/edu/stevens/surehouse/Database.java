package edu.stevens.surehouse;

import java.sql.*;
import java.util.ArrayList;

/**
 * A Database class for handling database interactions.
 */
public class Database {
	/**
	 * The DB URL used to connect to a database.
	 */
	private String DB_URL;

	/**
	 * The username to use when connecting to the database,
	 */
	private String USER;

	/**
	 * The password to use when connecting to the database.
	 */
	private String PASS;

	/**
	 * The Connection instance that handles the database connection.
	 */
	private Connection conn;

	/**
	 * A statement used for performing queries, and getting results.
	 */
	private Statement stmt;

	/**
	 * Instantiate a new instance of the Database class provided with credentials to the database.
	 * 
	 * @param host The database's host URL.
	 * @param user The username to use when connecting to the database.
	 * @param pass The password when connecting to the database.
	 */
	public Database(String host,String user,String pass){
		this.USER=user;
		this.PASS=pass;
		this.DB_URL=host;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database at " + DB_URL + "...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			System.out.println("Database Connection success!");
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Disconnect from the database.
	 */
	public void disconnect() {
		if(this.conn != null) {
			try {
				this.conn.close();
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * Add a sensor Log to the database.
	 * 
	 * @param log The sensor log to insert into the database.
	 */
	public void addLog(Log log){
		String sql;
		sql = "INSERT into sensor_logs VALUES ('" + log.getSensorId() + "','" +  log.getTime() + "','" +  (int)log.getValue() + "');";
		System.out.println(sql);
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("ERROR! Duplicate entry in database, log for sensor id="+log.getSensorId()+ " has been disreguarded. Check sample frequencies.");
			return;
		}
	}

	/**
	 * Queries the "gateways" table, and then returns an Array List of Gateway instances.
	 * 
	 * @return An ArrayList of Gateway instances.
	 */
	public ArrayList<Gateway> getGateways(){
		String sql;
		sql="Select * FROM gateways;";
		ArrayList<Gateway> gateways = new ArrayList<Gateway>();
		try {
			stmt.executeQuery(sql);
			ResultSet rs = stmt.getResultSet ();
			while (rs.next ()){
				int idVal = rs.getInt ("id");
				String nameVal = rs.getString ("name");
				String ipVal = rs.getString ("IP");
				String portVal = rs.getString ("port");
				String typeVal = rs.getString ("type");
				Gateway newGateway;
				if(typeVal.equalsIgnoreCase("eGuage")){
					newGateway = new eGauge(idVal,nameVal,ipVal+":"+portVal);
				}else{
					newGateway = new WEL(idVal,nameVal,ipVal+":"+portVal);
				}
				gateways.add(newGateway);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gateways;
	}

	/**
	 * Provided with a list of gateways as a base, this method queries the "sensors" table, and then returns 
	 * a list of all of the Sensors in the database.
	 * 
	 * @param gateways A List of Gateway instances to act as a base when querying the "sensors" table.
	 * @return An ArrayList of Sensor instances.
	 */
	public ArrayList<Sensor> getSensors(ArrayList<Gateway> gateways){
		String sql;
		sql="Select * FROM sensors where active = 1;";
		ArrayList<Sensor> sensors = new ArrayList<Sensor>();
		try {
			stmt.executeQuery(sql);
			ResultSet rs = stmt.getResultSet ();
			while (rs.next ()){
				int idVal = rs.getInt("id");
				int gidVal = rs.getInt("gateway");
				String nameVal = rs.getString ("name");
				for(int i=0;i<gateways.size();i++){
					if(gateways.get(i).getId()==gidVal){
						Sensor newdp = new Sensor(idVal,nameVal,gateways.get(i));
						sensors.add(newdp);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sensors;
	}
}
