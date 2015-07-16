package edu.stevens.surehouse;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * A class representation of a CT.
 */
public class CT {
	/**
	 * The name associated to the CT.
	 */
	private String name;
	
	/**
	 * The eGuage instance the CT belongs to.
	 */
	private eGauge myeGauge;
	
	/**
	 * The last time stamp recovered from the CT.
	 */
	private String lastTimestamp;
	
	/**
	 * Instantiate a new instance of a CT.
	 * 
	 * @param name The name associated to the CT.
	 * @param myeGauge The eGuage instance this CT belongs to.
	 */
	public CT(String name, eGauge myeGauge){
		this.name = name;
		this.myeGauge = myeGauge;
	}

	/**
	 * Get the Watts of the CT.
	 * 
	 * @return The Watts of the CT.
	 */
	public float getWatts(){
		String values[]={"NULL","NULL"};
		XML responseTest = myeGauge.getInstantaneousData();
		NodeList meters = responseTest.getMeterNodes();
		lastTimestamp = responseTest.getTimeStampValue();
		for(int i=0;i<meters.getLength();i++){
			Element meter = (Element) meters.item(i);
			if(meter.getAttributeNode("n").getTextContent().equals(name)){
				values = responseTest.getMeterValues((Element) meters.item(i));
			}
		}
		return Float.valueOf(values[1]);
	}
	
	/**
	 * Get the total Watts of the CT.
	 * 
	 * @return The total Watts of the CT.
	 */
	public long getTotalWatts(){
		String values[]={"NULL","NULL"};
		XML responseTest = myeGauge.getInstantaneousData();
		NodeList meters = responseTest.getMeterNodes();
		for(int i=0;i<meters.getLength();i++){
			if(meters.item(i).equals(name)){
				values = responseTest.getMeterValues((Element) meters.item(i));
			}
		}
		System.out.println(values[0]);
		return Long.valueOf(values[0]);
	}
	
	/**
	 * Get the name associated to the CT.
	 * 
	 * @return The name associated to the CT.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Get the last time stamp of the CT.
	 * 
	 * @return The last time stamp of the CT.
	 */
	public String getLastTimeStamp(){
		return lastTimestamp;
	}
}
