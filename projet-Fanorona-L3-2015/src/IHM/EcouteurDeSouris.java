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
		//	System.out.println("pfinal: "+pfinal.x+" "+pfinal.y);
			if(pfinal.colonne!=-1 && pfinal.ligne!=-1){
				if(aire.pionCliquer){
					
						((HumanPlayer)aire.fenetre.engine.partieCourante.joueurCourant).setCoup(aire.pCourant,pfinal);
						//System.out.println("Jouer: "+aire.pCourant.x+" "+aire.pCourant.y+" en "+pfinal.x+" "+pfinal.y);
						aire.pionCliquer=false;
						//System.out.println("test2");
				}
				else{
					if(aire.doitChoisir){
						if(aire.estUnChoix(pfinal)){
							System.out.println("----------------c'est un choix");
						}
						else{
							System.out.println("----------------non ce n'est pas un choix");
						}
					}
					else{
						if(aire.fenetre.engine.partieCourante.estJouable(pfinal)){
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
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
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
