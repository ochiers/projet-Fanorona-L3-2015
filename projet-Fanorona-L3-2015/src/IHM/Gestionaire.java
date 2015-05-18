package IHM;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

public class Gestionaire implements ComponentListener{
	JFrame frame;
	
	public Gestionaire(JFrame f){
		frame=f;
	}
	
    public void componentHidden(ComponentEvent e)
    {}

    public void componentMoved(ComponentEvent e)
    {}

    public void componentResized(ComponentEvent e)
    {
        frame.setSize(new Dimension(frame.getSize().height, frame.getSize().height));
    }

    public void componentShown(ComponentEvent e)
    {} 
}
