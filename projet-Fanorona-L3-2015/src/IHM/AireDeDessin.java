package IHM;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

import engine.*;

public class AireDeDessin extends JComponent {
    Fenetre fenetre;
    int tailleJeton;
    int tailleSegment;
    boolean pionCliquer=false;
    Coordonnee pCourant;
    Color halo;
    Color haloChoix;
    Color comboColor;
    boolean doitChoisir=false;
    ArrayList<Case> l1;
    ArrayList<Case> l2;
    ArrayList<Case> pionPossible;
    ArrayList<Case> combo;
    Case pionCombo;
 
    
    public AireDeDessin(Fenetre f) {
        fenetre=f;
        tailleSegment=Fenetre.frame.getWidth()*4/60;
        tailleSegment = 85;
        halo=Color.green;
        haloChoix=Color.blue;
        comboColor=Color.orange;
        System.out.println("taille: "+tailleSegment);
        tailleJeton=tailleSegment/(int)2.5;
        setPreferredSize(new Dimension(10*tailleSegment,6*tailleSegment));
        pCourant=new Coordonnee(-1,-1);
    }

    public void paintComponent(Graphics g) {
    	Graphics2D drawable = (Graphics2D) g;    	
    	int width = this.getSize().width;
    	int height = this.getSize().height;
    	//drawable.drawImage(new ImageIcon("src/images/Fano9x5.jpg").getImage(), 0, 0, (int)width, (int)height, null);
    	drawable.drawImage(new ImageIcon("src/images/Fano9x5.jpg").getImage(), (int)(0.78*fenetre.fw/6), (int)(0.17*fenetre.fh/6), (int)(4.4*fenetre.fw/6), (int)(4.68*fenetre.fh/6), null);

        drawable.setPaint(Color.black);
       
        majScore();
        majAQuiLeTour();
        majBouton();
        majNomJoueurs();
        
        dessinGrille(drawable);//grille
        if(!fenetre.engine.getCurrentGame().joueurCourant.aiPlayer){
	        if(!fenetre.engine.getCurrentGame().enCombo){
	        	//System.out.println("pas en combo---------------------");
		        if(!pionCliquer && !doitChoisir && !fenetre.engine.getCurrentGame().joueurCourant.aiPlayer)
		        	pionJouable(drawable);//halo vert
	        }
	        else{
	        	System.out.println("est en combo---------------------");
	        	cheminCombo(drawable);
	        	//pionCliquer=true;
	        	pionJouableCombo(drawable); 

	        }

        
	        if(!pionCliquer && doitChoisir){
	        	choixManger(drawable);//halo bleu
	        }
	       // dessinGrilleJeton(drawable,Color.black,Color.white); // A MODIFIER POUR CHOIX
	        dessinGrilleJeton(drawable);
	        if(pionCliquer){
	        	jetonCliquer(drawable);//rond cyan
	        	
	        }
    	}else dessinGrilleJeton(drawable);
        

      //  positionPossible(drawable);
    }
    
    public void majScore(){
    	fenetre.scoreInt1.setText(""+fenetre.engine.getCurrentGame().nombrePionBlanc);
        fenetre.scoreInt2.setText(""+fenetre.engine.getCurrentGame().nombrePionNoir);
    }

    public void majAQuiLeTour(){
    	if(fenetre.engine.getCurrentGame().joueurCourant.name.equals(fenetre.engine.getCurrentGame().joueurBlanc.name)){
        	fenetre.tour1.setVisible(true);
        	fenetre.tour2.setVisible(false);
        }else{
        	fenetre.tour2.setVisible(true);
        	fenetre.tour1.setVisible(false);
        }
    }
    
