package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import structures.Coordinate;
import structures.States;
import files.FileManagement;

public class GUI extends JPanel implements MouseListener{

	public static final int MAPCOMPLEXITY = 3;
	public static final int TILESIZE = 14;
	public static final int WIDTH = (TILESIZE*60);
	public static final int HEIGHT = (TILESIZE*60);
	
	int startX, startY, endX, endY;
	boolean algorithm = true;
	
	ArrayList<ArrayList<Coordinate>> tiles, tilesBackup;
	ArrayList<Coordinate> path;
	FileManagement fileManagement;
	
	AStar astar;
	
	public GUI(){
		addMouseListener(this);
		setFocusable(true);
		
		startX = 2;
		startY = 1;
		endX = 2;
		endY = 1;
		
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		tiles = new ArrayList<ArrayList<Coordinate>>();
		tilesBackup = new ArrayList<ArrayList<Coordinate>>();
		fileManagement = new FileManagement();
		
		tiles = MapMaker.createMap(MAPCOMPLEXITY);
		
		for(int y2 = 0; y2 < tiles.size(); y2++){
			tilesBackup.add(new ArrayList<Coordinate>());
			for(int x2 = 0; x2 < tiles.get(y2).size(); x2++){
				switch(tiles.get(y2).get(x2).getState()){
				case SOLID:
					tilesBackup.get(y2).add(new Coordinate((short)x2, (short)y2, (short)1));
					break;
				case VOID:
					tilesBackup.get(y2).add(new Coordinate((short)x2, (short)y2, (short)0));
					break;
				
				}
			}
		}
//		tilesBackup = tiles;
		astar = new AStar(tiles);
//		path = astar.solve(algorithm, (short)startX, (short)startY, (short)endX, (short)endY);
		int i = 0;
		int dif = 0;
		for(int y = 0; y < tiles.size(); y++){
			for(int x = 0; x < tiles.get(y).size(); x++){
				if(tiles.get(y).get(x).getState() == States.VOID){
					endX = x;
					endY = y;
					
//					System.out.println(endX + " , " + endY);
				
					tiles = new ArrayList<ArrayList<Coordinate>>();
					
					for(int y2 = 0; y2 < tilesBackup.size(); y2++){
						tiles.add(new ArrayList<Coordinate>());
						for(int x2 = 0; x2 < tilesBackup.get(y2).size(); x2++){
							switch(tilesBackup.get(y2).get(x2).getState()){
							case SOLID:
								tiles.get(y2).add(new Coordinate((short)x2, (short)y2, (short)1));
								break;
							case VOID:
								tiles.get(y2).add(new Coordinate((short)x2, (short)y2, (short)0));
								break;
							
							}
						}
					}
					astar = new AStar(tiles);
					path = astar.solve(true, (short)startX, (short)startY, (short)endX, (short)endY);
//					System.out.print(i+", " + MAPCOMPLEXITY+", " +path.get(0).getTraveled() + ", ");
					dif = path.get(0).getTraveled();
					tiles = new ArrayList<ArrayList<Coordinate>>();
					
					for(int y2 = 0; y2 < tilesBackup.size(); y2++){
						tiles.add(new ArrayList<Coordinate>());
						for(int x2 = 0; x2 < tilesBackup.get(y2).size(); x2++){
							switch(tilesBackup.get(y2).get(x2).getState()){
							case SOLID:
								tiles.get(y2).add(new Coordinate((short)x2, (short)y2, (short)1));
								break;
							case VOID:
								tiles.get(y2).add(new Coordinate((short)x2, (short)y2, (short)0));
								break;
							
							}
						}
					}
					astar = new AStar(tiles);
					path = astar.solve(false, (short)startX, (short)startY, (short)endX, (short)endY);
//					System.out.println(path.get(0).getTraveled()+",");
					dif-=path.get(0).getTraveled();
					tiles = new ArrayList<ArrayList<Coordinate>>();
					
					for(int y2 = 0; y2 < tilesBackup.size(); y2++){
						tiles.add(new ArrayList<Coordinate>());
						for(int x2 = 0; x2 < tilesBackup.get(y2).size(); x2++){
							switch(tilesBackup.get(y2).get(x2).getState()){
							case SOLID:
								tiles.get(y2).add(new Coordinate((short)x2, (short)y2, (short)1));
								break;
							case VOID:
								tiles.get(y2).add(new Coordinate((short)x2, (short)y2, (short)0));
								break;
							
							}
						}
					}
					++i;
				}
				else dif = -1;
			System.out.print(dif+",");
			}
			System.out.println("");
		}
		
	}
	
