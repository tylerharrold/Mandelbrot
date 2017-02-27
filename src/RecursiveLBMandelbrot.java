// this mandelbrot algorithm is a simple line by line alternation
public class RecursiveLBMandelbrot extends Thread{

	private boolean workLeft;
	private int threadID;
	private int width, height;
	private int thresh;
	private int[] pixels;
	private static volatile int up , down;
	private int depth;
	public RecursiveLBMandelbrot(int threadID, int depth, int width, int height, int thresh, int[] pixels){
		this.threadID = threadID;
		this.width = width;
		this.height = height;
		this.thresh = thresh;
		this.pixels = pixels;
		this.up = 0; // the first index of pixels
		this.down = pixels.length - 1; // the last index of pixels
		this.depth = depth;

	}


	private int mandelbrotIterations(int xCoord, int yCoord){
		double realC = (xCoord - width / 2.0) * 4.0/ width;
		double imC = (yCoord - height / 2.0) * 4.0 / width;
		double x = 0;
		double y = 0;
		int iteration = 0;
		while(x*x + y*y <= 4 && iteration < thresh){
			double newX = x*x - y*y + realC;
			y = 2*x*y + imC;
			x = newX;
			iteration++;
		}

		// hard code test
		int rVal = (255 - 10* iteration);
		if(rVal < 0){
			rVal = 0;
		}

		if(iteration < thresh){
			return (rVal << 16) | 0x00518A;
		}
		else{
			return 0x000000;
		}
	}

	private void workLeft() {
		int yCoord = 0;
		int xCoord = 0;
		while (up < down) {
			yCoord = up / width;
			xCoord = up % width;

			pixels[yCoord * height + xCoord] = mandelbrotIterations(xCoord, yCoord);

			up++;
		}
	}

	private void workRight(){
		int yCoord = 0;
		int xCoord = 0;
		while (down > up) {
			yCoord = down / width;
			xCoord = down % width;

			pixels[yCoord * height + xCoord] = mandelbrotIterations(xCoord, yCoord);

			down--;
		}
	}



	public void run(){
		// check the depth of the thread

		// spawn more threads if possible
		// then calculate, running either down or up, based on id
        if (threadID % 2 == 0){
        	workLeft();
		}
		else {
        	workRight();
		}
	}

}
