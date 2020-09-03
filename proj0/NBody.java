public class NBody{
	
	public static double readRadius(String fileName){
		In in = new In(fileName);
		int planets = in.readInt();
		double radius = in.readDouble();
		return radius;
	}
	
	public static Body[] readBodies(String fileName){
		In in2 = new In(fileName);

		int p = in2.readInt();
		double r = in2.readDouble();

		Body[] arr = new Body[5];
		
		for (int i = 0; i < arr.length; i++){
			double xp = in2.readDouble();
			double yp = in2.readDouble();
			double xv = in2.readDouble();
			double yv = in2.readDouble();
			double mass = in2.readDouble();
			String imgName = in2.readString();
			arr[i] = new Body(xp,yp,xv,yv,mass,imgName);
		}

		return arr;
	}
	

	public static void main (String[] args){
		// Starts by reading everything from file.
		double T = Double.parseDouble(args[0]);     // referenced https://beginnersbook.com/2013/12/how-to-convert-string-to-double-in-java/
		double dt = Double.parseDouble(args[1]);	// referenced https://beginnersbook.com/2013/12/how-to-convert-string-to-double-in-java/
		String filename = args[2];
		double r = readRadius(filename);
		Body[] bb = readBodies(filename);

		// Drawing the universe.
		StdDraw.enableDoubleBuffering();
		StdDraw.setScale(-r, r);
		StdDraw.clear();
		
		//animating moving planets around the sun.
		double time = 0.0;
		Double[] xForces = new Double[bb.length];
		Double[] yForces = new Double[bb.length];
		
		while (time < T){
			//storing x and y forces exerted in an array until time's up.
			for (int p = 0; p < bb.length; p++){
				xForces[p] = bb[p].calcNetForceExertedByX(bb);
				yForces[p] = bb[p].calcNetForceExertedByY(bb);
			}

			for (int l = 0; l < bb.length; l++){
				bb[l].update(dt,xForces[l],yForces[l]);
			}

			// Prevent flickering in animation by enabling Buffering.
			StdDraw.picture(0,0,"images/starfield.jpg");
			
			//Drawing all Bodies from readBodies method
			for (int k = 0; k < bb.length; k++){
				bb[k].draw();
			}	

			StdDraw.show();
			StdDraw.pause(10);
			time += dt;
		}

		StdOut.printf("%d\n", bb.length);
		StdOut.printf("%.2e\n", r);
		
		for (int q = 0; q < bb.length; q++) {
		    StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", bb[q].xxPos, bb[q].yyPos, bb[q].xxVel, bb[q].yyVel, bb[q].mass, bb[q].imgFileName);   
		}
	}
}











