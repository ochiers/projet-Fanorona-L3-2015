package IHM;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import AI.HumanPlayer;


public class EcouteurDeSouris implements MouseListener{
	AireDeDessin aire;
	//Point pSave=new Point();
	
	public EcouteurDeSouris(AireDeDessin a){
		aire=a;
	}

	public void mouseClicked(MouseEvent e) {
		int buttonDown = e.getButton();
		if (buttonDown == MouseEvent.BUTTON1) {// Bouton GAUCHE enfoncé
			Point p=new Point();	
			p.y=e.getX();
			p.x=e.getY();
			int nbCoteLargeur=p.y/aire.tailleSegment;
			int nbCoteHauteur=p.x/aire.tailleSegment;
		
			Point p1=new Point(nbCoteHauteur*aire.tailleSegment,nbCoteLargeur*aire.tailleSegment);// haut gauche
			Point p2=new Point(nbCoteHauteur*aire.tailleSegment,(nbCoteLargeur+1)*aire.tailleSegment);//haut droit
			Point p3=new Point((nbCoteHauteur+1)*aire.tailleSegment,nbCoteLargeur*aire.tailleSegment);//bas gauche
			Point p4=new Point((nbCoteHauteur+1)*aire.tailleSegment,(nbCoteLargeur+1)*aire.tailleSegment);//bas droit
			Point pfinal=new Point(-1,-1);
			
			if(distance(p,p1)<=(aire.tailleJeton/2)){
				pfinal.y=nbCoteLargeur-1;
				pfinal.x=nbCoteHauteur-1;
			}
			else if(distance(p,p2)<=(aire.tailleJeton/2)){
				pfinal.y=nbCoteLargeur;
				pfinal.x=nbCoteHauteur-1;
			}
			else if(distance(p,p3)<=(aire.tailleJeton/2)){
				pfinal.y=nbCoteLargeur-1;
				pfinal.x=nbCoteHauteur;		
			}
			else if(distance(p,p4)<=(aire.tailleJeton/2)){
				pfinal.y=nbCoteLargeur;
				pfinal.x=nbCoteHauteur;
			}
		//	System.out.println("pfinal: "+pfinal.x+" "+pfinal.y);
			if(pfinal.y!=-1 && pfinal.x!=-1){
				if(aire.pionCliquer){
					if(pfinal.y != aire.pCourant.y|| pfinal.x != aire.pCourant.x){
						//aire.fenetre.engine.partieCourante.matricePlateau[pfinal.y][pfinal.x].pion=aire.fenetre.engine.partieCourante.matricePlateau[pSave.y][pSave.x].pion;
					//	aire.fenetre.engine.partieCourante.matricePlateau[pSave.y][pSave.x].pion=null;
						((HumanPlayer)aire.fenetre.engine.partieCourante.joueurCourant).setCoup(aire.pCourant,pfinal);
						System.out.println("Jouer: "+aire.pCourant.y+" "+aire.pCourant.x+" en "+pfinal.y+" "+pfinal.x);
						aire.pionCliquer=false;
						System.out.println("test2");
					}
				}
				else{
					if(aire.fenetre.engine.partieCourante.estJouable(pfinal)){
						System.out.println("---------OUI c'est jouable");
						aire.pCourant.y=pfinal.y;
						aire.pCourant.x=pfinal.x;
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
