package IHM;

import java.awt.*;

import javax.swing.*;

class AireDeDessin extends JComponent {
    Fenetre fenetre;

    
    public AireDeDessin(Fenetre f) {
        fenetre=f;
        setPreferredSize(new Dimension(500,500));     
    }

    public void paintComponent(Graphics g) {
    	Graphics2D drawable = (Graphics2D) g;

        int width = getSize().width;
        int height = getSize().height;

        drawable.setPaint(Color.white);
        drawable.fillRect(0, 0, width, height);
        drawable.setPaint(Color.black);
       
        dessinGrille(drawable);

    }
    


   
    
    public void dessinGrille(Graphics2D drawable){
    	int taille=50;
    		//ligne verticale
    	for(int i=0;i<=8;i++){
    		drawable.drawLine(10+i*taille, 10, 10+i*taille,10+4*taille);
    	}
    		//ligne Horizontale
    	for(int i=0;i<=4;i++){
    		drawable.drawLine(10, 10+i*taille, 10+8*taille, 10+i*taille);
    	}
    		//diagonale decroissante
    	
    		//diagonale croissante
    	System.out.println("test");
    
    }
    
   
    
}
