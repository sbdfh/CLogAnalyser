import java.util.*;

public class piechart_tester {
	
	public static void main(String[] args) {
		
		HashMap<String,Integer> data = new HashMap<String,Integer> ();
		
		data.put("a", 1744);
		data.put("b", 3100);
		data.put("c", 4711);
		data.put("d", 129);
		data.put("e", 3349);
		
		piechart_template piechart =  new piechart_template(data);
	
	}	
	
}
