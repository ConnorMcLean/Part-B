package automail;

public class Building {
	
	
    /** The number of floors in the building **/
    private static int FLOORS;
    
    /** Represents the ground floor location */
    public static  int LOWEST_FLOOR = 1;
    
    /** Represents the mailroom location */
    private static  int MAILROOM_LOCATION = 1;
    
public Building() {
	//commit test
    	
    }
    public Building(int Floor, int Mailroom_Location) {
    	FLOORS = Floor;
    	MAILROOM_LOCATION = Mailroom_Location;
    }
    public static int getBuildingFloor() {
    	return FLOORS;
    }
    public static int getMailroom_Location() {
    	return MAILROOM_LOCATION;
    }
    
    public static void setFloor(int FloorCount) {
    	FLOORS = FloorCount;
    }
    
}
