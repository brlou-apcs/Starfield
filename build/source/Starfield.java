import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Starfield extends PApplet {

public final int NUM_NORM_PARTICLES = 50;
public final int NUM_JUMBO_PARTICLES = 2;
public final int NUM_ODD_PARTICLES = 1;

public ArrayList<Particle> particles = new ArrayList<Particle>();
public int moveCase = 1;

public void setup() {
	
	noStroke();
	int c = color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));
	addParticles(width/2, height/2, c);
}

public void draw() {
	background(0);
	for (int i = 0; i < particles.size(); i++) {
		Particle p = particles.get(i);
		p.show();
		p.move();

		if (p.rX() > width || p.rX() < 0 || p.rY() < 0 || p.rY() > height || (p.getNumMoves() >= 100 && p instanceof OddballParticle)) {
			particles.remove(i);
		}
	}
}

public void mousePressed() {
	int c = color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));
	addParticles(mouseX, mouseY, c);
}

public void keyPressed() {
	switch (key) {
		case '1':
			moveCase = 1;
			break;
		case '2':
			moveCase = 2;
			break;
		case '3':
			moveCase = 3;
			break;
	}
}

public interface Particle {
	public abstract void show();
	public abstract void move();
	public abstract double rX();
	public abstract double rY();
	public abstract double getNumMoves();
}

public class NormalParticle implements Particle {

	public double myX, myY, speed, angle;
	public int numMoves, myC;

	public NormalParticle(double x, double y, int c) {
		this.myX = x;
		this.myY = y;
		this.speed = Math.random()*10+1;
		this.angle = Math.PI * 2 * Math.random();
		this.myC = c;
	}

	public void move() {
		switch (moveCase) {
			case 1:
				this.angle += 0;
				this.speed += 0;
				break;
			case 2:
				this.angle += 5*(Math.PI/180);
				this.speed += 1;
				break;
			case 3:
				this.angle += 10*(Math.PI/180);
				this.speed += 0.1f;
				break;
		}

		this.myX += Math.cos(this.angle) * this.speed;
		this.myY += Math.sin(this.angle) * this.speed;
	}

	public void show() {
		fill(this.myC);
		ellipse((float)this.myX, (float)this.myY, 5, 5);
	}

	public double rX() {
		return this.myX;
	}

	public double rY() {
		return this.myY;
	}

	public double getNumMoves() {
		return this.numMoves;
	}
}

public class JumboParticle extends NormalParticle {

	public JumboParticle(double x, double y, int c) {
		super(x, y, c);
		this.myC = color(255, 0, 0);
	}

	public void show() {
		fill(this.myC);
		ellipse((float)this.myX, (float)this.myY, 10, 10);
	}

}

public class OddballParticle implements Particle {

	public double myX, myY;
	public int myC, numMoves;

	public OddballParticle(double x, double y, int c) {
		this.myX = x;
		this.myY = y;
		this.myC = color(255,255,255);
	}

	public void show() {
		fill(this.myC);
		ellipse((float)this.myX, (float)this.myY, 7, 7);
	}

	public void move() {
		this.myX += (int)(Math.random()*10-5);
		this.myY += (int)(Math.random()*10-5);
		this.numMoves++;
	}

	public double rX() {
		return this.myX;
	}

	public double rY() {
		return this.myY;
	}

	public double getNumMoves() {
		return this.numMoves;
	}
}

public void addParticles(double x, double y, int c) {

	for (int i = 0; i < NUM_NORM_PARTICLES; i++) {
		particles.add(new NormalParticle(x,y,c));
	}

	for (int i = 0; i < NUM_JUMBO_PARTICLES; i++) {
		particles.add(new JumboParticle(x,y,c));
	}

	for (int i = 0; i < NUM_ODD_PARTICLES; i++) {
		particles.add(new OddballParticle(x,y,c));
	}
}
  public void settings() { 	size(800,800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Starfield" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
