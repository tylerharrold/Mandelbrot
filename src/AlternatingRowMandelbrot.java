// this mandelbrot algorithm is a simple line by line alternation
public class AlternatingRowMandelbrot extends Thread{
	
	private int threadID;
	private int totalThreads;
	private int width, height;
	private int thresh;
	private int[] pixels;
	
	public AlternatingRowMandelbrot(int threadID, int totalThreads , int width, int height, int thresh, int[] pixels){
		this.threadID = threadID;
		this.width = width;
		this.height = height;
		this.thresh = thresh;
		this.pixels = pixels;
		this.totalThreads = totalThreads;
		
	}
	
	private void calculatePoints(){
		
		// iterate over every pixel in the screen, row by row
		for(int yPix = threadID ; yPix < height ; yPix+=totalThreads){
			for(int xPix = 0 ; xPix < width ; xPix+=1){
				pixels[yPix * height + xPix] = mandelbrotIterations(xPix, yPix);
				
			}
		}
		
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
	
	public void run(){
		calculatePoints();
	}

}
