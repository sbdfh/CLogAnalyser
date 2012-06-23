import java.util.HashMap;
import java.util.ListIterator;


public class CLogTester {

	public static void main(String[] args){
		CLogParser p = new CLogParser("../../git/CLogAnalyser/CLogs/FullCLogExample.txt");
		
		p.parse();		
	}
	
}
