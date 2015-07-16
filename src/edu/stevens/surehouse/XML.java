package edu.stevens.surehouse;

import java.util.Calendar;

import javax.xml.parsers.*;

import org.w3c.dom.*;

/**
 * A class for handling the XML parsing of sensor information queried from gateways.
 */
public class XML
{
	/**
	 * The XML document that will be parsed.
	 */
	private Document doc;

	/**
	 * The URL to the XML document.
	 */
	private String url;

	/**
	 * The constructor accepts a URL to an XML document, and parses it for later use.
	 * 
	 * @param url The URL to the XML document.
	 */
	public XML(String url)
	{
		this.url = url;
		try
		{
			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			DocumentBuilder b = f.newDocumentBuilder();
			this.doc = b.parse(this.url);
			doc.getDocumentElement().normalize();
		}
		catch (Exception e)
		{
			System.out.println("CONNECTION ERROR: Cannot reach '"+url+"'");
			//e.printStackTrace();
		}
	}

	/**
	 * Get the Root element of the document.
	 * 
	 * @return The Root element of the document.
	 */
	public String getRootElemValue(){
		return doc.getDocumentElement().getNodeName();
	}

	/**
	 * Get the time stamp value from the XML document.
	 * 
	 * @return The time stamp value from the XML document.
	 */
	public String getTimeStampValue(){       
		NodeList items = doc.getElementsByTagName("ts");
		if(items.getLength()>0){
			Element timeStamp = (Element)items.item(0);
			String timeStampVal = timeStamp.getChildNodes().item(0).getNodeValue();
			return timeStampVal;
		}else{
			System.out.println("Timestamp element not found!");
			return null;
		}
	}

	/**
	 * Convert a unix time stamp to a Calendar instance.
	 * 
	 * @param timeStampUNIX The unix time stamp.
	 * @return The Calendar instance after converting the unix time stamp.
	 */
	public Calendar unixToCalendar(String timeStampUNIX){
		long unixTime = Long.parseLong(timeStampUNIX);        
		Calendar mydate = Calendar.getInstance();
		mydate.setTimeInMillis(unixTime*1000);
		return mydate;
	}

	/**
	 * Given a time stamp element, this method returns the value associated to it.
	 * 
	 * @param timeStamp The time stamp element to get a value for.
	 * @return The value returned for the given time stamp.
	 */
	private String getElemValue(Element timeStamp){
		if(timeStamp!=null){
			Node n = timeStamp.getChildNodes().item(0);
			return n.getNodeValue();
		}else{
			return "NULL";
		}
	}

	/**
	 * Returns a list of meter nodes for all meters in the current document.
	 * 
	 * @return A NodeList of meter nodes.
	 */
	public NodeList getMeterNodes(){
		if(doc.getElementsByTagName("r").getLength()>0){
			return doc.getElementsByTagName("r");
		}else{
			System.out.println("No meters found!");
			return null;
		}
	}

	/**
	 * Returns a list of WEL device nodes for all meters in the current document (XML response)
	 * 
	 * @return A NodeList of Wel Devices.
	 */
	public NodeList getWelDevices(){
		/*
		 * Returns a list of WEL device nodes for all meters in the current document (XML response)
		 */
		if(doc.getElementsByTagName("device").getLength()>0){
			return doc.getElementsByTagName("device");
		}else{
			System.out.println("No Wel devices found!");
			return null;
		}
	}

	/**
	 * Get a time stamp value of a WEL device.
	 * 
	 * @return The WEL time stamp value.
	 */
	public String getWelTimeStampValue(){
		NodeList nodes = getWelDevices();
		String date = nodes.item(0).getChildNodes().item(3).getTextContent();
		//System.out.println(date);
		String time = nodes.item(1).getChildNodes().item(3).getTextContent();
		//System.out.println(time);
		if(date != null && time != null){
			return date + " " + time;
		}else{
			System.out.println("No timestamp found on Wel device! " + date + " " + time);
			return null;
		}
	}

	/**
	 * Converts an XML Meter Element to an array of String.
	 * 
	 * @param Meter The Meter Element.
	 * @return An array of strings.
	 */
	public String[] getMeterValues(Element Meter){
		String[] result = new String[3];
		Element Meter1_sub = (Element) Meter.getChildNodes().item(0);
		result[0]=getElemValue(Meter1_sub);
		Element Meter2_sub = (Element) Meter.getChildNodes().item(1);
		result[1]=getElemValue(Meter2_sub);

		return result;
	}

	/**
	 * Get a device's value given a device Element.
	 * 
	 * @param device The device Element.
	 * @return The device's value.
	 */
	public String getDeviceValue(Element device){

		Element d = (Element) device.getChildNodes().item(1);
		return getElemValue(d);
	}

	/**
	 * Displays the three values associated with a particular meter in a neat fashion for debugging purposes.
	 * 
	 * @param meter A Meter Element.
	 */
	public void displayMeterInfo(Element meter){
		String meterName = meter.getAttribute("n");
		Element meter1_sub1 = (Element) meter.getChildNodes().item(0);
		String energy = getElemValue(meter1_sub1);
		Element meter1_sub2 = (Element) meter.getChildNodes().item(1);
		String current = getElemValue(meter1_sub2);

		System.out.println("\nMeter Info: "+meterName);
		System.out.println("Cumulative Energy (Watt Hours): "+energy);
		System.out.println("Instantaneous Power: "+current);
	}
}