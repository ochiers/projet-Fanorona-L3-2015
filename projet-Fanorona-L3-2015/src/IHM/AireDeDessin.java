package IHM;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

import engine.*;

@SuppressWarnings("serial")
public class AireDeDessin extends JComponent {
    Fenetre fenetre;
    ImageIcon plateau;
    int CoordonneesPlateau[];
    int tailleJeton;
//    int segment;
    boolean pionCliquer=false;
    Coordonnee pCourant;
    Coordonnee pfinal;
    Color halo;
    Color haloChoix;
    Color comboColor;
    boolean doitChoisir=false;
    boolean finPartie=false;
    ArrayList<Case> l1;
    ArrayList<Case> l2;
    ArrayList<Case> pionPossible;
    ArrayList<Case> combo;
    Case pionCombo;
//    int decalageH;
//    int decalageL;
    double segment;
//    boolean animation=false;
//    int nombreImage=1;
    float etir;
    
    public AireDeDessin(Fenetre f) {
        fenetre=f;
//        segment  = (int)((fenetre.fw*3.99)/60);	//TODO
//        decalageH = (int)(1.4*segment);
//        decalageL = (int)(1.55*segment);
        halo=Color.green;
        haloChoix=Color.blue;
        comboColor=Color.orange;
        
        setPreferredSize(new Dimension((int)(10*segment),(int)(6*segment)));
        pCourant=new Coordonnee(-1,-1);
        plateau = new ImageIcon("src/images/Fano9x5.jpg");
        CoordonneesPlateau = new int[4];
        CoordonneesPlateau[0] = 80;
        CoordonneesPlateau[1] = 72;
        CoordonneesPlateau[2] = 484;
        CoordonneesPlateau[3] = 272;
        segment =0;
        tailleJeton=1;
        etir = 1;
    }

