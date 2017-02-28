import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SerialDriver extends JPanel implements MouseListener{
	private JFrame frame;
	public static final int THRESH = 10000;
	private BufferedImage buffImage;
	private int[] pixels;
	private final int WIDTH = 800;
	private final int HEIGHT = 800;
	private long duration;
	private final int REPITITIONS = 5;

	private SerialMandelbrot sm;

	public SerialDriver(){
		// setup window
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.addMouseListener(this);
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

		// setup array of pixels on screen
		buffImage = new BufferedImage(WIDTH , HEIGHT , BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)(buffImage.getRaster().getDataBuffer())).getData();

		runMandelbrots();

	}

	private void runMandelbrots(){
		long duration = 0;
		for(int i = 0 ; i < REPITITIONS ; i++){
			long start = System.currentTimeMillis();
			sm = new SerialMandelbrot(WIDTH , HEIGHT , THRESH, pixels);
			sm.start();
			try{
				sm.join();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			long end = System.currentTimeMillis();
			duration += end - start;
			repaint(); // keep redrawing to screen to have something to show
		}

		long averageRuntime = duration / REPITITIONS;
		System.out.println(averageRuntime);

	}


	public void paint(Graphics g){

		g.drawImage(buffImage, 0 , 0 , null);

	}

	public static void main(String[] args){
		new SerialDriver();
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