    public void majBouton(){
    	if(fenetre.engine.peutAnnuler())fenetre.annuler.setEnabled(true);
        else fenetre.annuler.setEnabled(false);
        if(fenetre.engine.peutRefaire())fenetre.refaire.setEnabled(true);
        else fenetre.refaire.setEnabled(false);
        if(!fenetre.engine.getCurrentGame().joueurCourant.aiPlayer){
	        if(fenetre.engine.getCurrentGame().enCombo)
	        	fenetre.valider.setEnabled(true);
	        else fenetre.valider.setEnabled(false);
        }else fenetre.valider.setEnabled(false);
        if(fenetre.engine.getCurrentGame().isPaused())
        	fenetre.stopper.setText(" Reprendre ");
        else fenetre.stopper.setText(" Pause ");
    }
    
    public void majNomJoueurs(){  	
    	String level = fenetre.engine.getCurrentGame().joueurBlanc.getNiveau();
		if (level.equals("Humain")){
			fenetre.idj1.setText(fenetre.engine.getCurrentGame().joueurBlanc.name);
			fenetre.levelj1.setText(" Bonne Chance ! ");
			fenetre.levelj1.setVisible(false);
		}else if (level.equals("IA Facile")){
			fenetre.idj1.setText(" Ordinateur ");
			fenetre.levelj1.setText(" Facile ");
			fenetre.levelj1.setVisible(true);
		}else if (level.equals("IA Moyenne")){
			fenetre.idj1.setText(" Ordinateur ");
			fenetre.levelj1.setText(" Moyen ");
			fenetre.levelj1.setVisible(true);
		}else if (level.equals("IA Difficile")){
			fenetre.idj1.setText(" Ordinateur ");
			fenetre.levelj1.setText(" Difficile ");
			fenetre.levelj1.setVisible(true);
		}
    	
    	level = fenetre.engine.getCurrentGame().joueurNoir.getNiveau();
		if (level.equals("Humain")){
			fenetre.idj2.setText(fenetre.engine.getCurrentGame().joueurNoir.name);
			fenetre.levelj2.setText(" Bonne Chance ! ");
			fenetre.levelj2.setVisible(false);
		}else if (level.equals("IA Facile")){
			fenetre.idj2.setText(" Ordinateur ");
			fenetre.levelj2.setText(" Facile ");
			fenetre.levelj2.setVisible(true);
		}else if (level.equals("IA Moyenne")){
			fenetre.idj2.setText(" Ordinateur ");
			fenetre.levelj2.setText(" Moyen ");
			fenetre.levelj2.setVisible(true);
		}else if (level.equals("IA Difficile")){
			fenetre.idj2.setText(" Ordinateur ");
			fenetre.levelj2.setText(" Difficile ");
			fenetre.levelj2.setVisible(true);
		}
    }
    
	public void jetonCliquer(Graphics2D drawable){	//MODIFIE 
		drawable.setPaint(Color.cyan);
		//drawable.fillOval(tailleSegment+pCourant.colonne*tailleSegment-tailleJeton/4, tailleSegment+pCourant.ligne*tailleSegment-tailleJeton/4, tailleJeton/2, tailleJeton/2);
		drawable.fillOval(300+pCourant.colonne*tailleSegment-tailleJeton/4, 135+pCourant.ligne*tailleSegment-tailleJeton/4, tailleJeton/2, tailleJeton/2);
		drawable.setPaint(Color.black);
	}
   
   public void jetonHaloChoix(Graphics2D drawable,Coordonnee p){	//MODIFIE 
	   drawable.setPaint(haloChoix);
	   //drawable.fillOval((int)(tailleSegment+p.colonne*tailleSegment-(tailleJeton*1.2)/2), (int)(tailleSegment+p.ligne*tailleSegment-(tailleJeton*1.2)/2), (int)(tailleJeton*1.2), (int)(tailleJeton*1.2));
	   drawable.fillOval((int)(300+p.colonne*tailleSegment-(tailleJeton*1.2)/2), (int)(135+p.ligne*tailleSegment-(tailleJeton*1.2)/2), (int)(tailleJeton*1.2), (int)(tailleJeton*1.2));
	   drawable.setPaint(Color.black);
   }
   
