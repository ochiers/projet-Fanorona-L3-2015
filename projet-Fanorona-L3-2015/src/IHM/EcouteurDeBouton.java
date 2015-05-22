package IHM;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import engine.*;
import AI.*;

public class EcouteurDeBouton implements ActionListener{
	Fenetre fenetre;
	String message;
	
	public EcouteurDeBouton(Fenetre f,String s){
		fenetre=f;
		message=s;
	}
	
	public void actionPerformed(ActionEvent e) {
		switch (message){
		case " Nouvelle Partie ":
			fenetre.engine.nouvellePartie(new HumanPlayer(fenetre.engine,false,"player1"), new HumanPlayer(fenetre.engine,false,"player2"),0, 5,9);
			System.out.println("new partie");
			break;
		case " Recommencer ":
			
			//TODO
			break;
		case " Sauvegarder ":
			//TODO
			break;
		case " Charger ":
			//TODO
			break;
		case " Quitter ":
			System.exit(0);
			break;
		case " Paramètres Partie ":
			fenetre.frame2.setVisible(true);
			//TODO
			break;
		case " Préférences ":
			fenetre.frame3.setVisible(true);
			//TODO
			break;
		case " Historique Scores ":
			//TODO
			break;
		case " Règles du Jeu ":
			//TODO
			break;
		case " A Propos ":
			//TODO
			break;
		case " Annuler Coup ":
			fenetre.engine.annuler();
			//TODO
			break;
		case " Refaire Coup ":
			fenetre.engine.refaire();
			//TODO
			break;
		case " Pause ":
			if(fenetre.engine.partieCourante.isPaused()){
				fenetre.engine.partieCourante.reprendre();
				fenetre.stopper.setText(" Pause ");
				System.out.println("reprise");
			}
			else{
				fenetre.engine.partieCourante.pause();
				fenetre.stopper.setText(" Reprendre ");
				System.out.println("en pause");
			}
			break;
		case " Fin du tour ":
			//TODO
			break;
		default:
			System.out.println("Erreur bouton switch");
			break;
		}
	}

}
