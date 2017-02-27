import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MBWrapperEvery4 extends JPanel implements MouseListener{
	private JFrame frame;
	public static final int THRESH = 10000;
	private BufferedImage buffImage;
	private int[] pixels;
	private final int WIDTH = 800;
	private final int HEIGHT = 800;
	private long duration;
	
	private ParallelMandelbrotEvery4 pm1, pm2, pm3, pm4, pm5, pm6, pm7, pm8;
	
	public MBWrapperEvery4(){
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
		long start = System.currentTimeMillis();
		pm1 = new ParallelMandelbrotEvery4(0 , WIDTH, HEIGHT, THRESH, pixels);
		pm2 = new ParallelMandelbrotEvery4(1 , WIDTH, HEIGHT, THRESH, pixels);
		pm3 = new ParallelMandelbrotEvery4(2 , WIDTH, HEIGHT, THRESH, pixels);
		pm4 = new ParallelMandelbrotEvery4(3 , WIDTH, HEIGHT, THRESH, pixels);
//		pm5 = new ParallelMandelbrotEvery4(4 , WIDTH, HEIGHT, THRESH, pixels);
//		pm6 = new ParallelMandelbrotEvery4(5 , WIDTH, HEIGHT, THRESH, pixels);
//		pm7 = new ParallelMandelbrotEvery4(6 , WIDTH, HEIGHT, THRESH, pixels);
//		pm8 = new ParallelMandelbrotEvery4(7 , WIDTH, HEIGHT, THRESH, pixels);
		pm1.start();
		pm2.start();
		pm3.start();
		pm4.start();
//		pm5.start();
//		pm6.start();
//		pm7.start();
//		pm8.start();
		
		try {
			pm1.join();
			pm2.join();
			pm3.join();
			pm4.join();
//			pm5.join();
//			pm6.join();
//			pm7.join();
//			pm8.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		duration = System.currentTimeMillis() - start;
		System.out.println("duration in millis: " + duration);
		
		//runMandelbrot();
		repaint();
	}
	
	private void runMandelbrot(){
		while(true){
			repaint();
		}
	}
	
	public void paint(Graphics g){
		
		g.drawImage(buffImage, 0 , 0 , null);
		
	}
	
	public static void main(String[] args){
		new MBWrapperEvery4();
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
