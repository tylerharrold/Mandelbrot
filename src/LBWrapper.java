import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class LBWrapper extends JPanel implements MouseListener{
	private JFrame frame;
	public static final int THRESH = 10000;
	private BufferedImage buffImage;
	private int[] pixels;
	private final int WIDTH = 800;
	private final int HEIGHT = 800;
	private long duration;

	//private ParallelMandelbrot pm1, pm2, pm3, pm4, pm5, pm6, pm7, pm8;
	private LBMandelbrot lbmUp , lbmDown;

	public LBWrapper(){
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
		lbmUp = new LBMandelbrot(0 , WIDTH , HEIGHT , THRESH , pixels);
		lbmDown = new LBMandelbrot(1 , WIDTH , HEIGHT, THRESH, pixels);

		lbmUp.run();
		lbmDown.run();

		try {
			lbmUp.join();
			lbmDown.join();
			// should maybe do bookkeeping here
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		duration = System.currentTimeMillis() - start;
		System.out.println("duration in millis: " + duration);

		repaint();
	}


	public void paint(Graphics g){

		g.drawImage(buffImage, 0 , 0 , null);

	}

	public static void main(String[] args){
		new LBWrapper();
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