    public void paintComponent(Graphics g) {
    	Graphics2D drawable = (Graphics2D) g; 
		fenetre.fw = fenetre.frame.getWidth();
		fenetre.fh = fenetre.frame.getHeight();
   //     segment  = (int)((fenetre.fw*3.99)/60);
        
//        decalageH = (int)(1.4*segment);
//        decalageL = (int)(1.55*segment);
    	int width = this.getSize().width;
    	int height = this.getSize().height;
    	int plateauW = plateau.getImage().getWidth(null);
    	int plateauH = plateau.getImage().getHeight(null);
    	float etirW = width  / (float)plateauW;
    	float etirH = height / (float)plateauH;
    	etir = etirW < etirH ? etirW : etirH;
    	segment =etir*    (CoordonneesPlateau[2] - CoordonneesPlateau[0])/8.0;
    	tailleJeton = (int)(segment/1.75);
    	System.out.println("//////taillejeton2//"+tailleJeton+" "+segment);
    	int originePlateauX = 0;
    	int originePlateauY = 0;
    	//System.out.println(" width " + width + " height " + height + " PW " + (int)(etir*plateauW));
    	drawable.drawImage(plateau.getImage(), originePlateauX, originePlateauY, (int)(etir*plateauW), (int)(etir*plateauH), null);
    	
//        drawable.setPaint(Color.black);
       
        majScore();
        majAQuiLeTour();
        majBouton();
        majNomJoueurs();

        if(!fenetre.engine.getCurrentGame().joueurCourant.aiPlayer && !finPartie){
        	if(!pionCliquer && doitChoisir){
	        	choixManger(drawable);//halo bleu
	        }
        	if(!fenetre.engine.getCurrentGame().enCombo){
	        	//System.out.println("pas en combo---------------------");
		        if(!pionCliquer && !doitChoisir)
		        	pionJouable(drawable);//halo vert
	        }
	        else{
	        	//System.out.println("est en combo---------------------");
	        	cheminCombo(drawable);
	        	//pionCliquer=true;
	        	pionJouableCombo(drawable); 
	        	//pCourant.ligne=pionCombo.position.ligne;
	        	//pCourant.colonne=pionCombo.position.colonne;
	        } 
	       // dessinGrilleJeton(drawable,Color.black,Color.white); // A MODIFIER POUR CHOIX
	        dessinGrilleJeton(drawable, originePlateauX, originePlateauY, (int)(etir*plateauW), (int)(etir*plateauH), etir);
	        if(pionCliquer){
	        	jetonCliquer(drawable);//rond cyan
	        }
    	}else dessinGrilleJeton(drawable, originePlateauX, originePlateauY, (int)(etir*plateauW), (int)(etir*plateauH), etir);


 //       Coordonnee p=positionGrille(new Coordonnee(2,2));
 //       drawArrow(drawable,p.colonne,p.ligne,50,70);
     /*   if(animation){
        	dessinGrilleJeton(drawable);
        	animation(drawable);
        }else{
        */	/*
	        if(!fenetre.engine.getCurrentGame().joueurCourant.aiPlayer && !finPartie){
	        	if(!pionCliquer && doitChoisir){
		        	choixManger(drawable);//halo bleu
		        }
	        	if(!fenetre.engine.getCurrentGame().enCombo){
		        	//System.out.println("pas en combo---------------------");
			        if(!pionCliquer && !doitChoisir)
			        	pionJouable(drawable);//halo vert
		        }
		        else{
		        	//System.out.println("est en combo---------------------");
		        	cheminCombo(drawable);
		        	//pionCliquer=true;
		        	pionJouableCombo(drawable); 
		        	//pCourant.ligne=pionCombo.position.ligne;
		        	//pCourant.colonne=pionCombo.position.colonne;
	
		        } 
		       // dessinGrilleJeton(drawable,Color.black,Color.white); // A MODIFIER POUR CHOIX
		        dessinGrilleJeton(drawable);
		        if(pionCliquer){
		        	jetonCliquer(drawable);//rond cyan
		        	
		        }
	    	}else dessinGrilleJeton(drawable);
   //     }*/
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
	        	fenetre.finTour.setEnabled(true);
	        else fenetre.finTour.setEnabled(false);
        }else fenetre.finTour.setEnabled(false);
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
		System.out.println("//////taillejeton/// "+tailleJeton);
		//drawable.fillOval(segment+pCourant.colonne*segment-tailleJeton/4, segment+pCourant.ligne*segment-tailleJeton/4, tailleJeton/2, tailleJeton/2);
		drawable.fillOval((int)(CoordonneesPlateau[0]*etir+pCourant.colonne*segment-tailleJeton/4), (int)(CoordonneesPlateau[1]*etir+pCourant.ligne*segment-tailleJeton/4), tailleJeton/2, tailleJeton/2);
		drawable.setPaint(Color.black);
	}
   
   public void jetonHaloChoix(Graphics2D drawable,Coordonnee p){	//MODIFIE 
	   drawable.setPaint(haloChoix);
	   //drawable.fillOval((int)(segment+p.colonne*segment-(tailleJeton*1.2)/2), (int)(segment+p.ligne*segment-(tailleJeton*1.2)/2), (int)(tailleJeton*1.2), (int)(tailleJeton*1.2));
	   drawable.fillOval((int)(CoordonneesPlateau[0]*etir+p.colonne*segment-(tailleJeton*1.2)/2), (int)(CoordonneesPlateau[1]*etir+p.ligne*segment-(tailleJeton*1.2)/2), (int)(tailleJeton*1.2), (int)(tailleJeton*1.2));
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
		double newTaille=tailleJeton*1.25;
		//drawable.fillOval((int)(segment+p.colonne*segment-(tailleJeton*1.2)/2), (int)(segment+p.ligne*segment-(tailleJeton*1.2)/2), (int)(tailleJeton*1.2), (int)(tailleJeton*1.2));
		drawable.fillOval((int)(CoordonneesPlateau[0]*etir+p.colonne*segment-newTaille/2), (int)(CoordonneesPlateau[1]*etir+p.ligne*segment-newTaille/2), (int)newTaille, (int)newTaille);
		drawable.setPaint(Color.black);
		//System.out.println("haloPION: "+p.ligne+" "+p.colonne);
	}
   
   public void pionJouableCombo(Graphics2D drawable){
	   if(pionCombo!=null){
		   jetonHalo(drawable,pionCombo.position);
	  // }
	   drawable.setPaint(Color.cyan);
		//drawable.fillOval(segment+pCourant.colonne*segment-tailleJeton/4, segment+pCourant.ligne*segment-tailleJeton/4, tailleJeton/2, tailleJeton/2);
		drawable.fillOval(CoordonneesPlateau[0]+(int)(pionCombo.position.colonne*segment)-tailleJeton/4, CoordonneesPlateau[1]+(int)(pionCombo.position.ligne*segment)-tailleJeton/4, tailleJeton/2, tailleJeton/2);
		drawable.setPaint(Color.black);
		//System.out.println("comboPION: "+pionCombo.position.ligne+" "+pionCombo.position.colonne);
   	}
   }
   
   public void cheminCombo(Graphics2D drawable){	//MODIFIE
	   drawable.setPaint(comboColor);
	   for(int i=0;i<combo.size();i++){
		   //System.out.println("--combot: "+combo.get(i).position.ligne+" "+combo.get(i).position.colonne);
		   //drawable.fillOval(segment+combo.get(i).position.colonne*segment-tailleJeton/4, segment+combo.get(i).position.ligne*segment-tailleJeton/4, tailleJeton/2, tailleJeton/2);
		   drawable.fillOval((int)(CoordonneesPlateau[0]*etir+combo.get(i).position.colonne*segment-tailleJeton/4), (int)(CoordonneesPlateau[1]*etir+combo.get(i).position.ligne*segment-tailleJeton/4), tailleJeton/2, tailleJeton/2);
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
    		drawable.drawLine(segment+i*segment, segment, segment+i*segment,segment+4*segment);
    		//ligne Horizontale
    	for(int i=0;i<=4;i++)
    		drawable.drawLine(segment, segment+i*segment, segment+8*segment, segment+i*segment);  	
    		//diagonale decroissante
    	drawable.drawLine(segment,segment+2*segment,segment+2*segment,segment+4*segment);
    	for(int i=0;i<3;i++)
    		drawable.drawLine(segment+(2*i*segment),segment,segment+(2*i*segment)+4*segment,segment+4*segment);
    	drawable.drawLine(segment+6*segment,segment,segment+8*segment,segment+2*segment);
    		//diagonale croissante
    	drawable.drawLine(segment,segment+2*segment,segment+2*segment,segment);
    	for(int i=0;i<3;i++)
    		drawable.drawLine(segment+(2*i*segment),segment+4*segment,segment+(2*i*segment)+4*segment,segment);
    	drawable.drawLine(segment+6*segment,segment+4*segment,segment+8*segment,segment+2*segment);
    }*/
   
   public void dessinGrille(Graphics2D drawable){
		//ligne verticale
	for(int i=0;i<=8;i++)
		drawable.drawLine(CoordonneesPlateau[0]+(int)(i*segment), CoordonneesPlateau[1], CoordonneesPlateau[0]+(int)(i*segment),CoordonneesPlateau[1]+(int)(4*segment));
		//ligne Horizontale
	for(int i=0;i<=4;i++)
		drawable.drawLine(CoordonneesPlateau[0], CoordonneesPlateau[1]+(int)(i*segment), CoordonneesPlateau[0]+(int)(8*segment), CoordonneesPlateau[1]+(int)(i*segment));
		//diagonale decroissante
	drawable.drawLine(CoordonneesPlateau[0],CoordonneesPlateau[1]+(int)(2*segment),CoordonneesPlateau[0]+(int)(2*segment),CoordonneesPlateau[1]+(int)(4*segment));
	for(int i=0;i<3;i++)
		drawable.drawLine(CoordonneesPlateau[0]+(int)(2*i*segment),CoordonneesPlateau[1],CoordonneesPlateau[0]+(int)(2*i*segment)+(int)(4*segment),CoordonneesPlateau[1]+(int)(4*segment));
	drawable.drawLine(CoordonneesPlateau[0]+(int)(6*segment),CoordonneesPlateau[1],CoordonneesPlateau[0]+(int)(8*segment),CoordonneesPlateau[1]+(int)(2*segment));
		//diagonale croissante
	drawable.drawLine(CoordonneesPlateau[0],CoordonneesPlateau[1]+(int)(2*segment),CoordonneesPlateau[0]+(int)(2*segment),CoordonneesPlateau[1]);
	for(int i=0;i<3;i++)
		drawable.drawLine(CoordonneesPlateau[0]+(int)(2*i*segment),CoordonneesPlateau[1]+(int)(4*segment),CoordonneesPlateau[0]+(int)(2*i*segment)+(int)(4*segment),CoordonneesPlateau[1]);
	drawable.drawLine(CoordonneesPlateau[0]+(int)(6*segment),CoordonneesPlateau[1]+(int)(4*segment),CoordonneesPlateau[0]+(int)(8*segment),CoordonneesPlateau[1]+(int)(2*segment));
}
   

    /*public void dessinGrilleJeton(Graphics2D drawable,Color c1,Color c2){
    	
    	for(int i=0;i<fenetre.engine.getCurrentGame().matricePlateau.length;i++){
    		for(int j=0;j<fenetre.engine.getCurrentGame().matricePlateau[0].length;j++){
    			if(fenetre.engine.getCurrentGame().matricePlateau[i][j].pion==Pion.Blanc)
    				dessinJeton(drawable,c1,segment-(tailleJeton/2)+j*segment,segment-(tailleJeton/2)+i*segment);
    			else if(fenetre.engine.getCurrentGame().matricePlateau[i][j].pion==Pion.Noir)
    				dessinJeton(drawable,c2,segment-(tailleJeton/2)+j*segment,segment-(tailleJeton/2)+i*segment);
    			else{}
    		}
    	}
   }*/

   /* public void dessinGrilleJeton(Graphics2D drawable){
    	
    	for(int i=0;i<fenetre.engine.getCurrentGame().matricePlateau.length;i++){
    		for(int j=0;j<fenetre.engine.getCurrentGame().matricePlateau[0].length;j++){
    			if(fenetre.engine.getCurrentGame().matricePlateau[i][j].pion==Pion.Blanc)
    				dessinJeton(drawable,fenetre.pion1,segment-(tailleJeton/2)+j*segment,segment-(tailleJeton/2)+i*segment);
    			else if(fenetre.engine.getCurrentGame().matricePlateau[i][j].pion==Pion.Noir)
    				dessinJeton(drawable,fenetre.pion2,segment-(tailleJeton/2)+j*segment,segment-(tailleJeton/2)+i*segment);
    			else{}
    		}
    	}    	
 	}*/
    
    public void dessinGrilleJeton( Graphics2D drawable, int originePlateauX, int originePlateauY, int plateauW, int plateauH, float etir) {
    //	double segment = etir*    (CoordonneesPlateau[2] - CoordonneesPlateau[0])/8.0;
    	Case[][] matrice = fenetre.engine.getCurrentGame().matricePlateau;
    	for(int i=0;i<matrice.length;i++){
    		for(int j=0;j<matrice[i].length;j++){
    			dessinJeton( drawable, matrice[i][j].pion, (int)(CoordonneesPlateau[0]*etir + j*segment), (int)(CoordonneesPlateau[1]*etir + i*segment));
    			/*
    			if(fenetre.engine.getCurrentGame().matricePlateau[i][j].pion==Pion.Blanc)
    				dessinJeton(drawable,fenetre.pion1,decalageL-(tailleJeton/2)+j*segment,decalageH-(tailleJeton/2)+i*segment);
    			else if(fenetre.engine.getCurrentGame().matricePlateau[i][j].pion==Pion.Noir)
    				dessinJeton(drawable,fenetre.pion2,decalageL-(tailleJeton/2)+j*segment,decalageH-(tailleJeton/2)+i*segment);
    			else{}
    			*/
    		}
    	}    	
 	}
    
    
    public void dessinJeton(Graphics2D drawable,Pion pion, int x,int y){
    	if(pion != null){
	    	if(pion == Pion.Blanc) drawable.setPaint(fenetre.pion1);else drawable.setPaint(fenetre.pion2);
	  //  	int tailleJeton = (int)(segment/3.5);
	    	drawable.fillOval(x-tailleJeton/2, y-tailleJeton/2, tailleJeton, tailleJeton);
	    	drawable.setPaint(Color.black);
	    	drawable.drawOval(x-tailleJeton/2, y-tailleJeton/2, tailleJeton, tailleJeton);
    	}
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
   
    public void drawArrow(Graphics2D g,int x,int y,int largeur,int hauteur){
	    g.setPaint(Color.red);
		largeur = largeur / 3;
		hauteur = hauteur / 3;
		
		g.fillRect (x -largeur/2, y,largeur, 2 * hauteur);
		
		int abcisses[] = new int[] { x-largeur/2,
					     x-largeur/2 + (3 * largeur),
					     x-largeur/2 + (largeur * 3 / 2)};
		int ordonnes[] = new int[] { y + (2 * hauteur),
					     y + (2 * hauteur),
					     y + (3 * hauteur)};
		
		g.fillPolygon (abcisses, ordonnes, 3);
		g.setPaint(Color.black);

	}
	

    
    public Coordonnee positionGrille(Coordonnee c){
    	Coordonnee p = new Coordonnee(-1,-1);
    	p.ligne=CoordonneesPlateau[1]+(int)(c.ligne*segment);
    	p.colonne=CoordonneesPlateau[0]+(int)(c.colonne*segment);
    	//System.out.println("////////NEW COOR "+p.ligne+" "+p.colonne);
    	return p;
    }
    
/*    public void animation(Graphics2D drawable) {
    	if(nombreImage==3){
    		try {
    			Thread.sleep(1000);
    		} catch (InterruptedException e) {
    			System.out.println("//////////BUG1//////////");
    			e.printStackTrace();
    		}
    		animation=false;
    		nombreImage=1;
    		repaint();
    	}
    	else{
    	System.out.println("///////ANIMATION///////");
    	Coordonnee p1=positionGrille(pCourant);
    	Coordonnee p2=positionGrille(pfinal);
    	
    	int x=p2.colonne-p1.colonne;
    	int y=p2.ligne-p1.ligne;
    	System.out.println("///////COO///////"+(p1.ligne+(y/3*nombreImage))+" "+(p1.colonne+(x/3*nombreImage)));
    	drawable.setPaint(Color.black);
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("//////////BUG2//////////");
			e.printStackTrace();
		}
    	drawable.fillOval(p1.colonne+(x/3*nombreImage), p1.ligne+(y/3*nombreImage), tailleJeton, tailleJeton);
    	nombreImage++;
    	repaint();
    	}
    	
    }*/
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

