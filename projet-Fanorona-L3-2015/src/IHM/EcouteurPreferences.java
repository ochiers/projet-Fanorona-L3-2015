package IHM;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class EcouteurPreferences implements ActionListener{
		Preferences pref;
		
		public EcouteurPreferences(Preferences p){
			pref = p;
		}

		public void actionPerformed(ActionEvent e) {
/*		
			// CHANGER FOND
			if(pref.rb1.isSelected() == true)
				pref.fenetre.panelAccueil = new ImagePanel(pref.rb1.getIcon(), Fenetre.frame.getWidth(), Fenetre.frame.getHeight());
			if(pref.rb2.isSelected() == true)
				pref.fenetre.panelAccueil = new ImagePanel(pref.rb2.getIcon(), Fenetre.frame.getWidth(), Fenetre.frame.getHeight());
			if(pref.rb3.isSelected() == true)
				pref.fenetre.panelAccueil = new ImagePanel(pref.rb3.getIcon(), Fenetre.frame.getWidth(), Fenetre.frame.getHeight());
			if(pref.rb4.isSelected() == true)
				pref.fenetre.panelAccueil = new ImagePanel(pref.rb4.getIcon(), Fenetre.frame.getWidth(), Fenetre.frame.getHeight());
			if(pref.rb5.isSelected() == true)
				pref.fenetre.panelAccueil = new ImagePanel(pref.rb5.getIcon(), Fenetre.frame.getWidth(), Fenetre.frame.getHeight());
			if(pref.rb6.isSelected() == true)
				pref.fenetre.panelAccueil = new ImagePanel(pref.rb6.getIcon(), Fenetre.frame.getWidth(), Fenetre.frame.getHeight());
			if(pref.rb7.isSelected() == true)
				pref.fenetre.panelAccueil = new ImagePanel(pref.rb7.getIcon(), Fenetre.frame.getWidth(), Fenetre.frame.getHeight());
			if(pref.rb8.isSelected() == true)
				pref.fenetre.panelAccueil = new ImagePanel(pref.rb8.getIcon(), Fenetre.frame.getWidth(), Fenetre.frame.getHeight());
			
			
			// CHANGER NOMS
			if (pref.nameJ1.equals("Humain")){
				pref.fenetre.engine.partieCourante.joueurBlanc.name = pref.name1.getText();
				pref.fenetre.idj1 = new JLabel(pref.name1.getText());
				pref.fenetre.levelj1 = new JLabel(" Bonne Chance ! ");
			}
			if (pref.nameJ1.equals("Humain")){
				pref.fenetre.engine.partieCourante.joueurNoir.name = pref.name2.getText();
				pref.fenetre.idj2 = new JLabel(pref.name2.getText());
				pref.fenetre.levelj2 = new JLabel(" Bonne Chance ! ");
			}*/
			
			//COULEUR PIONS
			int i=0;
			if(e.getSource()==pref.bouton[i][0])pref.save1=Color.black;	
			else{
				i++;
				if(e.getSource()==pref.bouton[i][0])pref.save1=Color.white;
				else{
					i++;
					if(e.getSource()==pref.bouton[i][0])pref.save1=Color.blue;
					else{
						i++;
						if(e.getSource()==pref.bouton[i][0])pref.save1=Color.red;
						else{
							i++;
							if(e.getSource()==pref.bouton[i][0])pref.save1=Color.green;
							else{
								i++;
								if(e.getSource()==pref.bouton[i][0])pref.save1=Color.yellow;
			}}}}}
			pref.bouton[i][1].setEnabled(false);
			
			i=0;
			if(e.getSource()==pref.bouton[i][1])pref.save2=Color.black;	
			else{
				i++;
				if(e.getSource()==pref.bouton[i][1])pref.save2=Color.white;
				else{
					i++;
					if(e.getSource()==pref.bouton[i][1])pref.save2=Color.blue;
					else{
						i++;
						if(e.getSource()==pref.bouton[i][1])pref.save2=Color.red;
						else{
							i++;
							if(e.getSource()==pref.bouton[i][1])pref.save2=Color.green;
							else{
								i++;
								if(e.getSource()==pref.bouton[i][1])pref.save2=Color.yellow;
			}}}}}
			pref.bouton[i][0].setEnabled(false);
			pref.desactiveLautrePion();
			
			// ACCEPTER ET ANNULER
			if(e.getSource() == pref.accepter){
				pref.fenetre.pion1=pref.save1;
				pref.fenetre.pion2=pref.save2;
			//	Fenetre.frame3.setVisible(false);
				pref.fenetre.afficherJeu();
			}
			else if(e.getSource() == pref.annuler){
				pref.resetBouton();
			//	Fenetre.frame3.setVisible(false);
			}
		}

}
