package IHM;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import AI.*;

public class EcouteurDeBouton implements ActionListener{
	Fenetre fenetre;
	String message;
	
	public EcouteurDeBouton(Fenetre f,String s){
		fenetre=f;
		message=s;
	}
	
	public void actionPerformed(ActionEvent e) {
		Dimension size = new Dimension(9,5);
		switch (message){
		case " Nouvelle Partie ":
			if(fenetre.mode==1){
				fenetre.engine.nouvellePartie(new HumanPlayer(fenetre.engine,false,fenetre.nameJ1), new HumanPlayer(fenetre.engine,false,fenetre.nameJ2),0,size);
			}else if(fenetre.mode==2){
				if(fenetre.lvlPC1==1){
					fenetre.engine.nouvellePartie(new HumanPlayer(fenetre.engine,false,fenetre.nameJ1), new EasyAI(fenetre.engine,true,"Ordi Facile"),0,size);
				}else if(fenetre.lvlPC1==2){
					fenetre.engine.nouvellePartie(new HumanPlayer(fenetre.engine,false,fenetre.nameJ1), new MediumAI(fenetre.engine,true,"Ordi Moyen"),0,size);
				}else if(fenetre.lvlPC1==3){
					fenetre.engine.nouvellePartie(new HumanPlayer(fenetre.engine,false,fenetre.nameJ1), new HardAI(fenetre.engine,true,"Ordi Difficile"),0,size);
				}
			}else if(fenetre.mode==3){
				if(fenetre.lvlPC1==1 && fenetre.lvlPC2==1){
					fenetre.engine.nouvellePartie(new EasyAI(fenetre.engine,true,"Ordi1 Facile"), new EasyAI(fenetre.engine,true,"Ordi2 Facile"),0,size);
				}else if(fenetre.lvlPC1==1 && fenetre.lvlPC2==2){
					fenetre.engine.nouvellePartie(new EasyAI(fenetre.engine,true,"Ordi1 Facile"), new MediumAI(fenetre.engine,true,"Ordi2 Moyen"),0,size);
				}else if(fenetre.lvlPC1==1 && fenetre.lvlPC2==3){
					fenetre.engine.nouvellePartie(new EasyAI(fenetre.engine,true,"Ordi1 Facile"), new HardAI(fenetre.engine,true,"Ordi2 Difficile"),0,size);
				}else if(fenetre.lvlPC1==2 && fenetre.lvlPC2==1){
					fenetre.engine.nouvellePartie(new MediumAI(fenetre.engine,true,"Ordi1 Moyen"), new EasyAI(fenetre.engine,true,"Ordi2 Facile"),0,size);
				}else if(fenetre.lvlPC1==2 && fenetre.lvlPC2==2){
					fenetre.engine.nouvellePartie(new MediumAI(fenetre.engine,true,"Ordi1 Moyen"), new MediumAI(fenetre.engine,true,"Ordi2 Moyen"),0,size);
				}else if(fenetre.lvlPC1==2 && fenetre.lvlPC2==3){
					fenetre.engine.nouvellePartie(new MediumAI(fenetre.engine,true,"Ordi1 Moyen"), new HardAI(fenetre.engine,true,"Ordi2 Difficile"),0,size);
				}else if(fenetre.lvlPC1==3 && fenetre.lvlPC2==1){
					fenetre.engine.nouvellePartie(new HardAI(fenetre.engine,true,"Ordi1 Difficile"), new EasyAI(fenetre.engine,true,"Ordi2 Facile"),0,size);
				}else if(fenetre.lvlPC1==3 && fenetre.lvlPC2==2){
					fenetre.engine.nouvellePartie(new HardAI(fenetre.engine,true,"Ordi1 Difficile"), new MediumAI(fenetre.engine,true,"Ordi2 Moyen"),0,size);
				}else if(fenetre.lvlPC1==3 && fenetre.lvlPC2==3){
					fenetre.engine.nouvellePartie(new HardAI(fenetre.engine,true,"Ordi1 Difficile"), new HardAI(fenetre.engine,true,"Ordi2 Difficile"),0,size);
				}
			}
			//fenetre.stopper.setText(" Reprendre ");
			//System.out.println("new partie");
			break;
			
		case " Recommencer ":
			//System.out.println("MODE: "+fenetre.mode+" - PC1: "+fenetre.lvlPC1+" - PC2: "+fenetre.lvlPC2+" - J1: "+fenetre.nameJ1+" - J2: "+fenetre.nameJ2);
			fenetre.engine.nouvellePartie(fenetre.engine.getCurrentGame().joueurBlanc, fenetre.engine.getCurrentGame().joueurNoir,0, size);
			fenetre.stopper.setText(" Reprendre ");
			break;
		case " Sauvegarder ":
			//System.out.println("save-----------");
			JFileChooser save = new JFileChooser();
			save.showSaveDialog(Fenetre.frame);
			fenetre.engine.sauvegarderPartie(save.getSelectedFile().getAbsolutePath());
			break;
		case " Charger ":
			//System.out.println("load------------");
			JFileChooser load = new JFileChooser();
			load.showOpenDialog(Fenetre.frame);
			fenetre.engine.chargerPartie(load.getSelectedFile().getAbsolutePath());
			break;
		case " Quitter ":
			System.exit(0);
			break;
		case " Parametres Partie ":
			if(fenetre.mode==1){
				fenetre.parametre.r1b1.setSelected(true);
			}else if(fenetre.mode==2){
				fenetre.parametre.r1b2.setSelected(true);
			}else if(fenetre.mode==3){
				fenetre.parametre.r1b3.setSelected(true);
			}
			
			if(fenetre.lvlPC1==1){
				fenetre.parametre.r2b1.setSelected(true);
			}else if(fenetre.lvlPC1==2){
				fenetre.parametre.r2b2.setSelected(true);
			}else if(fenetre.lvlPC1==3){
				fenetre.parametre.r2b3.setSelected(true);
			}
			if(fenetre.lvlPC2==1){
				fenetre.parametre.r3b1.setSelected(true);
			}else if(fenetre.lvlPC1==2){
				fenetre.parametre.r3b2.setSelected(true);
			}else if(fenetre.lvlPC2==3){
				fenetre.parametre.r3b3.setSelected(true);
			}
			fenetre.parametre.saveMode=fenetre.mode;
			fenetre.parametre.savelvlPC1=fenetre.lvlPC1;
			fenetre.parametre.savelvlPC2=fenetre.lvlPC2;
			Fenetre.frame2.setVisible(true);
			//TODO
			break;
		case " Preferences ":
			fenetre.preference.resetBouton();
			fenetre.preference.save1=fenetre.pion1;
			fenetre.preference.save2=fenetre.pion2;
			Fenetre.frame3.setVisible(true);
			break;
		case " Historique Scores ":
			//TODO
			break;
		case " Regles du Jeu ":
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
			if(fenetre.engine.getCurrentGame().isPaused()){
				fenetre.engine.getCurrentGame().reprendre();
				fenetre.stopper.setText(" Pause ");
				System.out.println("reprise");
			}
			else{
				fenetre.engine.getCurrentGame().pause();
				fenetre.stopper.setText(" Reprendre ");
				System.out.println("en pause");
			}
			break;
		case " Fin du tour ":
			fenetre.monDessin.pionCliquer=false;
			fenetre.engine.getCurrentGame().finirSonTour();
			break;
		default:
			System.out.println("Erreur bouton switch");
			break;
		}
	}

}
