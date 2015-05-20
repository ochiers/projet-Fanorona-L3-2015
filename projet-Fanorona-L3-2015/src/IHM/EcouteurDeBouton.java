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
			//TODO
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
			//TODO
			break;
		case " Préférences ":
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
			//TODO
			break;
		case " Refaire Coup ":
			//TODO
			break;
		case " Pause ":
			//TODO
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
