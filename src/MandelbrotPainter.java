import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MandelbrotPainter extends JPanel{
	private JFrame frame;
	public static final int THRESH = 1000;
	private BufferedImage buffImage;
	private int[] pixels;
	private int width, height;
	private long duration;
	
	public MandelbrotPainter(){
		frame = new JFrame();
		frame.setSize(new Dimension(800 , 800));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		frame.add(this);
		
		// setup array of pixels
		buffImage = new BufferedImage(frame.getWidth() , frame.getHeight() , BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)(buffImage.getRaster().getDataBuffer())).getData();
		
		// set variables
		width = frame.getWidth();
		height = frame.getHeight();
		
		// start painting mandelbrot
		paintMandelbrotBW();
	}
	
	public void paintMandelbrotBW(){
		long startTime = System.currentTimeMillis();
		// iterate over every pixel in the screen
		for(int yPix = 0 ; yPix < frame.getHeight() ; yPix++){
			for(int xPix = 0 ; xPix < frame.getWidth() ; xPix++){
				pixels[yPix * frame.getHeight() + xPix] = mandelbrotIterations(xPix, yPix);
				
			}
		}
		duration = System.currentTimeMillis() - startTime;
		repaint();
		System.out.println("duration: " + duration );
	}
	
	public int mandelbrotIterations(int xCoord , int yCoord){
		
		double realC = (xCoord - width / 2.0) * 4.0/ width;
		double imC = (yCoord - height / 2.0) * 4.0 / width;
		double x = 0;
		double y = 0;
		int iteration = 0;
		while(x*x + y*y <= 4 && iteration < THRESH){
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
		
		if(iteration < THRESH){
			return (rVal << 16) | 0x00518A;
		}
		else{
			return 0x000000;
		}
		
	}
	
	
	public void paint(Graphics g){
		
		g.drawImage(buffImage, 0 , 0 , null);
		
	}
	
	// main method
	public static void main(String[] args){
		MandelbrotPainter mp = new MandelbrotPainter();
		
	}
}