   public void pionJouable(Graphics2D drawable){
	   if(pionPossible!=null){
		   for(int i=0;i<pionPossible.size();i++){
			   jetonHalo(drawable,pionPossible.get(i).position);
			   //System.out.println("--Point: "+pionPossible.get(i).position.ligne+" "+pionPossible.get(i).position.colonne);
		   }
	   }
   }
   
   public void jetonHalo(Graphics2D drawable,Coordonnee p){	//MODIFIE 
		drawable.setPaint(halo);
		//drawable.fillOval((int)(tailleSegment+p.colonne*tailleSegment-(tailleJeton*1.2)/2), (int)(tailleSegment+p.ligne*tailleSegment-(tailleJeton*1.2)/2), (int)(tailleJeton*1.2), (int)(tailleJeton*1.2));
		drawable.fillOval((int)(300+p.colonne*tailleSegment-(tailleJeton*1.2)/2), (int)(135+p.ligne*tailleSegment-(tailleJeton*1.2)/2), (int)(tailleJeton*1.2), (int)(tailleJeton*1.2));
		drawable.setPaint(Color.black);
		System.out.println("haloPION: "+p.ligne+" "+p.colonne);
	}
   
   public void pionJouableCombo(Graphics2D drawable){
	   if(pionCombo!=null){
		   jetonHalo(drawable,pionCombo.position);
	  // }
	   drawable.setPaint(Color.cyan);
		//drawable.fillOval(tailleSegment+pCourant.colonne*tailleSegment-tailleJeton/4, tailleSegment+pCourant.ligne*tailleSegment-tailleJeton/4, tailleJeton/2, tailleJeton/2);
		drawable.fillOval(300+pionCombo.position.colonne*tailleSegment-tailleJeton/4, 135+pionCombo.position.ligne*tailleSegment-tailleJeton/4, tailleJeton/2, tailleJeton/2);
		drawable.setPaint(Color.black);
		System.out.println("comboPION: "+pionCombo.position.ligne+" "+pionCombo.position.colonne);
   	}
   }
   
   public void cheminCombo(Graphics2D drawable){	//MODIFIE
	   drawable.setPaint(comboColor);
	   for(int i=0;i<combo.size();i++){
		   System.out.println("--combot: "+combo.get(i).position.ligne+" "+combo.get(i).position.colonne);
		   //drawable.fillOval(tailleSegment+combo.get(i).position.colonne*tailleSegment-tailleJeton/4, tailleSegment+combo.get(i).position.ligne*tailleSegment-tailleJeton/4, tailleJeton/2, tailleJeton/2);
		   drawable.fillOval(300+combo.get(i).position.colonne*tailleSegment-tailleJeton/4, 135+combo.get(i).position.ligne*tailleSegment-tailleJeton/4, tailleJeton/2, tailleJeton/2);

	   }
	   drawable.setPaint(Color.black);
   }
   
   public void positionPossible(Graphics2D drawable){
	   ArrayList<Case> listCase = fenetre.engine.getCurrentGame().matricePlateau[2][4].voisins();
	   for(int i=0;i<listCase.size();i++){
		   //System.out.println("--Voisin: "+listCase.get(i).position.ligne+" "+listCase.get(i).position.colonne);
	   }
   }
    
