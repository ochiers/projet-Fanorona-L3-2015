package IHM;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JRadioButtonMenuItem;

import AI.HumanPlayer;

public class EcouteurPreferences implements ActionListener{
		Preferences pref;
		
		public EcouteurPreferences(Preferences p){
			pref = p;
		}

		public void actionPerformed(ActionEvent e) {
		
			// CHANGER FOND
			if(pref.rb1.isSelected() == true){
				pref.fenetre.panelAccueil = new ImagePanel(pref.rb1.getIcon(), pref.fenetre.frame.getWidth(), pref.fenetre.frame.getHeight());
				pref.fenetre.frame.repaint();
			}
			if(pref.rb2.isSelected() == true){
				pref.fenetre.panelAccueil = new ImagePanel(pref.rb2.getIcon(), pref.fenetre.frame.getWidth(), pref.fenetre.frame.getHeight());
				pref.fenetre.frame.repaint();
			}
			if(pref.rb3.isSelected() == true){
				pref.fenetre.panelAccueil = new ImagePanel(pref.rb3.getIcon(), pref.fenetre.frame.getWidth(), pref.fenetre.frame.getHeight());
				pref.fenetre.frame.repaint();
			}
			if(pref.rb4.isSelected() == true){
				pref.fenetre.panelAccueil = new ImagePanel(pref.rb4.getIcon(), pref.fenetre.frame.getWidth(), pref.fenetre.frame.getHeight());
				pref.fenetre.frame.repaint();
			}
			if(pref.rb5.isSelected() == true){
				pref.fenetre.panelAccueil = new ImagePanel(pref.rb5.getIcon(), pref.fenetre.frame.getWidth(), pref.fenetre.frame.getHeight());
				pref.fenetre.frame.repaint();
			}
			if(pref.rb6.isSelected() == true){
				pref.fenetre.panelAccueil = new ImagePanel(pref.rb6.getIcon(), pref.fenetre.frame.getWidth(), pref.fenetre.frame.getHeight());
				pref.fenetre.frame.repaint();
			}
			if(pref.rb7.isSelected() == true){
				pref.fenetre.panelAccueil = new ImagePanel(pref.rb7.getIcon(), pref.fenetre.frame.getWidth(), pref.fenetre.frame.getHeight());
				pref.fenetre.frame.repaint();
			}
			if(pref.rb8.isSelected() == true){
				pref.fenetre.panelAccueil = new ImagePanel(pref.rb8.getIcon(), pref.fenetre.frame.getWidth(), pref.fenetre.frame.getHeight());
				pref.fenetre.frame.repaint();
			}
			// CHANGER NOMS
			if (pref.nameJ1.equals("Humain")){
				pref.fenetre.engine.partieCourante.joueurBlanc.name = pref.name1.getText();
				pref.fenetre.idj1 = new JLabel((String)(pref.name1.getText()));
				pref.fenetre.levelj1 = new JLabel(" Bonne Chance ! ");
				pref.fenetre.frame.repaint();
			}
			if (pref.nameJ1.equals("Humain")){
				pref.fenetre.engine.partieCourante.joueurNoir.name = pref.name2.getText();
				pref.fenetre.idj2 = new JLabel((String)(pref.name2.getText()));
				pref.fenetre.levelj2 = new JLabel(" Bonne Chance ! ");
				pref.fenetre.frame.repaint();
			}
			// CHANGER COULEURS PIONS
			/*if(pref.rb10.isSelected() == true){
				if(rb11.isSelected() == true){}
				if(rb21.isSelected() == true){}
				if(rb31.isSelected() == true){}
				if(rb41.isSelected() == true){}
				if(rb51.isSelected() == true){}
				if(rb61.isSelected() == true){}
				if(rb71.isSelected() == true){}
			}
			if(pref.rb20.isSelected() == true){
				if(rb11.isSelected() == true){}
				if(rb21.isSelected() == true){}
				if(rb31.isSelected() == true){}
				if(rb41.isSelected() == true){}
				if(rb51.isSelected() == true){}
				if(rb61.isSelected() == true){}
				if(rb71.isSelected() == true){}
			}
			if(pref.rb30.isSelected() == true){
				if(rb11.isSelected() == true){}
				if(rb21.isSelected() == true){}
				if(rb31.isSelected() == true){}
				if(rb41.isSelected() == true){}
				if(rb51.isSelected() == true){}
				if(rb61.isSelected() == true){}
				if(rb71.isSelected() == true){}
			}
			if(pref.rb40.isSelected() == true){
				if(rb11.isSelected() == true){}
				if(rb21.isSelected() == true){}
				if(rb31.isSelected() == true){}
				if(rb41.isSelected() == true){}
				if(rb51.isSelected() == true){}
				if(rb61.isSelected() == true){}
				if(rb71.isSelected() == true){}
			}
			if(pref.rb50.isSelected() == true){
				if(rb11.isSelected() == true){}
				if(rb21.isSelected() == true){}
				if(rb31.isSelected() == true){}
				if(rb41.isSelected() == true){}
				if(rb51.isSelected() == true){}
				if(rb61.isSelected() == true){}
				if(rb71.isSelected() == true){}
			}
			if(pref.rb60.isSelected() == true){
				if(rb11.isSelected() == true){}
				if(rb21.isSelected() == true){}
				if(rb31.isSelected() == true){}
				if(rb41.isSelected() == true){}
				if(rb51.isSelected() == true){}
				if(rb61.isSelected() == true){}
				if(rb71.isSelected() == true){}
			}
			if(pref.rb70.isSelected() == true){
				if(rb11.isSelected() == true){}
				if(rb21.isSelected() == true){}
				if(rb31.isSelected() == true){}
				if(rb41.isSelected() == true){}
				if(rb51.isSelected() == true){}
				if(rb61.isSelected() == true){}
				if(rb71.isSelected() == true){}
			}*/
			
			// ACCEPTER ET ANNULER
			if(e.getSource() == pref.accept){
				pref.fenetre.frame.repaint();
				pref.fenetre.frame3.setVisible(false);
			}
			if(e.getSource() == pref.annuler){
				System.out.println(" Pas de modifications ");
				pref.fenetre.frame3.setVisible(false);
			}
			else System.out.println("Erreur bouton switch");
			}
		}

}