	public void paint(Graphics g){
		super.paint(g);
		for(int y = 0; y < tiles.size(); y++){
			for(int x = 0; x < tiles.get(y).size(); x++){
				switch(tiles.get(y).get(x).getState()){
				case OPEN:
					g.setColor(Color.white);
					break;
				case VOID:
					g.setColor(Color.black);
					break;
				case CLOSED:
					g.setColor(Color.orange);
					break;
				case SOLID:
					g.setColor(Color.gray);
					break;
				}
				g.fillRect(tiles.get(y).get(x).getX()*TILESIZE, tiles.get(y).get(x).getY()*TILESIZE, this.TILESIZE, this.TILESIZE);
			}
		}
		g.setColor(Color.red);
		for(int i = 0; i < path.size(); i++){
			g.fillRect(path.get(i).getX()*TILESIZE, path.get(i).getY()*TILESIZE, TILESIZE, TILESIZE);
		}
		g.setColor(Color.magenta);
		g.fillRect(startX*TILESIZE, startY*TILESIZE, TILESIZE, TILESIZE);
		g.fillRect(endX*TILESIZE, endY*TILESIZE, TILESIZE, TILESIZE);
		for(int i = 0; i < path.size()-1; i++){
			g.setColor(Color.magenta);
			int cx1 = path.get(i).getX()*TILESIZE + (TILESIZE/4);
			int cy1 = path.get(i).getY()*TILESIZE + (TILESIZE/4);
			g.fillOval(cx1, cy1, 10, 10);
			g.setColor(Color.blue);
			cx1 = path.get(i).getX()*TILESIZE + (TILESIZE/2);
			cy1 = path.get(i).getY()*TILESIZE + (TILESIZE/2);
			int cx2 = path.get(i).getParent().getX() * TILESIZE + (TILESIZE/2);
			int cy2 = path.get(i).getParent().getY() * TILESIZE + (TILESIZE/2);
			g.drawLine(cx1, cy1, cx2, cy2);
			g.drawLine(cx1+1, cy1-1, cx2+1, cy2-1);
			g.drawLine(cx1-1, cy1+1, cx2-1, cy2+1);
			g.drawLine(cx1+1, cy1+1, cx2+1, cy2+1);
			g.drawLine(cx1-1, cy1-1, cx2-1, cy2-1);
		}
		g.setColor(Color.yellow);
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.drawString(String.valueOf((double)path.get(0).getTraveled()), WIDTH-100, 25);
		if(algorithm)g.drawString("AStar", WIDTH-150, 25);
		else g.drawString("Dijkstra", WIDTH-165, 25);
	}

	public void mouseClicked(MouseEvent e) {
		int x = (int)(e.getX()/TILESIZE);
		int y = (int)(e.getY()/TILESIZE);
		switch(e.getButton()){
		case 1:
			if(x != startX && y != startY){
				startX = x;
				startY = y;
			}
			else{
				if(algorithm)algorithm = false;
				else algorithm = true;
			}
			break;
		case 3:
			if(x != endX && y != endY){
				endX = x;
				endY = y;
			}
			else{
				if(algorithm)algorithm = false;
				else algorithm = true;
			}
			break;
		}
		tiles = new ArrayList<ArrayList<Coordinate>>();
		
		for(y = 0; y < tilesBackup.size(); y++){
			tiles.add(new ArrayList<Coordinate>());
			for(x = 0; x < tilesBackup.get(y).size(); x++){
				switch(tilesBackup.get(y).get(x).getState()){
				case SOLID:
					tiles.get(y).add(new Coordinate((short)x, (short)y, (short)1));
					break;
				case VOID:
					tiles.get(y).add(new Coordinate((short)x, (short)y, (short)0));
					break;
				
				}
			}
		}
		astar = new AStar(tiles);
		path = astar.solve(algorithm, (short)startX, (short)startY, (short)endX, (short)endY);
		System.out.println(astar.getLoops());
		repaint();
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
}
