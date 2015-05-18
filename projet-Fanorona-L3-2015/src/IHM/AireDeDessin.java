package IHM;

import java.awt.*;

import javax.swing.*;

class AireDeDessin extends JComponent {
    Fenetre fenetre;
    int tailleJeton;
    int tailleSegment;

    
    public AireDeDessin(Fenetre f) {
        fenetre=f;
        tailleSegment=f.frame.getWidth()*4/60;
        System.out.println("taille: "+tailleSegment);
        tailleJeton=tailleSegment/(int)2.5;
        setPreferredSize(new Dimension(10*tailleSegment,6*tailleSegment));     
    }

    public void paintComponent(Graphics g) {
    	Graphics2D drawable = (Graphics2D) g;

        int width = getSize().width;
        int height = getSize().height;

        drawable.setPaint(Color.white);
        drawable.fillRect(0, 0, width, height);
        drawable.setPaint(Color.black);
       
        dessinGrille(drawable);
        dessinGrilleJeton(drawable,Color.white,Color.black);
    }
    


   
    
    public void dessinGrille(Graphics2D drawable){
    		//ligne verticale
    	for(int i=0;i<=8;i++){
    		drawable.drawLine(tailleSegment+i*tailleSegment, tailleSegment, tailleSegment+i*tailleSegment,tailleSegment+4*tailleSegment);
    	}
    	
    		//ligne Horizontale
    	for(int i=0;i<=4;i++){
    		drawable.drawLine(tailleSegment, tailleSegment+i*tailleSegment, tailleSegment+8*tailleSegment, tailleSegment+i*tailleSegment);
    	}
    	
    		//diagonale decroissante
    	drawable.drawLine(tailleSegment,tailleSegment+2*tailleSegment,tailleSegment+2*tailleSegment,tailleSegment+4*tailleSegment);
    	for(int i=0;i<3;i++){
    		drawable.drawLine(tailleSegment+(2*i*tailleSegment),tailleSegment,tailleSegment+(2*i*tailleSegment)+4*tailleSegment,tailleSegment+4*tailleSegment);
    	}
    	drawable.drawLine(tailleSegment+6*tailleSegment,tailleSegment,tailleSegment+8*tailleSegment,tailleSegment+2*tailleSegment);
    	
    		//diagonale croissante
    	drawable.drawLine(tailleSegment,tailleSegment+2*tailleSegment,tailleSegment+2*tailleSegment,tailleSegment);
    	for(int i=0;i<3;i++){
    		drawable.drawLine(tailleSegment+(2*i*tailleSegment),tailleSegment+4*tailleSegment,tailleSegment+(2*i*tailleSegment)+4*tailleSegment,tailleSegment);
    	}
    	drawable.drawLine(tailleSegment+6*tailleSegment,tailleSegment+4*tailleSegment,tailleSegment+8*tailleSegment,tailleSegment+2*tailleSegment);
    
    }

    public void dessinGrilleJeton(Graphics2D drawable,Color c1,Color c2){
    	for(int i=0;i<=8;i++){
    		for(int j=0;j<2;j++)
    			dessinJeton(drawable,c1,tailleSegment-(tailleJeton/2)+i*tailleSegment,tailleSegment-(tailleJeton/2)+j*tailleSegment);
    	}
    	boolean b=true;
    	for(int i=0;i<=8;i++){
    		if(i!=(8/2)){
	    		if(b){
	    			dessinJeton(drawable,c2,tailleSegment-(tailleJeton/2)+i*tailleSegment,tailleSegment-(tailleJeton/2)+2*tailleSegment);
	    			b=false;
	    		}
	    		else{
	    			dessinJeton(drawable,c1,tailleSegment-(tailleJeton/2)+i*tailleSegment,tailleSegment-(tailleJeton/2)+2*tailleSegment);
	    			b=true;
	    		}
    		}		
    	}
    	for(int i=0;i<=8;i++){
    		for(int j=3;j<5;j++)
    			dessinJeton(drawable,c2,tailleSegment-(tailleJeton/2)+i*tailleSegment,tailleSegment-(tailleJeton/2)+j*tailleSegment);
    	}
    	
    	
    }
    
    public void dessinJeton(Graphics2D drawable,Color c,int x,int y){
    	drawable.setPaint(c);
    	drawable.fillOval(x, y, tailleJeton, tailleJeton);
    	drawable.setPaint(Color.black);
    	drawable.drawOval(x, y, tailleJeton, tailleJeton);
    }
   
    
}