    /*public void dessinGrille(Graphics2D drawable){
    		//ligne verticale
    	for(int i=0;i<=8;i++)
    		drawable.drawLine(tailleSegment+i*tailleSegment, tailleSegment, tailleSegment+i*tailleSegment,tailleSegment+4*tailleSegment);
    		//ligne Horizontale
    	for(int i=0;i<=4;i++)
    		drawable.drawLine(tailleSegment, tailleSegment+i*tailleSegment, tailleSegment+8*tailleSegment, tailleSegment+i*tailleSegment);  	
    		//diagonale decroissante
    	drawable.drawLine(tailleSegment,tailleSegment+2*tailleSegment,tailleSegment+2*tailleSegment,tailleSegment+4*tailleSegment);
    	for(int i=0;i<3;i++)
    		drawable.drawLine(tailleSegment+(2*i*tailleSegment),tailleSegment,tailleSegment+(2*i*tailleSegment)+4*tailleSegment,tailleSegment+4*tailleSegment);
    	drawable.drawLine(tailleSegment+6*tailleSegment,tailleSegment,tailleSegment+8*tailleSegment,tailleSegment+2*tailleSegment);
    		//diagonale croissante
    	drawable.drawLine(tailleSegment,tailleSegment+2*tailleSegment,tailleSegment+2*tailleSegment,tailleSegment);
    	for(int i=0;i<3;i++)
    		drawable.drawLine(tailleSegment+(2*i*tailleSegment),tailleSegment+4*tailleSegment,tailleSegment+(2*i*tailleSegment)+4*tailleSegment,tailleSegment);
    	drawable.drawLine(tailleSegment+6*tailleSegment,tailleSegment+4*tailleSegment,tailleSegment+8*tailleSegment,tailleSegment+2*tailleSegment);
    }*/
   
   public void dessinGrille(Graphics2D drawable){
		//ligne verticale
	for(int i=0;i<=8;i++)
		drawable.drawLine(300+i*tailleSegment, 135, 300+i*tailleSegment,135+4*tailleSegment);
		//ligne Horizontale
	for(int i=0;i<=4;i++)
		drawable.drawLine(300, 135+i*tailleSegment, 300+8*tailleSegment, 135+i*tailleSegment);
		//diagonale decroissante
	drawable.drawLine(300,135+2*tailleSegment,300+2*tailleSegment,135+4*tailleSegment);
	for(int i=0;i<3;i++)
		drawable.drawLine(300+(2*i*tailleSegment),135,300+(2*i*tailleSegment)+4*tailleSegment,135+4*tailleSegment);
	drawable.drawLine(300+6*tailleSegment,135,300+8*tailleSegment,135+2*tailleSegment);
		//diagonale croissante
	drawable.drawLine(300,135+2*tailleSegment,300+2*tailleSegment,135);
	for(int i=0;i<3;i++)
		drawable.drawLine(300+(2*i*tailleSegment),135+4*tailleSegment,300+(2*i*tailleSegment)+4*tailleSegment,135);
	drawable.drawLine(300+6*tailleSegment,135+4*tailleSegment,300+8*tailleSegment,135+2*tailleSegment);
}
   

    /*public void dessinGrilleJeton(Graphics2D drawable,Color c1,Color c2){
    	
    	for(int i=0;i<fenetre.engine.partieCourante.matricePlateau.length;i++){
    		for(int j=0;j<fenetre.engine.partieCourante.matricePlateau[0].length;j++){
    			if(fenetre.engine.partieCourante.matricePlateau[i][j].pion==Pion.Blanc)
    				dessinJeton(drawable,c1,tailleSegment-(tailleJeton/2)+j*tailleSegment,tailleSegment-(tailleJeton/2)+i*tailleSegment);
    			else if(fenetre.engine.partieCourante.matricePlateau[i][j].pion==Pion.Noir)
    				dessinJeton(drawable,c2,tailleSegment-(tailleJeton/2)+j*tailleSegment,tailleSegment-(tailleJeton/2)+i*tailleSegment);
    			else{}
    		}
    	}
   }*/

   /* public void dessinGrilleJeton(Graphics2D drawable){
    	
    	for(int i=0;i<fenetre.engine.partieCourante.matricePlateau.length;i++){
    		for(int j=0;j<fenetre.engine.partieCourante.matricePlateau[0].length;j++){
    			if(fenetre.engine.partieCourante.matricePlateau[i][j].pion==Pion.Blanc)
    				dessinJeton(drawable,fenetre.pion1,tailleSegment-(tailleJeton/2)+j*tailleSegment,tailleSegment-(tailleJeton/2)+i*tailleSegment);
    			else if(fenetre.engine.partieCourante.matricePlateau[i][j].pion==Pion.Noir)
    				dessinJeton(drawable,fenetre.pion2,tailleSegment-(tailleJeton/2)+j*tailleSegment,tailleSegment-(tailleJeton/2)+i*tailleSegment);
    			else{}
    		}
    	}    	
 	}*/
    
