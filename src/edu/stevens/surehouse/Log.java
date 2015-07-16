package edu.stevens.surehouse;

import java.util.Calendar;

/**
 * A class representation of a Log instance.
 */
public class Log {
	/**
	 * The value gathered from the sensor log.
	 */
	private float value;
	
	/**
	 * The sensor ID this log is for.
	 */
	private long sensor_id;
	
	/**
	 * A Calendar instance to help with time stamps.
	 */
	private Calendar time;

	/**
	 * Instantiate a new instance of a Log.
	 * 
	 * @param sensor_id The sensor ID of the sensor this Log belongs to.
	 * @param value The value of this Log.
	 */
	public Log(long sensor_id, float value){
		this.time = Calendar.getInstance();
		this.value = value;
		this.sensor_id=sensor_id;
	}
	
	/**
	 * Set the time stamp of the Log by provding a Unix time stamp.
	 * 
	 * @param timeStampUNIX The unix time stamp to set for the log.
	 */
	public void setTimeUnix(String timeStampUNIX){
		long unixTime = Long.parseLong(timeStampUNIX);        
        Calendar mydate = Calendar.getInstance();
        mydate.setTimeInMillis(unixTime*1000);
        this.time = mydate;
	}
	
	/**
	 * Set the time stamp of the log.
	 * 
	 * @param timeStamp The log's time stamp.
	 */
	public void setTime(String timeStamp){
		//"04/19/2011 15:31:03"
		Calendar mydate = Calendar.getInstance();
		int day, month, year, hour, minute, second;
		day = Integer.parseInt(timeStamp.substring(3,5));
		month = Integer.parseInt(timeStamp.substring(0,2));
		year = Integer.parseInt(timeStamp.substring(6,10));
		hour = Integer.parseInt(timeStamp.substring(11,13));
		minute = Integer.parseInt(timeStamp.substring(14,16));
		second = Integer.parseInt(timeStamp.substring(17,19));
		mydate.set(year, (month-1), day, hour, minute, second);
		this.time = mydate;
	}
	
	/**
	 * Get the time stamp's log.
	 * 
	 * @return The time stamp's log.
	 */
	public String getTime(){
		 //year-month-day h:m:s
		String year, month, day, hour, minute, buff, second;
		year = Integer.toString(this.time.get(Calendar.YEAR));
		month = Integer.toString(this.time.get(Calendar.MONTH) + 1);
		day = Integer.toString(this.time.get(Calendar.DAY_OF_MONTH));
		hour = Integer.toString(this.time.get(Calendar.HOUR_OF_DAY));
		if(this.time.get(Calendar.MINUTE) < 10){
			buff = "0";
		}else{
			buff ="";
		}
		minute = buff + Integer.toString(this.time.get(Calendar.MINUTE));
		second = Integer.toString(this.time.get(Calendar.SECOND));
		String result = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;;
		//System.out.println(result);
		return result;
	}
	
	/**
	 * Get the sensor ID of the sensor this log belongs to.
	 * 
	 * @return The sensor ID of the sensor this log belongs to.
	 */
	public long getSensorId(){
		return this.sensor_id;
	}
	
	/**
	 * Get the value of this Log.
	 * 
	 * @return The value of this Log.
	 */
	public float getValue(){
		return this.value;
	}

	@Override
	public String toString() {
		return "Log [value=" + value + ", sensor_id=" + sensor_id + ", time="
				+ time + "]";
	}
}