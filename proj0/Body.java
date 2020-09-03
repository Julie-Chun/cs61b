public class Body{
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	//Two double constructors for bodies of planets.
	public Body(double xP, double yP, double xV, double yV, double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Body(Body b){
		xxPos = b.xxPos;
		yyPos = b.yyPos;
		xxVel = b.xxVel;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;
	}

	//static method to calculate the distance between two bodies of planets.
	public double calcDistance(Body b){
		double xSq = (xxPos - b.xxPos)*(xxPos - b.xxPos);
		double ySq = (yyPos - b.yyPos)*(yyPos - b.yyPos);
		double distSq = xSq + ySq;
		double dist = Math.sqrt(distSq); //operation Math.sqrt cited from Prof. Hug's 1.2 lecture.
		return dist;
	}

	//calculates the double value force exerted by input Body 'b' on a given Body.
	static final double gravity = 0.0000000000667;
	
	public double calcForceExertedBy(Body b){
		double dist = calcDistance(b);
		double force = (gravity*mass*b.mass)/(dist*dist);
		return force;
	}

	//Calculates the net X force exerted by all bodies from an input of an array of bodies on the current Body.
	public double calcNetForceExertedByX(Body[] arrB){
		double netX = 0.0;

		for (Body b: arrB){
			if (!(equals(b))){
				double force = calcForceExertedBy(b);
				double dist = calcDistance(b);
				double dx = b.xxPos - xxPos;
				double xForce = (force*dx)/dist;
				netX += xForce;
			}

		}
		return netX;
	}

	//Calculates the net Y force exerted by all bodies from an input of an array of bodies on the current Body.
	public double calcNetForceExertedByY(Body[] arrB){
		double netY = 0.0;

		for (Body b: arrB){
			if (!(equals(b))){
				double force = calcForceExertedBy(b);
				double dist = calcDistance(b);
				double dy = b.yyPos - yyPos;
				double yForce = (force*dy)/dist;
				netY += yForce;
			}
		}
		return netY;
	}

	//Calculate force needed for body to accelerate, change in velocity, and change in position during the time of dt.
	public void update(double dt, double xf, double yf){
		double accelX = xf/mass;
		double accelY = yf/mass;
		xxVel = xxVel + (dt*accelX);
		yyVel = yyVel + (dt*accelY);
		xxPos = xxPos + (dt*xxVel);
		yyPos = yyPos + (dt*yyVel);
	}

	public void draw(){
		StdDraw.picture(xxPos,yyPos,"images/" + imgFileName);
	}


}








