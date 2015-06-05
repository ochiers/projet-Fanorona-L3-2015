package IHM;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import AI.*;
import engine.*;


public class EcouteurDeSouris implements MouseListener{
	AireDeDessin aire;
	
	public EcouteurDeSouris(AireDeDessin a){
		aire=a;
	}

	public void mouseClicked(MouseEvent e) {
		if(!aire.fenetre.engine.getCurrentGame().joueurCourant.aiPlayer && !aire.fenetre.engine.getCurrentGame().isPaused()){
			int buttonDown = e.getButton();
			if (buttonDown == MouseEvent.BUTTON1) {// Bouton GAUCHE enfonce
				aire.pfinal=new Coordonnee(-1,-1);
				aire.pfinal=position(e.getX(),e.getY());
				if(aire.pfinal.colonne!=-1 && aire.pfinal.ligne!=-1){
					if(aire.pionCliquer){
					//	aire.animation=true;
					//	aire.repaint();
						if(aire.fenetre.engine.getJoueurCourant() instanceof HumanPlayer){
							((HumanPlayer)aire.fenetre.engine.getJoueurCourant()).setCoup(aire.pCourant,aire.pfinal);
							aire.pionCliquer=false;}
						/*	if(aire.pionCombo!=null){
								aire.pionCombo.position.ligne=pfinal.ligne;
								aire.pionCombo.position.colonne=pfinal.colonne;
							}*/
					}
					else{
						if(aire.doitChoisir){
							if(aire.estUnChoix(aire.pfinal)){
								((HumanPlayer)aire.fenetre.engine.getCurrentGame().joueurCourant).setDirectionMultiPrise(aire.pfinal);
								aire.doitChoisir=false;
							}
						}
						else{
							if(aire.estJouable(aire.pfinal) || (aire.fenetre.engine.getCurrentGame().enCombo && aire.pionCombo.position.ligne==aire.pfinal.ligne && aire.pionCombo.position.colonne==aire.pfinal.colonne ) ){
								System.out.println("---------OUI c'est jouable");
								aire.pCourant.colonne=aire.pfinal.colonne;
								aire.pCourant.ligne=aire.pfinal.ligne;
								aire.pionCliquer=true;
								System.out.println("Point: "+aire.pCourant.ligne+" "+aire.pCourant.colonne);
							}
						}
					}
					aire.repaint();
				}
			} else if(buttonDown == MouseEvent.BUTTON2) {// Bouton du MILIEU enfonce
			} else if(buttonDown == MouseEvent.BUTTON3) {// Bouton DROIT enfonce
				//if(!aire.fenetre.engine.getCurrentGame().enCombo)
					aire.pionCliquer=false;
				aire.repaint();
		    }
			
		}
	}
	
	public void mouseEntered(MouseEvent e) {
	
	}
		
	public void mouseExited(MouseEvent e) {
	
	}
	
