package IHM;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import engine.Pion;

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
	        dessinJeton(drawable, pionCouleur);
	 }

    public void dessinJeton(Graphics2D drawable, Color c){
    	drawable.setPaint(c);
    	drawable.fillOval(tailleJeton/2, tailleJeton/2, tailleJeton, tailleJeton);
    	drawable.setPaint(Color.black);
    	drawable.drawOval(tailleJeton/2, tailleJeton/2, tailleJeton, tailleJeton);
    }
}
