import java.util.Comparator;


public class LineGraphComp implements Comparator<LineGraphData> {


	public int compare(LineGraphData arg0, LineGraphData arg1) {
		if (arg0.hour < arg1.hour)
			return -1;
		if (arg0.hour > arg1.hour)
			return 1;
		if (arg0.minute < arg1.minute)
			return -1;
		if (arg0.minute > arg1.minute)
			return 1;
		if (arg0.second < arg1.second)
			return -1;
		if (arg0.second > arg1.second)
			return 1;
		if (arg0.millis < arg1.millis)
			return -1;
		if (arg0.millis > arg1.millis)
			return 1;
		return 0;
	}

}
