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
        tailleSegment=f.frame.getWidth()*4/60;
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

        //int width = getSize().width;
        //int height = getSize().height;

        drawable.setPaint(Color.black);
       
        majScore();
        majAQuiLeTour();
        majBouton();
        majNomJoueurs();
        
        dessinGrille(drawable);//grille
        if(!fenetre.engine.partieCourante.joueurCourant.aiPlayer){
	        if(!fenetre.engine.partieCourante.enCombo){
	        	//System.out.println("pas en combo---------------------");
		        if(!pionCliquer && !doitChoisir && !fenetre.engine.partieCourante.joueurCourant.aiPlayer){
		        	pionJouable(drawable);//halo vert
		        }
	        }
	        else{
	        	//System.out.println("est en combo---------------------");
	        	pionJouableCombo(drawable); 
	        }
        }
        if(!pionCliquer && doitChoisir && !fenetre.engine.partieCourante.joueurCourant.aiPlayer){
        	choixManger(drawable);//halo bleu
        }
        dessinGrilleJeton(drawable,Color.black,Color.white); // A MODIFIER POUR CHOIX
        if(pionCliquer && !fenetre.engine.partieCourante.joueurCourant.aiPlayer){
        	jetonCliquer(drawable);//rond cyan
        	
        }
        
        
      //  positionPossible(drawable);
    }
    
    public void majScore(){
    	fenetre.scoreInt1.setText(""+fenetre.engine.partieCourante.nombrePionBlanc);
        fenetre.scoreInt2.setText(""+fenetre.engine.partieCourante.nombrePionNoir);
    }

    public void majAQuiLeTour(){
    	if(fenetre.engine.partieCourante.joueurCourant.name.equals(fenetre.engine.partieCourante.joueurBlanc.name)){
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
    }
    
    public void majNomJoueurs(){  	
    	String level = fenetre.engine.partieCourante.joueurBlanc.getNiveau();
		if (level.equals("Humain")){
			fenetre.idj1.setText(fenetre.engine.partieCourante.joueurBlanc.name);
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
    	
    	level = fenetre.engine.partieCourante.joueurNoir.getNiveau();
		if (level.equals("Humain")){
			fenetre.idj2.setText(fenetre.engine.partieCourante.joueurNoir.name);
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
    
	public void jetonCliquer(Graphics2D drawable){
		drawable.setPaint(Color.cyan);
		drawable.fillOval(tailleSegment+pCourant.colonne*tailleSegment-tailleJeton/4, tailleSegment+pCourant.ligne*tailleSegment-tailleJeton/4, tailleJeton/2, tailleJeton/2);
		drawable.setPaint(Color.black);
	   
	}
   
	public void jetonHalo(Graphics2D drawable,Coordonnee p){
		drawable.setPaint(halo);
		drawable.fillOval((int)(tailleSegment+p.colonne*tailleSegment-(tailleJeton*1.2)/2), (int)(tailleSegment+p.ligne*tailleSegment-(tailleJeton*1.2)/2), (int)(tailleJeton*1.2), (int)(tailleJeton*1.2));
		drawable.setPaint(Color.black);
	}
   
   public void jetonHaloChoix(Graphics2D drawable,Coordonnee p){
	   drawable.setPaint(haloChoix);
	   drawable.fillOval((int)(tailleSegment+p.colonne*tailleSegment-(tailleJeton*1.2)/2), (int)(tailleSegment+p.ligne*tailleSegment-(tailleJeton*1.2)/2), (int)(tailleJeton*1.2), (int)(tailleJeton*1.2));
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
   
   public void pionJouableCombo(Graphics2D drawable){
	   if(pionCombo!=null){
		   jetonHalo(drawable,pionCombo.position);
	   }
   }
   
   public void cheminCombo(Graphics2D drawable){
	   drawable.setPaint(comboColor);
	  /* if(combo!=null){
		   for(int i=0;i<combo.size();i++){
			   //System.out.println("--combot: "+combo.get(i).position.ligne+" "+combo.get(i).position.colonne);
		   }
	   }*/
	   drawable.fillOval(tailleSegment+pCourant.colonne*tailleSegment-tailleJeton/4, tailleSegment+pCourant.ligne*tailleSegment-tailleJeton/4, tailleJeton/2, tailleJeton/2);
       drawable.setPaint(Color.black);
   }
   
   public void positionPossible(Graphics2D drawable){
	   ArrayList<Case> listCase = fenetre.engine.partieCourante.matricePlateau[2][4].voisins();
	   for(int i=0;i<listCase.size();i++){
		   //System.out.println("--Voisin: "+listCase.get(i).position.ligne+" "+listCase.get(i).position.colonne);
	   }
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
	private Image img;
	private Icon icon;
	private int width;
	private int height;
 
	public ImagePanel(String img, int x, int y) {
		this(new ImageIcon(img).getImage(), x, y);
	}
 
	public ImagePanel(Image img, int x, int y) {
		this.img = img;
		this.width = x;
		this.height = y;
	}
 
	public ImagePanel(Icon icon, int x, int y) {
		this.icon = icon;
		this.width = x;
		this.height = y;
		this.img = iconToImage(icon);
	}

	//fonction récupéré sur Internet
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

