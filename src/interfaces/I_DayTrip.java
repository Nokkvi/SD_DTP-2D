package interfaces;
import Classes.*;

public interface I_DayTrip {
	
	public IndivDayTrip selectIndiv(int i);
	public void addKeyword(String s);
	public void editDesc(String s);
	public Rating rate(int r, String c);
	
}
