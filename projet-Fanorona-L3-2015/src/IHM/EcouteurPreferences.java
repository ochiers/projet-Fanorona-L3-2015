package IHM;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JRadioButtonMenuItem;

import AI.HumanPlayer;

public class EcouteurPreferences implements ActionListener{
		Preferences pref;

		Icon icon;
		int fw = pref.fenetre.fw;
		int fh = pref.fenetre.fh;
		String message;

		public EcouteurPreferences(Preferences p){
			pref = p;
		}

		public EcouteurPreferences(Preferences p,String s){
			pref=p;
			message=s;
		}
		
		public void actionPerformed(ActionEvent e) {
					
			if(pref.rb1.isSelected() == true) pref.fenetre.panelAccueil = new ImagePanel(pref.rb1.getIcon(), fw, fh);
			if(pref.rb2.isSelected() == true) pref.fenetre.panelAccueil = new ImagePanel(pref.rb2.getIcon(), fw, fh);
			if(pref.rb3.isSelected() == true) pref.fenetre.panelAccueil = new ImagePanel(pref.rb3.getIcon(), fw, fh);
			if(pref.rb4.isSelected() == true) pref.fenetre.panelAccueil = new ImagePanel(pref.rb4.getIcon(), fw, fh);
			if(pref.rb5.isSelected() == true) pref.fenetre.panelAccueil = new ImagePanel(pref.rb5.getIcon(), fw, fh);
			if(pref.rb6.isSelected() == true) pref.fenetre.panelAccueil = new ImagePanel(pref.rb6.getIcon(), fw, fh);
			if(pref.rb7.isSelected() == true) pref.fenetre.panelAccueil = new ImagePanel(pref.rb7.getIcon(), fw, fh);
			else pref.fenetre.panelAccueil = new ImagePanel(pref.rb8.getIcon(), fw, fh);
			/*
			if(rb10.isSelected() == true){}
			if(rb20.isSelected() == true){}
			if(rb30.isSelected() == true){}
			if(rb40.isSelected() == true){}
			if(rb50.isSelected() == true){}
			if(rb60.isSelected() == true){}
			if(rb70.isSelected() == true){}
			else{}
			
			if(rb11.isSelected() == true){}
			if(rb21.isSelected() == true){}
			if(rb31.isSelected() == true){}
			if(rb41.isSelected() == true){}
			if(rb51.isSelected() == true){}
			if(rb61.isSelected() == true){}
			if(rb71.isSelected() == true){}
			else{}
			*/
			switch (message){
			case " Accepter ":
				Fenetre.frame.repaint();
				System.exit(0);
				break;
			case " Annuler ":
				System.out.println(" Pas de modifications ");
				System.exit(0);
				break;
			default:
				System.out.println("Erreur bouton switch");
			break;
			}
		}

}
