package IHM;

import java.awt.Color;
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
		if(!aire.fenetre.engine.partieCourante.joueurCourant.aiPlayer){
			int buttonDown = e.getButton();
			if (buttonDown == MouseEvent.BUTTON1) {// Bouton GAUCHE enfoncé
				Coordonnee p=new Coordonnee(-1,-1);	
				p.colonne=e.getX();
				p.ligne=e.getY();
				int nbCoteLargeur=p.colonne/aire.tailleSegment;
				int nbCoteHauteur=p.ligne/aire.tailleSegment;
			
				Coordonnee p1=new Coordonnee(nbCoteHauteur*aire.tailleSegment,nbCoteLargeur*aire.tailleSegment);// haut gauche
				Coordonnee p2=new Coordonnee(nbCoteHauteur*aire.tailleSegment,(nbCoteLargeur+1)*aire.tailleSegment);//haut droit
				Coordonnee p3=new Coordonnee((nbCoteHauteur+1)*aire.tailleSegment,nbCoteLargeur*aire.tailleSegment);//bas gauche
				Coordonnee p4=new Coordonnee((nbCoteHauteur+1)*aire.tailleSegment,(nbCoteLargeur+1)*aire.tailleSegment);//bas droit
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
				if(pfinal.colonne!=-1 && pfinal.ligne!=-1){
					if(aire.pionCliquer){
							((HumanPlayer)aire.fenetre.engine.partieCourante.joueurCourant).setCoup(aire.pCourant,pfinal);
							aire.pionCliquer=false;
						/*	if(aire.pionCombo!=null){
								aire.pionCombo.position.ligne=pfinal.ligne;
								aire.pionCombo.position.colonne=pfinal.colonne;
							}*/
					}
					else{
						if(aire.doitChoisir){
							if(aire.estUnChoix(pfinal)){
								((HumanPlayer)aire.fenetre.engine.partieCourante.joueurCourant).setDirectionMultiPrise(pfinal);
								aire.doitChoisir=false;
							}
						}
						else{
							if(aire.estJouable(pfinal) || (aire.fenetre.engine.partieCourante.enCombo && aire.pionCombo.position.ligne==pfinal.ligne && aire.pionCombo.position.colonne==pfinal.colonne ) ){
								System.out.println("---------OUI c'est jouable");
								aire.pCourant.colonne=pfinal.colonne;
								aire.pCourant.ligne=pfinal.ligne;
								aire.pionCliquer=true;
								System.out.println("Point: "+aire.pCourant.ligne+" "+aire.pCourant.colonne);
							}
						}
					}
					aire.repaint();
				}
			} else if(buttonDown == MouseEvent.BUTTON2) {// Bouton du MILIEU enfoncé
			} else if(buttonDown == MouseEvent.BUTTON3) {// Bouton DROIT enfoncé
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
		/*
		 	System.out.println(e.getX());
		 	System.out.println(e.getY());
		 */
	}
	public void mouseReleased(MouseEvent e) {
	}
	
	public int sqr(int a) {
        return a*a;
    }
 
    public int distance(Coordonnee p1, Coordonnee p2) {	
        return (int)Math.sqrt(sqr(p2.colonne - p1.colonne) + sqr(p2.ligne - p1.ligne));
    } 

}
