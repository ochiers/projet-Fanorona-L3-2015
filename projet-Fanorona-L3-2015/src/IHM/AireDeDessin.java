package IHM;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import engine.Case;
import engine.Pion;

class AireDeDessin extends JComponent {
    Fenetre fenetre;
    int tailleJeton;
    int tailleSegment;
    boolean pionCliquer=false;
    Point pCourant;
    Color halo;

    
    public AireDeDessin(Fenetre f) {
        fenetre=f;
        tailleSegment=f.frame.getWidth()*4/60;
        halo=Color.green;
        System.out.println("taille: "+tailleSegment);
        tailleJeton=tailleSegment/(int)2.5;
        setPreferredSize(new Dimension(10*tailleSegment,6*tailleSegment));
        pCourant=new Point();
    }

    public void paintComponent(Graphics g) {
    	Graphics2D drawable = (Graphics2D) g;

        int width = getSize().width;
        int height = getSize().height;

        //drawable.setPaint(Color.white);
        //drawable.fillRect(0, 0, width, height);
        drawable.setPaint(Color.black);
       
        dessinGrille(drawable);
        if(!pionCliquer){
        	pionJouable(drawable);
        }
        dessinGrilleJeton(drawable,Color.black,Color.white);
        if(pionCliquer){
        	jetonCliquer(drawable);
        	positionPossible(drawable);
        }
    }
    


   public void jetonCliquer(Graphics2D drawable){
	   drawable.setPaint(Color.cyan);
       drawable.fillOval(tailleSegment+pCourant.y*tailleSegment-tailleJeton/4, tailleSegment+pCourant.x*tailleSegment-tailleJeton/4, tailleJeton/2, tailleJeton/2);
       drawable.setPaint(Color.black);
	   
   }
   
   public void jetonHalo(Graphics2D drawable,Point p){
	   drawable.setPaint(halo);
       drawable.fillOval((int)(tailleSegment+p.y*tailleSegment-(tailleJeton*1.2)/2), (int)(tailleSegment+p.x*tailleSegment-(tailleJeton*1.2)/2), (int)(tailleJeton*1.2), (int)(tailleJeton*1.2));
       drawable.setPaint(Color.black);
   }
   
   public void pionJouable(Graphics2D drawable){
	   ArrayList<Case> listCase = fenetre.engine.partieCourante.lesPionsJouables();
	   for(int i=0;i<listCase.size();i++){
		   jetonHalo(drawable,listCase.get(i).position);
		   System.out.println("--Point: "+listCase.get(i).position.y+" "+listCase.get(i).position.x);
	   }
   }
   
   public void positionPossible(Graphics2D drawable){
	   
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
    	
    	for(int i=0;i<fenetre.engine.partieCourante.matricePlateau.length;i++){
    		for(int j=0;j<fenetre.engine.partieCourante.matricePlateau[0].length;j++){
    			//System.out.print(fenetre.engine.partieCourante.matricePlateau[i][j].pion+" ");
    			if(fenetre.engine.partieCourante.matricePlateau[i][j].pion==Pion.Blanc){
    				dessinJeton(drawable,c1,tailleSegment-(tailleJeton/2)+j*tailleSegment,tailleSegment-(tailleJeton/2)+i*tailleSegment);
    			}
    			else if(fenetre.engine.partieCourante.matricePlateau[i][j].pion==Pion.Noir){
    				dessinJeton(drawable,c2,tailleSegment-(tailleJeton/2)+j*tailleSegment,tailleSegment-(tailleJeton/2)+i*tailleSegment);
    			}
    			else{
    				
    			}
    		}
    	}
    	
    	
    }
    
    public void dessinJeton(Graphics2D drawable,Color c,int x,int y){
    	drawable.setPaint(c);
    	drawable.fillOval(x, y, tailleJeton, tailleJeton);
    	drawable.setPaint(Color.black);
    	drawable.drawOval(x, y, tailleJeton, tailleJeton);
    }
   
}


class ImagePanel extends JPanel {
	 
	private static final long serialVersionUID = 1L;
	private Image img;
 
	public ImagePanel(String img) {
		this(new ImageIcon(img).getImage());
	}
 
	public ImagePanel(Image img) {
		this.img = img;
	}
 
	public void paintComponent(Graphics g) {
		int fw = Fenetre.frame.getWidth();
		int fh = Fenetre.frame.getHeight();
		g.drawImage(img, 0, 0, fw, fh, this);
	}
}

