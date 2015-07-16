package edu.stevens.surehouse;

/**
 * A class representation of a Gateway instance.
 */
public abstract class Gateway {
	/**
	 * The IP address of the gateway.
	 */
	private String ip;
	
	/**
	 * The Query parameters of the Gateway.
	 */
	private String query;
	
	/**
	 * The name of the gateway.
	 */
	private String name;
	
	/**
	 * The ID associated to this gateway.
	 */
	private int id;
	
	/**
	 * Instantiate a new instance of a Gateway class.
	 * 
	 * @param id The ID associated to this Gateway.
	 * @param ip The IP address of where this gateway is located.
	 * @param query The query parameters of the gateway.
	 * @param name The name of the gateway.
	 */
	public Gateway(int id, String ip,String query,String name){
		this.ip=ip;
		this.query=query;
		this.name=name;
		this.id=id;
	}
	
	/**
	 * Get the IP address of the gateway.
	 * 
	 * @return The IP address of the gateway.
	 */
	public String getIp(){
		return ip;
	}
	
	/**
	 * Get the query parameters of the gateway.
	 * 
	 * @return The query parameters of the gateway.
	 */
	public String getQuery(){
		return query;
	}
	
	/**
	 * Get the ID of the gateway.
	 * 
	 * @return The ID of the gateway.
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * Get the name of the gateway.
	 * 
	 * @return The name of the gateway.
	 */
	public String getName(){
		return name;
	}
	
	public abstract void addDevice(String name);
	
	public abstract Object getDevice(String name);
	
	/**
	 * Get the instantaneous data for a gateway's sensor.
	 * 
	 * @return An XML document of the instantaneous data for a gateway's sensor.
	 */
	public XML getInstantaneousData(){
		return new XML("http://"+ip+"/"+query);
	}

	@Override
	public String toString() {
		return "Gateway [ip=" + ip + ", name=" + name
				+ ", id=" + id + "]";
	}
}
