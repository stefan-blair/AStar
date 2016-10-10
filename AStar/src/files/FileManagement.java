package files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;

import structures.Coordinate;

public class FileManagement {
	
	public ArrayList<ArrayList<Coordinate>> getTiles(String map){
		ArrayList<ArrayList<Coordinate>>readTiles = new ArrayList<ArrayList<Coordinate>>();
		ArrayList<String>lines = new ArrayList<String>();
		
		try {

			BufferedReader reader = new BufferedReader(new FileReader(new File("map.txt")));
			String line = null;
			while((line = reader.readLine()) != null){
				lines.add(line);
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(short y = 0; y < lines.size(); y++){
			readTiles.add(new ArrayList<Coordinate>());
			for(short x = 0; x < lines.get(y).length(); x++){
				readTiles.get(y).add(new Coordinate(x, y, (short) (Short.valueOf((short)lines.get(y).charAt(x))-48)));
			}
		}
		return readTiles;
	}
	
}
