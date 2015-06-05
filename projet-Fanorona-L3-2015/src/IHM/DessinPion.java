package IHM;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class DessinPion extends JComponent {
	
	Fenetre fenetre;
	Color pionCouleur;
    int tailleJeton;
	
	 public DessinPion(Fenetre f, Color c, int taille) {
		 fenetre=f;
		 pionCouleur = c;
		 tailleJeton = taille;
	 }
 
	 public void paintComponent(Graphics g) {
	    	Graphics2D drawable = (Graphics2D) g;
	        try
			{
				dessinJeton(drawable, pionCouleur);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
	 }

    public void dessinJeton(Graphics2D drawable, Color c) throws IOException{
//    	drawable.setPaint(c);
//    	drawable.fillOval(tailleJeton/2, tailleJeton/2, tailleJeton, tailleJeton);
//    	drawable.setPaint(Color.black);
//    	drawable.drawOval(tailleJeton/2, tailleJeton/2, tailleJeton, tailleJeton);
    	
    	BufferedImage imgBlanc = ImageIO.read(new File("./Ressources/pionBlanc.png"));
    	BufferedImage imgNoir = ImageIO.read(new File("./Ressources/pionNoir.png"));
    	if (pionCouleur == Color.white)
    	{
//    		JLabel imgBlanc = new JLabel(new ImageIcon("../Ressources/pionBlanc.png"));
    		drawable.drawImage(imgBlanc, tailleJeton/2, tailleJeton/2, imgBlanc.getWidth()/2, imgBlanc.getHeight()/2, null);
    	}else {
    		drawable.drawImage(imgNoir, tailleJeton/2, tailleJeton/2, imgNoir.getWidth()/2, imgNoir.getHeight()/2, null);
    	}
    	
    }
}
