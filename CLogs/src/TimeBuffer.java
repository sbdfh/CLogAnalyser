import java.util.LinkedList;
import java.util.Queue;


public class TimeBuffer {

	protected static final int SPAN = 10;
	
	protected class Pair{
		public int time, amount;
	}
	
	protected Queue<Pair> queue;
	protected int buffer = 0;
	
	public TimeBuffer(){
		queue = new LinkedList<Pair>();
	}
	
	public void put (int time, int amount){
		Pair p = new Pair();
		p.time = time;
		p.amount = amount;
		buffer += amount;
		queue.offer(p);
	}
	
	public float giveDPS(int now){		
		while (!queue.isEmpty() && now - queue.peek().time > SPAN*1000)
			buffer -= queue.poll().amount;					
		return buffer / (float)SPAN;
	}
	
}
