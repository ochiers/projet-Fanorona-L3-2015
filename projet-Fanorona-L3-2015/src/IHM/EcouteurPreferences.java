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
			}
			// CHANGER COULEURS PIONS
			if(pref.rb10.isSelected() == true){
				pref.fenetre.pion1 = Color.black;
				pref.rb11.setEnabled(false);
				if(pref.rb21.isSelected() == true) pref.fenetre.pion2 = Color.white;
				if(pref.rb31.isSelected() == true) pref.fenetre.pion2 = Color.blue;
				if(pref.rb41.isSelected() == true) pref.fenetre.pion2 = Color.red;
				if(pref.rb51.isSelected() == true) pref.fenetre.pion2 = Color.green;
				if(pref.rb61.isSelected() == true) pref.fenetre.pion2 = Color.yellow;
				//if(pref.rb71.isSelected() == true) pref.fenetre.pion2 = Color.multi;
			}
			if(pref.rb20.isSelected() == true){
				pref.fenetre.pion1 = Color.white;
				pref.rb21.setEnabled(false);
				if(pref.rb11.isSelected() == true) pref.fenetre.pion2 = Color.black;
				if(pref.rb31.isSelected() == true) pref.fenetre.pion2 = Color.blue;
				if(pref.rb41.isSelected() == true) pref.fenetre.pion2 = Color.red;
				if(pref.rb51.isSelected() == true) pref.fenetre.pion2 = Color.green;
				if(pref.rb61.isSelected() == true) pref.fenetre.pion2 = Color.yellow;
				//if(pref.rb71.isSelected() == true) pref.fenetre.pion2 = Color.multi;
			}
			if(pref.rb30.isSelected() == true){
				pref.fenetre.pion1 = Color.blue;
				pref.rb31.setEnabled(false);
				if(pref.rb11.isSelected() == true) pref.fenetre.pion2 = Color.black;
				if(pref.rb21.isSelected() == true) pref.fenetre.pion2 = Color.white;
				if(pref.rb41.isSelected() == true) pref.fenetre.pion2 = Color.red;
				if(pref.rb51.isSelected() == true) pref.fenetre.pion2 = Color.green;
				if(pref.rb61.isSelected() == true) pref.fenetre.pion2 = Color.yellow;
				//if(pref.rb71.isSelected() == true) pref.fenetre.pion2 = Color.multi;
			}
			if(pref.rb40.isSelected() == true){
				pref.fenetre.pion1 = Color.red;
				pref.rb41.setEnabled(false);
				if(pref.rb11.isSelected() == true) pref.fenetre.pion2 = Color.black;
				if(pref.rb21.isSelected() == true) pref.fenetre.pion2 = Color.white;
				if(pref.rb31.isSelected() == true) pref.fenetre.pion2 = Color.blue;
				if(pref.rb51.isSelected() == true) pref.fenetre.pion2 = Color.green;
				if(pref.rb61.isSelected() == true) pref.fenetre.pion2 = Color.yellow;
				//if(pref.rb71.isSelected() == true) pref.fenetre.pion2 = Color.multi;
			}
			if(pref.rb50.isSelected() == true){
				pref.fenetre.pion1 = Color.green;
				pref.rb51.setEnabled(false);
				if(pref.rb11.isSelected() == true) pref.fenetre.pion2 = Color.black;
				if(pref.rb21.isSelected() == true) pref.fenetre.pion2 = Color.white;
				if(pref.rb31.isSelected() == true) pref.fenetre.pion2 = Color.blue;
				if(pref.rb41.isSelected() == true) pref.fenetre.pion2 = Color.red;
				if(pref.rb61.isSelected() == true) pref.fenetre.pion2 = Color.yellow;
				//if(pref.rb71.isSelected() == true) pref.fenetre.pion2 = Color.multi;
			}
			if(pref.rb60.isSelected() == true){
				pref.fenetre.pion1 = Color.yellow;
				pref.rb61.setEnabled(false);
				if(pref.rb11.isSelected() == true) pref.fenetre.pion2 = Color.black;
				if(pref.rb21.isSelected() == true) pref.fenetre.pion2 = Color.white;
				if(pref.rb31.isSelected() == true) pref.fenetre.pion2 = Color.blue;
				if(pref.rb41.isSelected() == true) pref.fenetre.pion2 = Color.red;
				if(pref.rb51.isSelected() == true) pref.fenetre.pion2 = Color.green;
				//if(pref.rb71.isSelected() == true) pref.fenetre.pion2 = Color.multi;
			}
			/*if(pref.rb70.isSelected() == true){
				pref.fenetre.pion1 = Color.multi;
				pref.rb61.setEnabled(false);
				if(pref.rb11.isSelected() == true) pref.fenetre.pion2 = Color.black;
				if(pref.rb21.isSelected() == true) pref.fenetre.pion2 = Color.white;
				if(pref.rb31.isSelected() == true) pref.fenetre.pion2 = Color.blue;
				if(pref.rb41.isSelected() == true) pref.fenetre.pion2 = Color.red;
				if(pref.rb51.isSelected() == true) pref.fenetre.pion2 = Color.green;
				if(pref.rb61.isSelected() == true) pref.fenetre.pion2 = Color.yellow;
			}*/
			if(pref.rb11.isSelected() == true){
				pref.fenetre.pion2 = Color.black;
				pref.rb10.setEnabled(false);
				if(pref.rb20.isSelected() == true) pref.fenetre.pion1 = Color.white;
				if(pref.rb30.isSelected() == true) pref.fenetre.pion1 = Color.blue;
				if(pref.rb40.isSelected() == true) pref.fenetre.pion1 = Color.red;
				if(pref.rb50.isSelected() == true) pref.fenetre.pion1 = Color.green;
				if(pref.rb60.isSelected() == true) pref.fenetre.pion1 = Color.yellow;
				//if(pref.rb70.isSelected() == true) pref.fenetre.pion1 = Color.multi;
			}
			if(pref.rb21.isSelected() == true){
				pref.fenetre.pion2 = Color.white;
				pref.rb20.setEnabled(false);
				if(pref.rb10.isSelected() == true) pref.fenetre.pion1 = Color.black;
				if(pref.rb30.isSelected() == true) pref.fenetre.pion1 = Color.blue;
				if(pref.rb40.isSelected() == true) pref.fenetre.pion1 = Color.red;
				if(pref.rb50.isSelected() == true) pref.fenetre.pion1 = Color.green;
				if(pref.rb60.isSelected() == true) pref.fenetre.pion1 = Color.yellow;
				//if(pref.rb70.isSelected() == true) pref.fenetre.pion1 = Color.multi;
			}
			if(pref.rb31.isSelected() == true){
				pref.fenetre.pion2 = Color.blue;
				pref.rb30.setEnabled(false);
				if(pref.rb10.isSelected() == true) pref.fenetre.pion1 = Color.black;
				if(pref.rb20.isSelected() == true) pref.fenetre.pion1 = Color.white;
				if(pref.rb40.isSelected() == true) pref.fenetre.pion1 = Color.red;
				if(pref.rb50.isSelected() == true) pref.fenetre.pion1 = Color.green;
				if(pref.rb60.isSelected() == true) pref.fenetre.pion1 = Color.yellow;
				//if(pref.rb70.isSelected() == true) pref.fenetre.pion1 = Color.multi;
			}
			if(pref.rb41.isSelected() == true){
				pref.fenetre.pion2 = Color.red;
				pref.rb40.setEnabled(false);
				if(pref.rb10.isSelected() == true) pref.fenetre.pion1 = Color.black;
				if(pref.rb20.isSelected() == true) pref.fenetre.pion1 = Color.white;
				if(pref.rb30.isSelected() == true) pref.fenetre.pion1 = Color.blue;
				if(pref.rb50.isSelected() == true) pref.fenetre.pion1 = Color.green;
				if(pref.rb60.isSelected() == true) pref.fenetre.pion1 = Color.yellow;
				//if(pref.rb70.isSelected() == true) pref.fenetre.pion1 = Color.multi;
			}
			if(pref.rb51.isSelected() == true){
				pref.fenetre.pion2 = Color.green;
				pref.rb50.setEnabled(false);
				if(pref.rb10.isSelected() == true) pref.fenetre.pion1 = Color.black;
				if(pref.rb20.isSelected() == true) pref.fenetre.pion1 = Color.white;
				if(pref.rb30.isSelected() == true) pref.fenetre.pion1 = Color.blue;
				if(pref.rb40.isSelected() == true) pref.fenetre.pion1 = Color.red;
				if(pref.rb60.isSelected() == true) pref.fenetre.pion1 = Color.yellow;
				//if(pref.rb70.isSelected() == true) pref.fenetre.pion1 = Color.multi;
			}
			if(pref.rb61.isSelected() == true){
				pref.fenetre.pion2 = Color.yellow;
				pref.rb60.setEnabled(false);
				if(pref.rb10.isSelected() == true) pref.fenetre.pion1 = Color.black;
				if(pref.rb20.isSelected() == true) pref.fenetre.pion1 = Color.white;
				if(pref.rb30.isSelected() == true) pref.fenetre.pion1 = Color.blue;
				if(pref.rb40.isSelected() == true) pref.fenetre.pion1 = Color.red;
				if(pref.rb50.isSelected() == true) pref.fenetre.pion1 = Color.green;
				//if(pref.rb70.isSelected() == true) pref.fenetre.pion1 = Color.multi;
			}
			/*if(pref.rb71.isSelected() == true){
				pref.fenetre.pion2 = Color.multi;
				pref.rb70.setEnabled(false);
				if(pref.rb10.isSelected() == true) pref.fenetre.pion1 = Color.black;
				if(pref.rb20.isSelected() == true) pref.fenetre.pion1 = Color.white;
				if(pref.rb30.isSelected() == true) pref.fenetre.pion1 = Color.blue;
				if(pref.rb40.isSelected() == true) pref.fenetre.pion1 = Color.red;
				if(pref.rb50.isSelected() == true) pref.fenetre.pion1 = Color.green;
				if(pref.rb60.isSelected() == true) pref.fenetre.pion1 = Color.yellow;
			}*/
			// ACCEPTER ET ANNULER
			if(e.getSource() == pref.accept){
				Fenetre.frame.repaint();
				Fenetre.frame3.setVisible(false);
			}
			if(e.getSource() == pref.annuler){
				System.out.println(" Pas de modifications ");
				Fenetre.frame3.setVisible(false);
			}
			else System.out.println("Erreur bouton switch");
		}

}
