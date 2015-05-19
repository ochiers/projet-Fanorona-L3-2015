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
			break;
		case " Recommencer ":
			break;
		case " Sauvegarder ":
			break;
		case " Charger ":
			break;
		case " Quitter ":
			System.exit(0);
			break;
		case " Paramètres Partie ":
			break;
		case " Préférences ":
			break;
		case " Historique Scores ":
			break;
		case " Règles du Jeu ":
			break;
		case " A Propos ":
			break;
		case " Annuler Coup ":
			break;
		case " Refaire Coup ":
			break;
		case " Pause ":
			break;
		case " Fin du tour ":
			break;
		default:
			System.out.println("Erreur bouton switch");
			break;
		}
	}

}
