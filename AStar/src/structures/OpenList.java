package structures;

import java.util.ArrayList;

public class OpenList extends ArrayList<Coordinate>{
		
	boolean type;
	public OpenList(Coordinate start, boolean type){
		this.add(start);
		this.type = type;
	}
	
	private int split(int start, int end, int a){
		int average = (start+end)/2;
		int index = average;
		if(start == end){
			return index;
		}
		else if(a == getValue(this.get(index))){
			return ++index;
		}
		else if(a < getValue(this.get(index))){
			index = split(start, average, a);
		}
		else if(a > getValue(this.get(index))){
			index = split(++average, end, a);
		}
		
		return index;
	}
	
	public void sortAdd(Coordinate a){
		this.add(split(0, this.size(), getValue(a)), a);
	}
	public int getValue(Coordinate a){
		if(type){
			return a.getFScore();
		}
		else{
			return a.getTraveled();
		}
	}
	public Coordinate getCurrent(){return this.get(0);}
}
