package main;

import java.util.ArrayList;
import java.util.Random;

import structures.Coordinate;
import structures.States;

public class MapMaker {
	
	public static ArrayList<ArrayList<Coordinate>> createMap(int seed){
		ArrayList<ArrayList<Coordinate>> map = new ArrayList<ArrayList<Coordinate>>();
		Random random = new Random();
		int Generators[] = {2, 7, 12, 17, 22, 27, 32, 37, 42, 47, 52, 57};
		int xGen, yGen;
		
		for(int y = 0; y < 60; y++){
			map.add(new ArrayList<Coordinate>());
			for(int x = 0; x < 60; x++){
				map.get(y).add(new Coordinate((short)x, (short)y, (short)1));
			}
		}
		boolean direction;
		for(int i = 0; i < Generators.length; i++){
			xGen = Generators[i];
			direction = random.nextBoolean();
			for(yGen = 1; yGen < 59; yGen++){
				map.get(yGen).get(xGen).setState(States.VOID);
				if(random.nextInt(seed) > 2 && xGen > 1 && xGen < 58){
					if(direction)xGen++;
					else xGen--;
					if(map.get(yGen).get(xGen).getState() == States.VOID){
						direction = random.nextBoolean();
					}
					else{
						map.get(yGen).get(xGen).setState(States.VOID);
					}
				}
			}
			
			yGen = Generators[i]; 
			direction = random.nextBoolean();
			for(xGen = 1; xGen < 59; xGen++){
				map.get(yGen).get(xGen).setState(States.VOID);
				if(random.nextInt(seed) > 2 && yGen > 1 && yGen < 58){
					if(direction)yGen++;
					else yGen--;
					if(map.get(yGen).get(xGen).getState() == States.VOID){
						direction = random.nextBoolean();
					}
					else{
						map.get(yGen).get(xGen).setState(States.VOID);
					}
				}
			}
			
		}
		
		return map;
	}
	
}