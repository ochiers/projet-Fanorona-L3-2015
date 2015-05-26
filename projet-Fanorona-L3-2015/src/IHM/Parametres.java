package IHM;

import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Parametres {
	JRadioButton r1b1;
	JRadioButton r1b2;
	JRadioButton r1b3;
	JRadioButton r2b1;
	JRadioButton r2b2;
	JRadioButton r2b3;
	JRadioButton r3b1;
	JRadioButton r3b2;
	JRadioButton r3b3;
	int saveMode;
	int savelvlPC1;
	int savelvlPC2;
	JButton accepter;
	JButton annuler;
	
	
	public Fenetre fenetre;
	
	public Parametres(Fenetre f){
		fenetre = f;
	}
			
	public void majParam(){
		//fenetre parametre
		fenetre.frame2.setSize(500, 500);
		JPanel panelAccueil2 = new JPanel(new GridLayout(0,1));
				
		JLabel label1= new JLabel("Mode de jeu");
		panelAccueil2.add(label1);
		ButtonGroup bg1 = new ButtonGroup();
		r1b1 = new JRadioButton("Joueur 1 vs Joueur 2");
			r1b1.addActionListener(new EcouteurParametres(this));
		r1b2 = new JRadioButton("Joueur 1 vs Ordi 1");
			r1b2.addActionListener(new EcouteurParametres(this));
		r1b3 = new JRadioButton("Ordi 1 vs Ordi 2");
			r1b3.addActionListener(new EcouteurParametres(this));
			
		if(fenetre.engine.partieCourante.joueurBlanc.aiPlayer || fenetre.engine.partieCourante.joueurNoir.aiPlayer){
			if(fenetre.engine.partieCourante.joueurBlanc.aiPlayer && fenetre.engine.partieCourante.joueurNoir.aiPlayer){
				r1b3.setSelected(true);
				fenetre.mode=3;
				saveMode=3;
			}
			else{
				r1b2.setSelected(true);
				fenetre.mode=2;
				saveMode=2;
			}
		}
		else{
			r1b1.setSelected(true);
			fenetre.mode=1;
			saveMode=1;
		}
		// ajout des boutons radio dans le groupe bg
		bg1.add(r1b1);
		bg1.add(r1b2);
		bg1.add(r1b3);
		panelAccueil2.add(r1b1);
		panelAccueil2.add(r1b2);
		panelAccueil2.add(r1b3);
		
		JLabel label2= new JLabel("Difficulte Ordi 1");
		panelAccueil2.add(label2);
		ButtonGroup bg2 = new ButtonGroup();
		r2b1 = new JRadioButton("Facile");
			r2b1.addActionListener(new EcouteurParametres(this));
		r2b2 = new JRadioButton("Moyen");
			r2b2.addActionListener(new EcouteurParametres(this));
		r2b3 = new JRadioButton("Difficile");
			r2b3.addActionListener(new EcouteurParametres(this));
		
		// ajout des boutons radio dans le groupe bg
		bg2.add(r2b1);
		bg2.add(r2b2);
		bg2.add(r2b3);
		panelAccueil2.add(r2b1);
		panelAccueil2.add(r2b2);
		panelAccueil2.add(r2b3);
		
		JLabel label3= new JLabel("Difficulte Ordi 2");
		panelAccueil2.add(label3);
		ButtonGroup bg3 = new ButtonGroup();
		r3b1 = new JRadioButton("Facile");
			r3b1.addActionListener(new EcouteurParametres(this));
		r3b2 = new JRadioButton("Moyen");
			r3b2.addActionListener(new EcouteurParametres(this));
		r3b3 = new JRadioButton("Difficile");
			r3b3.addActionListener(new EcouteurParametres(this));
		
		if(fenetre.engine.partieCourante.joueurBlanc.aiPlayer || fenetre.engine.partieCourante.joueurNoir.aiPlayer)
			if(fenetre.engine.partieCourante.joueurBlanc.aiPlayer && fenetre.engine.partieCourante.joueurNoir.aiPlayer){
				switch(fenetre.engine.partieCourante.joueurBlanc.getNiveau()){
					case "IA Facile":
						r2b1.setSelected(true);
						fenetre.lvlPC1=1;
						savelvlPC1=1;
						break;
					case "IA Moyen":
						r2b2.setSelected(true);
						fenetre.lvlPC1=2;
						savelvlPC1=2;
						break;
					case "IA Difficile":
						r2b3.setSelected(true);
						fenetre.lvlPC1=3;
						savelvlPC1=3;
						break;
					default:
						System.out.println("Niveau IA blanc inconnu");
						break;
				}
				switch(fenetre.engine.partieCourante.joueurNoir.getNiveau()){
					case "IA Facile":
						r3b1.setSelected(true);
						fenetre.lvlPC2=1;
						savelvlPC2=1;
						break;
					case "IA Moyen":
						r3b2.setSelected(true);
						fenetre.lvlPC2=2;
						savelvlPC2=2;
						break;
					case "IA Difficile":
						r3b3.setSelected(true);
						fenetre.lvlPC2=3;
						savelvlPC2=3;
						break;
					default:
						System.out.println("Niveau IA noir inconnu");
						break;
				}
			}
			else{
				if(fenetre.engine.partieCourante.joueurBlanc.aiPlayer){
					switch(fenetre.engine.partieCourante.joueurBlanc.getNiveau()){
						case "IA Facile":
							r2b1.setSelected(true);
							fenetre.lvlPC1=1;
							savelvlPC1=1;
							break;
						case "IA Moyen":
							r2b2.setSelected(true);
							fenetre.lvlPC1=2;
							savelvlPC1=2;
							break;
						case "IA Difficile":
							r2b3.setSelected(true);
							fenetre.lvlPC1=3;
							savelvlPC1=3;
							break;
						default:
							System.out.println("Niveau IA blanc inconnu");
							break;
					}
				}
				else{
					switch(fenetre.engine.partieCourante.joueurNoir.getNiveau()){
						case "IA Facile":
							r2b1.setSelected(true);
							fenetre.lvlPC1=1;
							savelvlPC1=1;
							break;
						case "IA Moyen":
							r2b2.setSelected(true);
							fenetre.lvlPC1=2;
							savelvlPC1=2;
							break;
						case "IA Difficile":
							r2b3.setSelected(true);
							fenetre.lvlPC1=3;
							savelvlPC1=3;
							break;
						default:
							System.out.println("Niveau IA noir inconnu");
							break;
					}
				}
			}
		else{
			r2b2.setSelected(true);
			r3b2.setSelected(true);
			fenetre.lvlPC1=2;
			fenetre.lvlPC2=2;
			savelvlPC1=2;
			savelvlPC1=2;
		}
		// ajout des boutons radio dans le groupe bg
		bg3.add(r3b1);
		bg3.add(r3b2);
		bg3.add(r3b3);
		panelAccueil2.add(r3b1);
		panelAccueil2.add(r3b2);
		panelAccueil2.add(r3b3);
		
		accepter= new JButton("Accepter");
			accepter.addActionListener(new EcouteurParametres(this));
		annuler= new JButton("Annuler");
			annuler.addActionListener(new EcouteurParametres(this));
		panelAccueil2.add(accepter);
		panelAccueil2.add(annuler);
		
		fenetre.frame2.add(panelAccueil2);
		fenetre.frame2.setResizable(false);
		fenetre.frame2.setVisible(false);
	}
	
}
