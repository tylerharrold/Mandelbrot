import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AlternatingRowDriver extends JPanel implements MouseListener{
	private JFrame frame;
	public static final int THRESH = 10000;
	private BufferedImage buffImage;
	private int[] pixels;
	private final int WIDTH = 800;
	private final int HEIGHT = 800;
	private long duration;
	private final int REPETITIONS = 10;
	
	private AlternatingRowMandelbrot pm1, pm2, pm3, pm4, pm5, pm6, pm7, pm8;
	
	public AlternatingRowDriver(){
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.addMouseListener(this);
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

		// setup array of pixels
		buffImage = new BufferedImage(WIDTH , HEIGHT , BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)(buffImage.getRaster().getDataBuffer())).getData();
		
		// begin measurements and thread
//		long start = System.currentTimeMillis();
//		pm1 = new ParallelMandelbrot(0 , WIDTH, HEIGHT, THRESH, pixels);
//		pm2 = new ParallelMandelbrot(1 , WIDTH, HEIGHT, THRESH, pixels);
	//	pm3 = new ParallelMandelbrot(2 , WIDTH, HEIGHT, THRESH, pixels);
	//	pm4 = new ParallelMandelbrot(3 , WIDTH, HEIGHT, THRESH, pixels);
//		pm5 = new ParallelMandelbrot(4 , WIDTH, HEIGHT, THRESH, pixels);
//		pm6 = new ParallelMandelbrot(5 , WIDTH, HEIGHT, THRESH, pixels);
//		pm7 = new ParallelMandelbrot(6 , WIDTH, HEIGHT, THRESH, pixels);
//		pm8 = new ParallelMandelbrot(7 , WIDTH, HEIGHT, THRESH, pixels);
//		pm1.start();
//		pm2.start();
	//	pm3.start();
	//	pm4.start();
//		pm5.start();
//		pm6.start();
//		pm7.start();
//		pm8.start();
		
//		try {
//			pm1.join();
//			pm2.join();
	//		pm3.join();
	//		pm4.join();
//			pm5.join();
//			pm6.join();
//			pm7.join();
//			pm8.join();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		duration = System.currentTimeMillis() - start;
//		System.out.println("duration in millis: " + duration);
		
		runMandelbrotsSingleCore();
		runMandelbrotsDoubleCore();
		runMandelbrotsTripleCore();
		repaint();
	}

	private void runMandelbrotsSingleCore(){
		long totalDuration = 0;
		for(int i = 0 ; i < REPETITIONS ; i++) {
			long start = System.currentTimeMillis();
			pm1 = new AlternatingRowMandelbrot(0, 1, WIDTH, HEIGHT, THRESH, pixels);
			pm1.start();
			try {
				pm1.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			long end = System.currentTimeMillis();
			totalDuration += end - start;
			repaint(); // just to keep display up
		}
		long averageRuntime = totalDuration / REPETITIONS;
		System.out.println("Single Core Average Runtime: " + averageRuntime);
	}

	private void runMandelbrotsDoubleCore(){
		long totalDuration = 0;
		for(int i = 0 ; i < REPETITIONS ; i++) {
			long start = System.currentTimeMillis();
			pm1 = new AlternatingRowMandelbrot(0, 2, WIDTH, HEIGHT, THRESH, pixels);
			pm2 = new AlternatingRowMandelbrot(1, 2,WIDTH, HEIGHT, THRESH, pixels);
			pm1.start();
			pm2.start();
			try {
				pm1.join();
				pm2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			long end = System.currentTimeMillis();
			totalDuration += end - start;
			repaint(); // just to keep display up
		}
		long averageRuntime = totalDuration / REPETITIONS;
		System.out.println("Double Core Average Runtime: " + averageRuntime);
	}

	private void runMandelbrotsTripleCore(){
		long totalDuration = 0;
		for(int i = 0 ; i < REPETITIONS ; i++) {
			long start = System.currentTimeMillis();
			pm1 = new AlternatingRowMandelbrot(0, 3, WIDTH, HEIGHT, THRESH, pixels);
			pm2 = new AlternatingRowMandelbrot(1, 3,WIDTH, HEIGHT, THRESH, pixels);
			pm3 = new AlternatingRowMandelbrot(2,3, WIDTH , HEIGHT, THRESH, pixels);
			pm1.start();
			pm2.start();
			pm3.start();
			try {
				pm1.join();
				pm2.join();
				pm3.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			long end = System.currentTimeMillis();
			totalDuration += end - start;
			repaint(); // just to keep display up
		}
		long averageRuntime = totalDuration / REPETITIONS;
		System.out.println("Triple Core Average Runtime: " + averageRuntime);
	}
	
	public void paint(Graphics g){
		
		g.drawImage(buffImage, 0 , 0 , null);
		
	}
	
	public static void main(String[] args){
		new AlternatingRowDriver();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(e.getX() +","+e.getY());
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