    public void dessinGrilleJeton(Graphics2D drawable){
    	
    	for(int i=0;i<fenetre.engine.getCurrentGame().matricePlateau.length;i++){
    		for(int j=0;j<fenetre.engine.getCurrentGame().matricePlateau[0].length;j++){
    			if(fenetre.engine.getCurrentGame().matricePlateau[i][j].pion==Pion.Blanc)
    				dessinJeton(drawable,fenetre.pion1,300-(tailleJeton/2)+j*tailleSegment,135-(tailleJeton/2)+i*tailleSegment);
    			else if(fenetre.engine.getCurrentGame().matricePlateau[i][j].pion==Pion.Noir)
    				dessinJeton(drawable,fenetre.pion2,300-(tailleJeton/2)+j*tailleSegment,135-(tailleJeton/2)+i*tailleSegment);
    			else{}
    		}
    	}    	
 	}
    
    
    public void dessinJeton(Graphics2D drawable,Color c,int x,int y){
    	drawable.setPaint(c);
    	drawable.fillOval(x, y, tailleJeton, tailleJeton);
    	drawable.setPaint(Color.black);
    	drawable.drawOval(x, y, tailleJeton, tailleJeton);
    }
    
    public void choixManger(Graphics2D drawable){
    	for(int i=0;i<l1.size();i++){
 		   jetonHaloChoix(drawable,l1.get(i).position);
    	}
    	for(int i=0;i<l2.size();i++){
  		   jetonHaloChoix(drawable,l2.get(i).position);
     	}
    }
    
    public boolean estUnChoix(Coordonnee c){
    	boolean choix = false;
    	int i=0;
    	while(i<l1.size() && !choix){
    		if(l1.get(i).position.ligne==c.ligne && l1.get(i).position.colonne==c.colonne)
  		  		choix=true;
    		i++;
     	}
    	i=0;
    	while(i<l2.size() && !choix){
    		if(l2.get(i).position.ligne==c.ligne && l2.get(i).position.colonne==c.colonne)
  		  		choix=true;
    		i++;
     	}
    	return choix;
    }
   
    public boolean estJouable(Coordonnee c){
    	boolean choix = false;
    	int i=0;
    	while(i<pionPossible.size() && !choix){
    		if(pionPossible.get(i).position.ligne==c.ligne && pionPossible.get(i).position.colonne==c.colonne)
  		  		choix=true;
    		i++;
     	}
    	return choix;
    } 
}

 class ImagePanel extends JPanel {
	 
	private static final long serialVersionUID = 1L;
	static Image img;
	private int width;
	private int height;
 
	public ImagePanel(String img, int x, int y) {
		//this(new ImageIcon(img).getImage(), x, y);
		ImagePanel.img = new ImageIcon(img).getImage();
		this.width = x;
		this.height = y;
	}
 
	public ImagePanel(Image img, int x, int y) {
		ImagePanel.img = img;
		this.width = x;
		this.height = y;
	}
 
	public ImagePanel(Icon icon, int x, int y) {
		this.width = x;
		this.height = y;
		ImagePanel.img = iconToImage(icon);
	}
	
	public Image getImage(){
		return ImagePanel.img;
	}

	//fonction recuperee sur Internet
	public static Image iconToImage(Icon icon) {
        if (icon instanceof ImageIcon) {
            return ((ImageIcon)icon).getImage();
        } else {
            int w = icon.getIconWidth();
            int h = icon.getIconHeight();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gd.getDefaultConfiguration();
            BufferedImage image = gc.createCompatibleImage(w, h);
            Graphics2D g = image.createGraphics();
            icon.paintIcon(null, g, 0, 0);
            g.dispose();
            return image;
        }
    }
	
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, width, height, this);
	}
}

