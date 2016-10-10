package main;

import java.util.ArrayList;
import java.util.Random;

import structures.Coordinate;
import structures.States;

public class Scrambler {
	
	public static ArrayList<ArrayList<Coordinate>> scramble(ArrayList<ArrayList<Coordinate>> coordinates, int seed){
		ArrayList<ArrayList<Coordinate>> scrambledCoordinates = coordinates;
		Random random = new Random();
		
		for(int y = 1; y < coordinates.size()-1; y++){
			for(int x = 1; x < coordinates.get(y).size()-1; x++){
				if(random.nextInt(seed) == 0){
					switch(coordinates.get(y).get(x).getState()){
					case SOLID:
						scrambledCoordinates.get(y).get(x).setState(States.VOID);
						break;
					case VOID:
						scrambledCoordinates.get(y).get(x).setState(States.SOLID);
						break;
					default:
						break;
					
					}
				}
			}
		}
		
		return scrambledCoordinates;
	}
	
}
