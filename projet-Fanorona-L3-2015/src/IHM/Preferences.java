package IHM;

import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;

public class Preferences {

	public Fenetre fenetre;
	
	public Preferences(Fenetre f){
		fenetre = f;
	}
			
	public void majPref(){
		int fw = fenetre.fw;
		int fh = fenetre.fh;
		
		//fenetre preferences
		Fenetre.frame3.setSize(500, 500);
		JPanel panelAccueil3 = new JPanel(new GridLayout(0,1));
		
			// FOND
		JLabel fond = new JLabel(" Choix fond d'ecran ");
		panelAccueil3.add(fond);
		ButtonGroup groupe1 = new ButtonGroup();
		JRadioButtonMenuItem rb1 = new JRadioButtonMenuItem(" image1 ", new ImageIcon("src/images/image1"), false);
			rb1.addActionListener(new EcouteurPreferences(fenetre,rb1.getText()));
		JRadioButtonMenuItem rb2 = new JRadioButtonMenuItem(" image2 ", new ImageIcon("src/images/image2"), false);
			rb2.addActionListener(new EcouteurPreferences(fenetre,rb2.getText()));
		JRadioButtonMenuItem rb3 = new JRadioButtonMenuItem(" image3 ", new ImageIcon("src/images/image3"), false);
			rb3.addActionListener(new EcouteurPreferences(fenetre,rb3.getText()));
		JRadioButtonMenuItem rb4 = new JRadioButtonMenuItem(" image4 ", new ImageIcon("src/images/image4"), false);
			rb4.addActionListener(new EcouteurPreferences(fenetre,rb4.getText()));
		JRadioButtonMenuItem rb5 = new JRadioButtonMenuItem(" image5 ", new ImageIcon("src/images/image5"), false);
			rb5.addActionListener(new EcouteurPreferences(fenetre,rb5.getText()));
		JRadioButtonMenuItem rb6 = new JRadioButtonMenuItem(" image6 ", new ImageIcon("src/images/image6"), false);
			rb6.addActionListener(new EcouteurPreferences(fenetre,rb6.getText()));
		JRadioButtonMenuItem rb7 = new JRadioButtonMenuItem(" image7 ", new ImageIcon("src/images/image7"), false);
			rb7.addActionListener(new EcouteurPreferences(fenetre,rb7.getText()));
		JRadioButtonMenuItem rb8 = new JRadioButtonMenuItem(" fond par defaut ", new ImageIcon("src/images/imageDefault"), true);
			rb8.addActionListener(new EcouteurPreferences(fenetre,rb8.getText()));
		if(rb1.isSelected() == true) fenetre.panelAccueil = new ImagePanel(rb1.getIcon(), fw, fh);
		if(rb2.isSelected() == true) fenetre.panelAccueil = new ImagePanel(rb2.getIcon(), fw, fh);
		if(rb3.isSelected() == true) fenetre.panelAccueil = new ImagePanel(rb3.getIcon(), fw, fh);
		if(rb4.isSelected() == true) fenetre.panelAccueil = new ImagePanel(rb4.getIcon(), fw, fh);
		if(rb5.isSelected() == true) fenetre.panelAccueil = new ImagePanel(rb5.getIcon(), fw, fh);
		if(rb6.isSelected() == true) fenetre.panelAccueil = new ImagePanel(rb6.getIcon(), fw, fh);
		if(rb7.isSelected() == true) fenetre.panelAccueil = new ImagePanel(rb7.getIcon(), fw, fh);
		else fenetre.panelAccueil = new ImagePanel(rb8.getIcon(), fw, fh);
		// ajout des boutons radio dans le groupe bg
		groupe1.add(rb1);
		groupe1.add(rb2);
		groupe1.add(rb3);
		groupe1.add(rb4);
		groupe1.add(rb5);
		groupe1.add(rb6);
		groupe1.add(rb7);
		groupe1.add(rb8);
		//ajout de bg au panel
		panelAccueil3.add(rb1);
		panelAccueil3.add(rb2);
		panelAccueil3.add(rb3);
		panelAccueil3.add(rb4);
		panelAccueil3.add(rb5);
		panelAccueil3.add(rb6);
		panelAccueil3.add(rb7);
		panelAccueil3.add(rb8);
		
			// NOMS
		JLabel nom = new JLabel(" Choix nom joueurs humains ");
		panelAccueil3.add(nom);
		String nameJ1 = fenetre.engine.partieCourante.joueurBlanc.getNiveau();
		JTextField name1 = new JTextField(" Humain 1 ");
		if (nameJ1.equals("Humain")){
					// ajout boite de dialogue
			fenetre.engine.partieCourante.joueurBlanc.name = name1.getText();
			fenetre.idj1 = new JLabel((String)(name1.getText()));
			fenetre.levelj1 = new JLabel(" Bonne Chance ! ");
		}
		String nameJ2 = fenetre.engine.partieCourante.joueurNoir.getNiveau();
		JTextField name2 = new JTextField(" Humain 2 ");
		if (nameJ2.equals("Humain")){
			fenetre.engine.partieCourante.joueurNoir.name = name2.getText();
			fenetre.idj2 = new JLabel((String)(name2.getText()));
			fenetre.levelj2 = new JLabel(" Bonne Chance ! ");
		}
		else{
			// verifier parametres par defaut !!!!!!!!!!!!!!! 
			fenetre.idj1 = new JLabel(" Humain ");
			fenetre.levelj1 = new JLabel(" Bonne Chance ! ");
			fenetre.idj2 = new JLabel(" Humain ");
			fenetre.levelj2 = new JLabel(" Bonne Chance ! ");
		}
		
		// ajout des boites de dialogue
		panelAccueil3.add(name1);
		panelAccueil3.add(name2);
		
			// COULEUR PIONS
		JLabel pion = new JLabel(" Choix couleur pions ");
		panelAccueil3.add(pion);
		ButtonGroup groupe2 = new ButtonGroup();
		JRadioButton rb10 = new JRadioButton(" Noir ");
		JRadioButton rb20 = new JRadioButton(" Blanc ");
		JRadioButton rb30 = new JRadioButton(" Bleu ");
		JRadioButton rb40 = new JRadioButton(" Rouge ");
		JRadioButton rb50 = new JRadioButton(" Vert ");
		JRadioButton rb60 = new JRadioButton(" Jaune ");
		JRadioButton rb70 = new JRadioButton(" Multicolor ");
		if(rb10.isSelected() == true){
			
		}
		if(rb20.isSelected() == true){
			
		}
		if(rb30.isSelected() == true){
			
		}
		if(rb40.isSelected() == true){
			
		}
		if(rb50.isSelected() == true){
			
		}
		if(rb60.isSelected() == true){
			
		}
		if(rb70.isSelected() == true){
			
		}
		else{
			
		}
		
		// ajout des boutons radio dans le groupe bg
		groupe2.add(rb10);
		groupe2.add(rb20);
		groupe2.add(rb30);
		groupe2.add(rb40);
		groupe2.add(rb50);
		groupe2.add(rb60);
		groupe2.add(rb70);
		//ajout de bg au panel
		panelAccueil3.add(rb10);
		panelAccueil3.add(rb20);
		panelAccueil3.add(rb30);
		panelAccueil3.add(rb40);
		panelAccueil3.add(rb50);
		panelAccueil3.add(rb60);
		panelAccueil3.add(rb70);
		
		Fenetre.frame3.add(panelAccueil3);
		Fenetre.frame3.setResizable(false);
		Fenetre.frame3.setVisible(false);
		
		Fenetre.frame.repaint();
	}
	
}
