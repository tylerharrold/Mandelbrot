import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MBWEvery4Recursive extends JPanel implements MouseListener{
	private JFrame frame;
	public static final int THRESH = 10000;
	private BufferedImage buffImage;
	private int[] pixels;
	private final int WIDTH = 800;
	private final int HEIGHT = 800;
	private long duration;

	private RecursiveEvery4 mb0;

	public MBWEvery4Recursive(){
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
		mb0 = new RecursiveEvery4(4 , 0, WIDTH , HEIGHT, THRESH, pixels);
		mb0.start();
		try {
			mb0.join();
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
		new MBWEvery4Recursive();
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
