package IHM;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import engine.*;
import AI.*;

public class EcouteurParametres implements ActionListener{
	Fenetre fenetre;
	String message;
	
	public EcouteurParametres(Fenetre f,String s){
		fenetre=f;
		message=s;
	}
	
	public void actionPerformed(ActionEvent e) {
		switch (message){
		case " Nouvelle Partie ":
			break;
		case " Recommencer ":
			
			//TODO
			break;
		
		default:
			System.out.println("Erreur bouton switch Parametre");
			break;
		}
	}

}
