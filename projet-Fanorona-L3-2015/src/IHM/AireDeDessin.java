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
    	drawable.drawLine(10,10+2*taille,10+2*taille,10+4*taille);
    	for(int i=0;i<3;i++){
    		drawable.drawLine(10+(2*i*taille),10,10+(2*i*taille)+4*taille,10+4*taille);
    	}
    	drawable.drawLine(10+6*taille,10,10+8*taille,10+2*taille);
    	
    		//diagonale croissante
    	/*drawable.drawLine(10,10+2*taille,10+2*taille,10+4*taille);
    	for(int i=0;i<3;i++){
    		drawable.drawLine(10+(2*i*taille),10,10+(2*i*taille)+4*taille,10+4*taille);
    	}
    	drawable.drawLine(10+6*taille,10,10+8*taille,10+2*taille);*/
    
    }
    
   
    
}
