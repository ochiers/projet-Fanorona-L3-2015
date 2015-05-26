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

		Icon icon;
		String message;
		String humain1;
		String humain2;

		public EcouteurPreferences(Preferences p){
			pref = p;
		}

		public EcouteurPreferences(Preferences p,String s){
			pref=p;
			message=s;
		}
		
		public void actionPerformed(ActionEvent e) {

			boolean j1 = pref.nameJ1.equals("Humain");
			boolean j2 = pref.nameJ1.equals("Humain");
		
			if (j1 == true) humain1 = " Humain1 ";
			if (j2 == true) humain2 = " Humain2 ";
			else {
				humain1 = "";
				humain2 = "";
			}
			if(pref.rb1.isSelected() == true) pref.fenetre.panelAccueil = new ImagePanel(pref.rb1.getIcon(), pref.fenetre.frame.getWidth(), pref.fenetre.frame.getHeight());
			if(pref.rb2.isSelected() == true) pref.fenetre.panelAccueil = new ImagePanel(pref.rb2.getIcon(), pref.fenetre.frame.getWidth(), pref.fenetre.frame.getHeight());
			if(pref.rb3.isSelected() == true) pref.fenetre.panelAccueil = new ImagePanel(pref.rb3.getIcon(), pref.fenetre.frame.getWidth(), pref.fenetre.frame.getHeight());
			if(pref.rb4.isSelected() == true) pref.fenetre.panelAccueil = new ImagePanel(pref.rb4.getIcon(), pref.fenetre.frame.getWidth(), pref.fenetre.frame.getHeight());
			if(pref.rb5.isSelected() == true) pref.fenetre.panelAccueil = new ImagePanel(pref.rb5.getIcon(), pref.fenetre.frame.getWidth(), pref.fenetre.frame.getHeight());
			if(pref.rb6.isSelected() == true) pref.fenetre.panelAccueil = new ImagePanel(pref.rb6.getIcon(), pref.fenetre.frame.getWidth(), pref.fenetre.frame.getHeight());
			if(pref.rb7.isSelected() == true) pref.fenetre.panelAccueil = new ImagePanel(pref.rb7.getIcon(), pref.fenetre.frame.getWidth(), pref.fenetre.frame.getHeight());
			else pref.fenetre.panelAccueil = new ImagePanel(pref.rb8.getIcon(), pref.fenetre.frame.getWidth(), pref.fenetre.frame.getHeight());
			
			if(pref.rb1.isSelected() == true) message = "rb1";
			if(pref.rb2.isSelected() == true) message = "rb2";			
			if(pref.rb3.isSelected() == true) message = "rb3";
			if(pref.rb4.isSelected() == true) message = "rb4";
			if(pref.rb5.isSelected() == true) message = "rb5";
			if(pref.rb6.isSelected() == true) message = "rb6";
			if(pref.rb7.isSelected() == true) message = "rb7";			
			else message = "rb8";
			
			/*if(rb10.isSelected() == true){}
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
			else{}*/
			
			switch (message){
			case "rb1":
				pref.fenetre.panelAccueil = new ImagePanel(pref.rb1.getIcon(), pref.fenetre.frame.getWidth(), pref.fenetre.frame.getHeight());
				pref.fenetre.frame.repaint();
				break;
			case "rb2":
				pref.fenetre.panelAccueil = new ImagePanel(pref.rb2.getIcon(), pref.fenetre.frame.getWidth(), pref.fenetre.frame.getHeight());
				pref.fenetre.frame.repaint();
				break;
			case "rb3":
				pref.fenetre.panelAccueil = new ImagePanel(pref.rb3.getIcon(), pref.fenetre.frame.getWidth(), pref.fenetre.frame.getHeight());
				pref.fenetre.frame.repaint();
				break;
			case "rb4":
				pref.fenetre.panelAccueil = new ImagePanel(pref.rb4.getIcon(), pref.fenetre.frame.getWidth(), pref.fenetre.frame.getHeight());
				pref.fenetre.frame.repaint();
				break;
			case "rb5":
				pref.fenetre.panelAccueil = new ImagePanel(pref.rb5.getIcon(), pref.fenetre.frame.getWidth(), pref.fenetre.frame.getHeight());
				pref.fenetre.frame.repaint();
				break;
			case "rb6":
				pref.fenetre.panelAccueil = new ImagePanel(pref.rb6.getIcon(), pref.fenetre.frame.getWidth(), pref.fenetre.frame.getHeight());
				pref.fenetre.frame.repaint();
				break;
			case "rb7":
				pref.fenetre.panelAccueil = new ImagePanel(pref.rb7.getIcon(), pref.fenetre.frame.getWidth(), pref.fenetre.frame.getHeight());
				pref.fenetre.frame.repaint();
				break;
			case "rb8":
				pref.fenetre.panelAccueil = new ImagePanel(pref.rb8.getIcon(), pref.fenetre.frame.getWidth(), pref.fenetre.frame.getHeight());
				pref.fenetre.frame.repaint();
				break;
			case " Humain1 ":
				pref.fenetre.engine.partieCourante.joueurBlanc.name = pref.name1.getText();
				pref.fenetre.idj1 = new JLabel((String)(pref.name1.getText()));
				pref.fenetre.levelj1 = new JLabel(" Bonne Chance ! ");
				pref.fenetre.frame.repaint();
				break;
			case " Humain2 ":
				pref.fenetre.engine.partieCourante.joueurNoir.name = pref.name2.getText();
				pref.fenetre.idj2 = new JLabel((String)(pref.name2.getText()));
				pref.fenetre.levelj2 = new JLabel(" Bonne Chance ! ");
				pref.fenetre.frame.repaint();
				break;
			case " Accepter ":
				pref.fenetre.frame.repaint();
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
