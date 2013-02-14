package com.core.ks;

/** public class Timer
 * <p>
 * A timer class for getting the time passed or check if an
 * a certain time has passed.
 * 
 * @author K-S
 */
public class Timer {

	private long startTime = 0;
	private int limit = 0;
	
	/** Standard constructor for Timer. 
	 * Sets the start time to the current time. 
	 * 
	 */
	public Timer(){
		startTime = System.currentTimeMillis();
	}
	
	/** Limit constructor for Timer.
	 * Sets the start time to the current time and
	 * limit to the specified value.
	 * 
	 * @param limit - the time in milliseconds to check if time has passed
	 */
	public Timer(int limit){
		startTime = System.currentTimeMillis();
		this.limit = limit;
	}
	
	/** StartTime constructor.
	 * Sets the start time to the specified time.
	 * 
	 * @param startTime - time in milliseconds when the timer starts counting
	 */
	public Timer(long startTime){
		this.startTime = startTime;
	}
	
	/**StartTime and limit constructor.
	 * Sets the start time to the specified time and limit to
	 * the specified value.
	 * 
	 * @param startTime - time in milliseconds when the timer starts counting
	 * @param limit - the time in milliseconds to check if passed. 
	 */
	public Timer(long startTime, int limit){
		this.startTime = startTime;
		this.limit = limit;
	}
	
	/**Set the time to start counting from.
	 * 
	 * @param time - the time in milliseconds when the timer starts counting
	 */
	public void setStartTime(long time){
		startTime = time;
	}
	
	/**Set the time limit for this timer.
	 * 
	 * @param limit - the time limit in milliseconds wanted to reach. 
	 */
	public void setLimit(int limit){
		this.limit = limit;
	}
	
	/**Check if elapsed time has passed the set limit. 
	 * If limit is reached the startTime is set to the current time and 
	 * the elapsed time is zero again.
	 * 
	 * @return true if the time elapsed has passed the limit, else false. 
	 */
	public boolean passedLimit(){
		if(System.currentTimeMillis() - startTime >= limit){
			startTime = System.currentTimeMillis();
			return true;
		}
		return false;
	}
	
	/**Get the time passed from the last time. 
	 * 
	 * @return timePassed - the time elapsed from the start time.
	 */
	public int timePassed(){
		int timePassed = (int) (System.currentTimeMillis() - startTime);
		startTime = System.currentTimeMillis();
		return timePassed;
	}
	
}