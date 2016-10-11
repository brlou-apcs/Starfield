// Final variables for the number of each particle in each spawn
public final int NUM_NORM_PARTICLES = 50;
public final int NUM_JUMBO_PARTICLES = 2;
public final int NUM_ODD_PARTICLES = 1;

public ArrayList<Particle> particles = new ArrayList<Particle>();
public int moveCase = 1;

public void setup() {
	size(600,600);
	noStroke();
	int c = color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));
	addParticles(width/2, height/2, c); // Spawns initial particles in center of screen
}

public void draw() {
	background(0); // Resets background
	for (int i = 0; i < particles.size(); i++) {
		Particle p = particles.get(i);
		p.show();
		p.move();

		if (p.getX() > width || p.getX() < 0 || p.getY() < 0 || p.getY() > height || (p.getNumMoves() >= 100 && p instanceof OddballParticle)) {
			particles.remove(i);
		}
	}
}

// Spawns more particles when mouse is pressed
public void mousePressed() {
	int c = color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));
	addParticles(mouseX, mouseY, c);
}

// Switch case to change moveCase when key is pressed
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

// Particle interface
public interface Particle {
	public abstract void show();
	public abstract void move();
	public abstract double getX();
	public abstract double getY();
	public abstract double getNumMoves();
}

public class NormalParticle implements Particle {

	public double x, y, speed, angle;
	public int numMoves, c;

	public NormalParticle(double x, double y, int c) {
		this.x = x;
		this.y = y;
		this.speed = Math.random()*10+1;
		this.angle = Math.PI * 2 * Math.random();
		this.c = c;
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
				this.speed += 0.1;
				break;
		}

		this.x += Math.cos(this.angle) * this.speed;
		this.y += Math.sin(this.angle) * this.speed;

	}

	public void show() {
		fill(this.c);
		ellipse((float)this.x, (float)this.y, 5, 5);
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getNumMoves() {
		return this.numMoves;
	}
}

public class JumboParticle extends NormalParticle {

	public JumboParticle(double x, double y, int c) {
		super(x, y, c);
		this.c = color(255, 0, 0);
	}

	public void show() {
		fill(this.c);
		ellipse((float)this.x, (float)this.y, 10, 10);
	}

}

public class OddballParticle implements Particle {

	public double x, y;
	public int c, numMoves;

	public OddballParticle(double x, double y, int c) {
		this.x = x;
		this.y = y;
		this.c = color(255,255,255);
	}

	public void show() {
		fill(this.c);
		ellipse((float)this.x, (float)this.y, 7, 7);
	}

	public void move() {
		this.x += (int)(Math.random()*10-5);
		this.y += (int)(Math.random()*10-5);
		this.numMoves++;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getNumMoves() {
		return this.numMoves;
	}
}

// Spawns the particles
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