	public void mousePressed(MouseEvent e) {
		
		if(!aire.fenetre.engine.getCurrentGame().joueurCourant.aiPlayer && !aire.fenetre.engine.getCurrentGame().isPaused()){
			int buttonDown = e.getButton();
			if (buttonDown == MouseEvent.BUTTON1) {// Bouton GAUCHE enfonce
				aire.pfinal=new Coordonnee(-1,-1);
				aire.pfinal=position(e.getX(),e.getY());
				if(aire.pfinal.colonne!=-1 && aire.pfinal.ligne!=-1){
					if(aire.pionCliquer){
					}
					else{
						if(aire.doitChoisir){
							if(aire.estUnChoix(aire.pfinal)){
								((HumanPlayer)aire.fenetre.engine.getCurrentGame().joueurCourant).setDirectionMultiPrise(aire.pfinal);
								aire.doitChoisir=false;
							}
						}
						else{
							if(aire.estJouable(aire.pfinal) || (aire.fenetre.engine.getCurrentGame().enCombo && aire.pionCombo.position.ligne==aire.pfinal.ligne && aire.pionCombo.position.colonne==aire.pfinal.colonne ) ){
								System.out.println("---------OUI c'est jouable");
								aire.pCourant.colonne=aire.pfinal.colonne;
								aire.pCourant.ligne=aire.pfinal.ligne;
								aire.pionCliquer=true;
								System.out.println("Point: "+aire.pCourant.ligne+" "+aire.pCourant.colonne);
							}
						}
					}
					aire.repaint();
				}
			} else if(buttonDown == MouseEvent.BUTTON2) {// Bouton du MILIEU enfonce
			} else if(buttonDown == MouseEvent.BUTTON3) {// Bouton DROIT enfonce
				//if(!aire.fenetre.engine.getCurrentGame().enCombo)
					aire.pionCliquer=false;
				aire.repaint();
		    }
			
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		System.out.println("je l'ai relaché");
		if(!aire.fenetre.engine.getCurrentGame().joueurCourant.aiPlayer && !aire.fenetre.engine.getCurrentGame().isPaused()){
			int buttonDown = e.getButton();
			if (buttonDown == MouseEvent.BUTTON1) {// Bouton GAUCHE enfonce
				aire.pfinal=new Coordonnee(-1,-1);
				aire.pfinal=position(e.getX(),e.getY());
				if(aire.pfinal.colonne!=-1 && aire.pfinal.ligne!=-1){
					if(aire.pionCliquer){
						if(aire.fenetre.engine.getJoueurCourant() instanceof HumanPlayer){
							((HumanPlayer)aire.fenetre.engine.getJoueurCourant()).setCoup(aire.pCourant,aire.pfinal);
							aire.pionCliquer=false;
						}
					}
					aire.repaint();
				}
			} else if(buttonDown == MouseEvent.BUTTON2) {// Bouton du MILIEU enfonce
			} else if(buttonDown == MouseEvent.BUTTON3) {// Bouton DROIT enfonce
				//if(!aire.fenetre.engine.getCurrentGame().enCombo)
					aire.pionCliquer=false;
				aire.repaint();
		    }
			
		}
	}
	
	public int sqr(int a) {
        return a*a;
    }
 
    public int distance(Coordonnee p1, Coordonnee p2) {	
        return (int)Math.sqrt(sqr(p2.colonne - p1.colonne) + sqr(p2.ligne - p1.ligne));
    } 

    public Coordonnee position(int x,int y){
    	Coordonnee p=new Coordonnee(-1,-1);	
		p.colonne=x-aire.originePlateauX-((int)(aire.CoordonneesPlateau[0]*aire.etir-aire.segment));
		p.ligne=y-aire.originePlateauY-((int)(aire.CoordonneesPlateau[1]*aire.etir-aire.segment));
		int nbCoteLargeur=p.colonne/(int)aire.segment;
		int nbCoteHauteur=p.ligne/(int)aire.segment;
	//	System.out.println(" ( " + p.colonne + " , " + p.ligne + " ) ");
	//	System.out.println(" ( " + nbCoteLargeur + " , " + nbCoteHauteur + " ) ");
					
		Coordonnee p1=new Coordonnee((int)(nbCoteHauteur*aire.segment),(int)(nbCoteLargeur*aire.segment));// haut gauche
		Coordonnee p2=new Coordonnee((int)(nbCoteHauteur*aire.segment),(int)((nbCoteLargeur+1)*aire.segment));//haut droit
		Coordonnee p3=new Coordonnee((int)((nbCoteHauteur+1)*aire.segment),(int)(nbCoteLargeur*aire.segment));//bas gauche
		Coordonnee p4=new Coordonnee((int)((nbCoteHauteur+1)*aire.segment),(int)((nbCoteLargeur+1)*aire.segment));//bas droit
		Coordonnee pfinal=new Coordonnee(-1,-1);
		
		if(distance(p,p1)<=(aire.tailleJeton/2)){
			pfinal.colonne=nbCoteLargeur-1;
			pfinal.ligne=nbCoteHauteur-1;
		}
		else if(distance(p,p2)<=(aire.tailleJeton/2)){
			pfinal.colonne=nbCoteLargeur;
			pfinal.ligne=nbCoteHauteur-1;
		}
		else if(distance(p,p3)<=(aire.tailleJeton/2)){
			pfinal.colonne=nbCoteLargeur-1;
			pfinal.ligne=nbCoteHauteur;		
		}
		else if(distance(p,p4)<=(aire.tailleJeton/2)){
			pfinal.colonne=nbCoteLargeur;
			pfinal.ligne=nbCoteHauteur;
		}
	//	System.out.println("/////pfinal/////// "+pfinal.ligne+" "+pfinal.colonne);
		return pfinal;
    	
    }
}
