package main;

import java.util.ArrayList;

import structures.Coordinate;
import structures.OpenList;

public class AStar {

	private ArrayList<ArrayList<Coordinate>> coordinates;
	private Coordinate start, end;
	private OpenList openList;
	private int loops;
	
	public AStar(ArrayList<ArrayList<Coordinate>> coordinates){
		this.coordinates = coordinates;
	}
	
	public ArrayList<Coordinate> solve(boolean algorithm, short startX, short startY, short endX, short endY){
		this.start = this.coordinates.get(startY).get(startX);
		this.end = this.coordinates.get(endY).get(endX);
		this.start.open(getHeurstic(this.start, this.end), (short) 0, start);
		this.openList = new OpenList(this.start, algorithm);
		this.loops = 0;
				
		ArrayList<Coordinate> path = new ArrayList<Coordinate>();
		Coordinate currentCoord;
		boolean solved = false;

		while(!solved){
			currentCoord = openList.getCurrent();
			perimeterCheck(currentCoord);
			if(checkSimilarity(currentCoord, end)){
				path.add(openList.getCurrent());
				boolean retraced = false;
				while(!retraced){
					path.add(path.get(path.size()-1).getParent());
					if(checkSimilarity(path.get(path.size()-1), start)){
						retraced = true;
						return path;
					}
				}
				solved = true;
			}
			currentCoord.close();
			openList.remove(currentCoord);
			++loops;
		}
		
		return path;
	}
	
	public void perimeterCheck(Coordinate coordinate){
		boolean up, down, left, right;
		left  = action((short)(coordinate.getX()-1), coordinate.getY(), (short)(coordinate.getTraveled()+10), coordinate);
		right = action((short)(coordinate.getX()+1), coordinate.getY(), (short)(coordinate.getTraveled()+10), coordinate);
		down  = action(coordinate.getX(), (short)(coordinate.getY()+1), (short)(coordinate.getTraveled()+10), coordinate);
		up    = action(coordinate.getX(), (short)(coordinate.getY()-1), (short)(coordinate.getTraveled()+10), coordinate);
		
		if(right && down)action((short)(coordinate.getX()+1), (short)(coordinate.getY()+1), (short)(coordinate.getTraveled()+14), coordinate);
		if(left  && down)action((short)(coordinate.getX()-1), (short)(coordinate.getY()+1), (short)(coordinate.getTraveled()+14), coordinate);
		if(right && up  )action((short)(coordinate.getX()+1), (short)(coordinate.getY()-1), (short)(coordinate.getTraveled()+14), coordinate);
		if(left  && up  )action((short)(coordinate.getX()-1), (short)(coordinate.getY()-1), (short)(coordinate.getTraveled()+14), coordinate);
	}
	
	public boolean action(short x, short y, short weight, Coordinate parent){
		Coordinate bufferCoordinate = coordinates.get(y).get(x);
		switch(bufferCoordinate.getState()){
		case SOLID:
			return false;
		case CLOSED:
			break;
		case OPEN:
			if(weight < bufferCoordinate.getTraveled()){
				bufferCoordinate.reParent(parent, weight);
			}
			break;
		case VOID:
			bufferCoordinate.open(getHeurstic(bufferCoordinate, end), weight, parent);
			openList.sortAdd(bufferCoordinate);
			break;
		}
		return true;
	}
	
	public short getHeurstic(Coordinate a, Coordinate b){
		return (short) ((Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()))*10);
	}
	
	public boolean checkSimilarity(Coordinate a, Coordinate b){
		return (a.getX() == b.getX() && a.getY() == b.getY());
	}
	
	public int getLoops(){return this.loops;}
	
}
