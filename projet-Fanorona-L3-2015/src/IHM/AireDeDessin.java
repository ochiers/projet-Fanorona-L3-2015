package IHM;

import java.awt.*;
import java.awt.PageAttributes.OriginType;
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
    Color coupPossible;
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
    double tailleHalo;
	int originePlateauX = 0;
	int originePlateauY = 0;
	
	boolean surbrillance=false;
	Coordonnee pSurbrillance;
	
    
    public AireDeDessin(Fenetre f) {
        fenetre=f;
//        segment  = (int)((fenetre.fw*3.99)/60);
//        decalageH = (int)(1.4*segment);
//        decalageL = (int)(1.55*segment);
    //    halo=Color.green;
        halo=Color.green;
        haloChoix=Color.yellow;
        comboColor=Color.orange;
        coupPossible=Color.cyan;
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
        tailleHalo=1.5;
        
        
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
    	fenetre.panelAccueil = new ImagePanel(new ImageIcon("src/images/imageDefault.jpg").getImage(), fenetre.frame.WIDTH, fenetre.frame.HEIGHT);
    //	System.out.println("//////taillejeton2//"+tailleJeton+" "+segment);
    	//System.out.println(" width " + width + " height " + height + " PW " + (int)(etir*plateauW));
    	drawable.drawImage(plateau.getImage(), originePlateauX, originePlateauY, (int)(etir*plateauW), (int)(etir*plateauH), null);
    	
//        drawable.setPaint(Color.black);
       
        majScore();
        majAQuiLeTour();
        majBouton();
        majNomJoueurs();

        if(!fenetre.engine.getJoueurCourant().aiPlayer && !finPartie){
        	
        	
        	if(!pionCliquer && doitChoisir){
        		choixManger(drawable);//halo bleu
	        }
        	if(!fenetre.engine.enCombo()){
	        	//System.out.println("pas en combo---------------------");
		        if(!pionCliquer && !doitChoisir)
		        	pionJouable(drawable);//halo vert
	        }
	        else{ 
	        	if(!pionCliquer) pionJouableCombo(drawable); 
	        	cheminCombo(drawable);
	        } 
	       // dessinGrilleJeton(drawable,Color.black,Color.white); // A MODIFIER POUR CHOIX
	        dessinGrilleJeton(drawable, originePlateauX, originePlateauY, (int)(etir*plateauW), (int)(etir*plateauH), etir);
	        if(pionCliquer){
	        	//halojetonCliquer(drawable);//rond cyan
	        	halo(drawable,pCourant,Color.cyan);
	        	emplacementPossible(drawable);
	        	if(surbrillance)
	        		pionSurbrillance(drawable,pSurbrillance,fenetre.engine.getJoueurCourant()==fenetre.engine.getJoueurBlanc()?fenetre.pion1:fenetre.pion2);
	        }
    	}else dessinGrilleJeton(drawable, originePlateauX, originePlateauY, (int)(etir*plateauW), (int)(etir*plateauH), etir);

        //testdegrader(drawable);
        centrerPlateau(width, height, (int)(etir*plateauW), (int)(etir*plateauH));
    }
    
    public void halo(Graphics2D drawable,Coordonnee p,Color c){
    //	System.out.println("//////transparen+ "+halo.getAlpha());
    	int red=c.getRed();
    	int green=c.getGreen();
    	int blue=c.getBlue();
    	int alpha=c.getAlpha();
    	int newTaille=(int)(tailleJeton*tailleHalo);
    	double diff=alpha/(newTaille-tailleJeton);
    	drawable.setPaint(new Color(red,green,blue,alpha));
    	for(int i=tailleJeton+2;i<newTaille;i++){
    		alpha=(int)(255-(i-tailleJeton)*diff);
    		drawable.setPaint(new Color(red,green,blue,alpha));
    		drawable.drawOval((int)(CoordonneesPlateau[0]*etir+p.colonne*segment-i/2+originePlateauX), (int)(CoordonneesPlateau[1]*etir+p.ligne*segment-i/2+originePlateauY), (int)i, (int)i);
    	}
		drawable.setPaint(Color.black);
    }
    
    public void pionSurbrillance(Graphics2D drawable,Coordonnee p,Color c){
    	int red=c.getRed();
    	int green=c.getGreen();
    	int blue=c.getBlue();
    	int alpha=128;
    	drawable.setPaint(new Color(red,green,blue,alpha));
    	drawable.fillOval((int)(CoordonneesPlateau[0]*etir+p.colonne*segment-tailleJeton/2+originePlateauX), (int)(CoordonneesPlateau[1]*etir+p.ligne*segment-tailleJeton/2+originePlateauY), (int)tailleJeton, (int)tailleJeton);
		drawable.setPaint(Color.black);
	//	System.out.println("test");
    }
    
    private void centrerPlateau(int width, int height, int pw, int ph) {
		if (width>=height){
			originePlateauX = (width/2)-(pw/2);
			originePlateauY = (height/2)-(ph/2);
		}
		else {
			originePlateauX = 0;
			originePlateauY = (height/2)-(ph/2);
		}
	}
  
    public void majScore(){
    	fenetre.scoreInt1.setText(""+fenetre.engine.getNombrePionsBlancs());
        fenetre.scoreInt2.setText(""+fenetre.engine.getNombrePionsNoirs());
    }

    public void majAQuiLeTour(){
    	if(fenetre.engine.getJoueurCourant().name.equals(fenetre.engine.getJoueurBlanc().name)){
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
    	String level = fenetre.engine.getJoueurBlanc().getNiveau();
		if (level.equals("Humain")){
			fenetre.idj1.setText(fenetre.engine.getJoueurBlanc().name);
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
    	
    	level = fenetre.engine.getJoueurNoir().getNiveau();
		if (level.equals("Humain")){
			fenetre.idj2.setText(fenetre.engine.getJoueurNoir().name);
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
    
/*	public void halojetonCliquer(Graphics2D drawable){	//MODIFIE 
		int red=Color.cyan.getRed();
    	int green=Color.cyan.getGreen();
    	int blue=Color.cyan.getBlue();
    	int alpha=Color.cyan.getAlpha();
    	int newTaille=(int)(tailleJeton*tailleHalo);
    	double diff=alpha/(newTaille-tailleJeton);
    	drawable.setPaint(new Color(red,green,blue,alpha));
		
    	for(int i=tailleJeton;i<newTaille;i++){
    		alpha=(int)(255-(i-tailleJeton)*diff);
    		drawable.setPaint(new Color(red,green,blue,alpha));
    		drawable.drawOval((int)(CoordonneesPlateau[0]*etir+pCourant.colonne*segment-i/2), (int)(CoordonneesPlateau[1]*etir+pCourant.ligne*segment-i/2), (int)i, (int)i);
    		
    	}
		drawable.setPaint(Color.black);
		
	}
*/ 
    
/*	public void haloChoixManger(Graphics2D drawable,Coordonnee p){	//MODIFIE 
	
		int red=haloChoix.getRed();
    	int green=haloChoix.getGreen();
    	int blue=haloChoix.getBlue();
    	int alpha=haloChoix.getAlpha();
    	int newTaille=(int)(tailleJeton*tailleHalo);
    	double diff=alpha/(newTaille-tailleJeton);
    	drawable.setPaint(new Color(red,green,blue,alpha));
		
    	for(int i=tailleJeton;i<newTaille;i++){
    		alpha=(int)(255-(i-tailleJeton)*diff);
    		drawable.setPaint(new Color(red,green,blue,alpha));
    		drawable.drawOval((int)(CoordonneesPlateau[0]*etir+p.colonne*segment-i/2), (int)(CoordonneesPlateau[1]*etir+p.ligne*segment-i/2), (int)i, (int)i);
    		
    	}
		drawable.setPaint(Color.black);
		
	}
 */
 
    public ArrayList<Case> coupReel(ArrayList<Case> c1,ArrayList<Case> c2){
    	ArrayList<Case> c3=new ArrayList<Case>();
    	boolean b=false;
    	int i=0;
    	int j=0;
    	while(i<c1.size()){
    		b=false;
    		j=0;
    		while(j<c2.size() && !b){
    			if(c1.get(i)==c2.get(j))
    				b=true;
    			j++;
    		}
    		if(!b)
    			c3.add(c1.get(i));
    		i++;
    	}
    	
    	return c3;
    }
    
    public void emplacementPossible(Graphics2D drawable){
    	ArrayList<Case> emplacementPossible1 = fenetre.engine.getCurrentGame().coupsPossiblesPourUnPion(fenetre.engine.getCurrentGame().matricePlateau[pfinal.ligne][pfinal.colonne]);
    	ArrayList<Case> emplacementPossible= fenetre.engine.getCurrentGame().coupsPourPriseParUnPion(emplacementPossible1, fenetre.engine.getCurrentGame().matricePlateau[pfinal.ligne][pfinal.colonne]);
    	if(combo!=null){
    		emplacementPossible=coupReel(emplacementPossible,combo);
    	//	System.out.println("pioncombo "+ combo.get(0).position.ligne+" "+combo.get(0).position.colonne + " taille: "+combo.size());
    	}
   // 	System.out.println("test1");
    	if(emplacementPossible!=null){
    //		System.out.println("test2 "+emplacementPossible.size());
    		if(emplacementPossible.size()!=0){
    			for(int i=0;i<emplacementPossible.size();i++){
    				halo(drawable,emplacementPossible.get(i).position,coupPossible);
    //				System.out.println("emplacement "+emplacementPossible.get(i).position);
    			}
    		}
    		else{
    			for(int i=0;i<emplacementPossible1.size();i++){
    				halo(drawable,emplacementPossible1.get(i).position,coupPossible);
    //				System.out.println("emplacement "+emplacementPossible1.get(i).position);
    			}
    		}
    	}
    	
    }
    
	public void pionJouable(Graphics2D drawable){
	   if(pionPossible!=null){
		   for(int i=0;i<pionPossible.size();i++){
		//	   haloSelectionnable(drawable,pionPossible.get(i).position);
			   halo(drawable,pionPossible.get(i).position,halo);
			   //System.out.println("--Point: "+pionPossible.get(i).position.ligne+" "+pionPossible.get(i).position.colonne);
		   }
	   }
   }
   
/*	public void haloSelectionnable(Graphics2D drawable,Coordonnee p){	//MODIFIE 
    	int red=halo.getRed();
    	int green=halo.getGreen();
    	int blue=halo.getBlue();
    	int alpha=halo.getAlpha();
    	int newTaille=(int)(tailleJeton*tailleHalo);
    	double diff=alpha/(newTaille-tailleJeton);
    	drawable.setPaint(new Color(red,green,blue,alpha));
		
    	for(int i=tailleJeton;i<newTaille;i++){
    		alpha=(int)(255-(i-tailleJeton)*diff);
    		drawable.setPaint(new Color(red,green,blue,alpha));
    		drawable.drawOval((int)(CoordonneesPlateau[0]*etir+p.colonne*segment-i/2), (int)(CoordonneesPlateau[1]*etir+p.ligne*segment-i/2), (int)i, (int)i);
    		
    	}
		drawable.setPaint(Color.black);
		
	}
*/
	
	public void pionJouableCombo(Graphics2D drawable){
	   if(pionCombo!=null){
		 //  haloSelectionnable(drawable,pionCombo.position);
		   halo(drawable,pionCombo.position,halo);
	  // }
//	   drawable.setPaint(Color.red);
		//drawable.fillOval(segment+pCourant.colonne*segment-tailleJeton/4, segment+pCourant.ligne*segment-tailleJeton/4, tailleJeton/2, tailleJeton/2);
//		drawable.fillOval((int)(CoordonneesPlateau[0]*etir+pionCombo.position.colonne*segment-tailleJeton/4), (int)(CoordonneesPlateau[1]*etir+pionCombo.position.ligne*segment-tailleJeton/4), tailleJeton/2, tailleJeton/2);
	//	drawable.setPaint(Color.black);
		//System.out.println("comboPION: "+pionCombo.position.ligne+" "+pionCombo.position.colonne);
   	}
   }
   
	public void cheminCombo(Graphics2D drawable){	//MODIFIE
	   drawable.setPaint(comboColor);
	   for(int i=0;i<combo.size();i++){
		   //System.out.println("--combot: "+combo.get(i).position.ligne+" "+combo.get(i).position.colonne);
		   //drawable.fillOval(segment+combo.get(i).position.colonne*segment-tailleJeton/4, segment+combo.get(i).position.ligne*segment-tailleJeton/4, tailleJeton/2, tailleJeton/2);
		   drawable.fillOval((int)(CoordonneesPlateau[0]*etir+combo.get(i).position.colonne*segment-tailleJeton/4+originePlateauX), (int)(CoordonneesPlateau[1]*etir+combo.get(i).position.ligne*segment-tailleJeton/4+originePlateauY), tailleJeton/2, tailleJeton/2);
	   }
	   drawable.setPaint(Color.black);
   }
   
	public void positionPossible(Graphics2D drawable){
	   ArrayList<Case> listCase = fenetre.engine.getPlateau()[2][4].voisins();
	   for(int i=0;i<listCase.size();i++){
		   //System.out.println("--Voisin: "+listCase.get(i).position.ligne+" "+listCase.get(i).position.colonne);
	   }
   }
   
/*	public void dessinGrille(Graphics2D drawable){
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
*/
	
    public void dessinGrilleJeton( Graphics2D drawable, int originePlateauX, int originePlateauY, int plateauW, int plateauH, float etir) {
    //	double segment = etir*    (CoordonneesPlateau[2] - CoordonneesPlateau[0])/8.0;
    	Case[][] matrice = fenetre.engine.getPlateau();
    	for(int i=0;i<matrice.length;i++){
    		for(int j=0;j<matrice[i].length;j++){
    			int startX = (int)(CoordonneesPlateau[0]*etir + j*segment) + originePlateauX;
    			int startY = (int)(CoordonneesPlateau[1]*etir + i*segment) + originePlateauY;
    			dessinJeton( drawable, matrice[i][j].pion, startX, startY);
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
	    	if(pion == Pion.Blanc) drawable.setPaint(fenetre.pion1);
	    	else drawable.setPaint(fenetre.pion2);
	  //  	int tailleJeton = (int)(segment/3.5);
	    	drawable.fillOval(x-tailleJeton/2, y-tailleJeton/2, tailleJeton, tailleJeton);
	    	drawable.setPaint(Color.black);
	    	drawable.drawOval(x-tailleJeton/2, y-tailleJeton/2, tailleJeton, tailleJeton);
    	}
    }
    
    public void choixManger(Graphics2D drawable){
    	for(int i=0;i<l1.size();i++){
    	//	haloChoixManger(drawable,l1.get(i).position);
    		halo(drawable,l1.get(i).position,haloChoix);
    	}
    	for(int i=0;i<l2.size();i++){
    	//	haloChoixManger(drawable,l2.get(i).position);
    		halo(drawable,l2.get(i).position,haloChoix);
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
    	if(fenetre.engine.getCurrentGame().enCombo){
    		return pionCombo.position.ligne==c.ligne && pionCombo.position.colonne==c.colonne;
    	}else{
	    	int i=0;
	    	while(i<pionPossible.size() && !choix){
	    		if(pionPossible.get(i).position.ligne==c.ligne && pionPossible.get(i).position.colonne==c.colonne)
	  		  		choix=true;
	    		i++;
	     	}
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
    	//TODO peut etre enlever originePlateauX et originePlateauY
    	p.ligne=CoordonneesPlateau[1]+(int)(c.ligne*segment)+originePlateauX;
    	p.colonne=CoordonneesPlateau[0]+(int)(c.colonne*segment)+originePlateauY;
    	//System.out.println("////////NEW COOR "+p.ligne+" "+p.colonne);
    	return p;
    }
    
}
