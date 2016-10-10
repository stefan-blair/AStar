package structures;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import main.GUI;


public class Coordinate{

	 private short x, y;
	 private short heurstic, traveled, FScore;
	 Coordinate parent;
	 States state;
	 
	 public Coordinate(short x, short y, short state){
		 this.x = x;
		 this.y = y;
		 switch(state){
		 case(0):
			 this.state = States.VOID;
			 break;
		 case(1):
			 this.state = States.SOLID;
			 break;
		 }
	 }
	 
	 public void open(short heurstic, short traveled, Coordinate parent){
		 this.heurstic = heurstic;
		 this.traveled = traveled;
		 this.FScore = (short) (heurstic+traveled);
		 this.parent = parent;
		 this.state = States.OPEN;
	 }
	 
	 public void close(){
		 this.state = States.CLOSED;
	 }
	 
	 public void reParent(Coordinate parent, short traveled){
		 this.parent = parent;
		 this.traveled = traveled;
		 this.FScore = (short)(heurstic+traveled);
	 }
	 
	 public void setX(short x){this.x = x;}
	 public void setY(short y){this.y = y;}
	 public void setState(States state){this.state = state;}
	 
	 public short getX(){return this.x;}
	 public short getY(){return this.y;}
	 public short getTraveled(){return this.traveled;}
	 public short getFScore(){return this.FScore;}
	 public States getState(){return this.state;}
	 public Coordinate getParent(){return this.parent;}
	
}
