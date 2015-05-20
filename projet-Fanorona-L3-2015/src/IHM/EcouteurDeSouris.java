package IHM;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import AI.HumanPlayer;


public class EcouteurDeSouris implements MouseListener{
	AireDeDessin aire;
	Point pSave=new Point();
	
	public EcouteurDeSouris(AireDeDessin a){
		aire=a;
	}

	public void mouseClicked(MouseEvent e) {
		int buttonDown = e.getButton();
		if (buttonDown == MouseEvent.BUTTON1) {// Bouton GAUCHE enfoncé
			Point p=new Point();	
			p.x=e.getX();
			p.y=e.getY();
			int nbCoteLargeur=p.x/aire.tailleSegment;
			int nbCoteHauteur=p.y/aire.tailleSegment;
			Point p1=new Point(nbCoteLargeur*aire.tailleSegment,nbCoteHauteur*aire.tailleSegment);// haut gauche
			Point p2=new Point((nbCoteLargeur+1)*aire.tailleSegment,nbCoteHauteur*aire.tailleSegment);//haut droit
			Point p3=new Point(nbCoteLargeur*aire.tailleSegment,(nbCoteHauteur+1)*aire.tailleSegment);//bas gauche
			Point p4=new Point((nbCoteLargeur+1)*aire.tailleSegment,(nbCoteHauteur+1)*aire.tailleSegment);//bas droit
			Point pfinal=new Point(-1,-1);
			
			if(distance(p,p1)<=(aire.tailleJeton/2)){
				pfinal.x=nbCoteLargeur-1;
				pfinal.y=nbCoteHauteur-1;
			}
			else if(distance(p,p2)<=(aire.tailleJeton/2)){
				pfinal.x=nbCoteLargeur;
				pfinal.y=nbCoteHauteur-1;
			}
			else if(distance(p,p3)<=(aire.tailleJeton/2)){
				pfinal.x=nbCoteLargeur-1;
				pfinal.y=nbCoteHauteur;		
			}
			else if(distance(p,p4)<=(aire.tailleJeton/2)){
				pfinal.x=nbCoteLargeur;
				pfinal.y=nbCoteHauteur;
			}
			if(pfinal.x!=-1 && pfinal.y!=-1){
				if(aire.pionCliquer){
					if(pfinal.x != pSave.x || pfinal.y != pSave.y){
						//aire.fenetre.engine.partieCourante.matricePlateau[pfinal.y][pfinal.x].pion=aire.fenetre.engine.partieCourante.matricePlateau[pSave.y][pSave.x].pion;
					//	aire.fenetre.engine.partieCourante.matricePlateau[pSave.y][pSave.x].pion=null;
						((HumanPlayer)aire.fenetre.engine.partieCourante.joueurCourant).setCoup(pSave,pfinal);
						aire.pionCliquer=false;
						System.out.println("test2");
					}
				}
				else{
					if(aire.fenetre.engine.partieCourante.matricePlateau[pfinal.y][pfinal.x].pion!=null){
						aire.pCourant.x=pfinal.x;
						aire.pCourant.y=pfinal.y;
						aire.pionCliquer=true;
						System.out.println("Point: "+aire.pCourant.x+" "+aire.pCourant.y);
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
 
    public int distance(Point p1, Point p2) {	
        return (int)Math.sqrt(sqr(p2.y - p1.y) + sqr(p2.x - p1.x));
    } 

}
