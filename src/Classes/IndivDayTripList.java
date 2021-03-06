package classes;
//import interfaces.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class IndivDayTripList{
	
	public static void main(String[] args) {
		System.out.println("�etta er IndivDayTripList Klasinn");

	}

	//@Override
	public static void addIndivDayTrip(IndivDayTrip d) {
		Database.connectToDatabase();
		int id = d.getId();
		Date startD = d.getStartTime();
		Date endD = d.getEndTime();
		DayTrip parent = d.getParent();
		int numSeatsAvail = d.getNumSeatsAvail();
		
		String sdStr = startD.toString();
		String edStr = endD.toString();
		String parentStr = parent.getName();

		String inject = "INSERT INTO INDIVDAYTRIPS(ID,STARTTIME,ENDTIME,DAYTRIP,NUMSEATSAVAIL) "
						+ "VALUES('" + id + "', '" + sdStr + "', '" + edStr + "', '" + parentStr + "', '"
						+ numSeatsAvail + "');";
		
    	Database.insert(inject);
		Database.closeDatabase();
	}

	//@Override
    @SuppressWarnings("deprecation")
	public static IndivDayTrip[] pullIndivDayTrip() {
        Database.connectToDatabase();
        ResultSet rs = Database.getTable("indivDaytrips");
        List<IndivDayTrip> result = new ArrayList<IndivDayTrip>();
        try {
            //System.out.println(rs.getString(15));
        	int k = 0;
            while (rs.next()){
            	int id = rs.getInt(1);
            	Date Sd = rs.getDate(2);
            	Date Ed = rs.getDate(3);
            	String parent = rs.getString(4);
            	int numSeats = rs.getInt(5);           	
          
            	
            	ResultSet rsp = Database.execute("SELECT * FROM Daytrips WHERE name='"+parent+"';");
            	if (rsp.next()){
                    String name = rsp.getString(1);
                    String company = rsp.getString(2);
                    double rating = rsp.getFloat(3);
                    int price = rsp.getInt(4);
                    String description = rsp.getString(5);
                    String[] keywords = rsp.getString(6).split(" - ");
                    String category = rsp.getString(7);
                    String[] pickup = rsp.getString(8).split(", ");
                    String location = rsp.getString(9);
                    DayTrip parent_o = new DayTrip(name, category, company, pickup, keywords, location, description, price, rating);
                    result.add(new IndivDayTrip(id, Sd, Ed, numSeats, parent_o));
                    k++;
            	} else {
            	    System.out.println("Parent "+parent+" not found!!!?!!!?!!");
            	}
            }
            IndivDayTrip[] truOp = new IndivDayTrip[k];
            Database.closeDatabase();
            return result.toArray(truOp);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Database.closeDatabase();
            return null;
        }
    }

	//@Override
	public static void removeIndivDayTrip(IndivDayTrip d) {
		Database.connectToDatabase();
		int id = d.getId();
		String cond = "id = "+id;
		ResultSet rs = Database.deleteEntry("indivDaytrips", cond);
		Database.closeDatabase();
	}

}
