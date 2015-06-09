package IHM;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChoixCouleurPion extends JPanel {

	private static final long	serialVersionUID	= 3572378252075049348L;
	public Fenetre				frame;
	int							f1w					= 700;
	int							f1h					= 225;

	protected JLabel			titre;

	public ChoixCouleurPion(Fenetre f, File repertoire)
	{
		
		super(new BorderLayout());
		this.frame = f;
		titre = new JLabel(" Choissir les couleurs des pions ", JLabel.CENTER);
		titre.setForeground(Color.red);
		titre.setBackground(Color.white);
		titre.setOpaque(true);
		titre.setFont(new Font("SansSerif", Font.BOLD, 24));
		titre.setPreferredSize(new Dimension(100, 65));
		
		JLabel joueur1 = new JLabel(" Choisir le pion du joueur 1 : ");
		JLabel joueur2 = new JLabel(" Choisir le pion du joueur 2 : ");

		joueur1.setBackground(Color.white);
		joueur1.setOpaque(true);
		joueur1.setPreferredSize(new Dimension(80, 55));
		joueur2.setBackground(Color.white);
		joueur2.setOpaque(true);
		joueur2.setPreferredSize(new Dimension(80, 55));
		
		JPanel panelTitre = new JPanel(new BorderLayout());
		panelTitre.add(titre, BorderLayout.CENTER);

		JPanel panelChoix = new JPanel(new BorderLayout());
		panelChoix.setBackground(Color.LIGHT_GRAY);
		JPanel choixJ1 = new JPanel(new BorderLayout());
		JPanel choixJ2 = new JPanel(new BorderLayout());
		ListeSelectionPion panelChoixBlanc = new ListeSelectionPion(frame, frame.engine.getJoueurBlanc(), repertoire);
		ListeSelectionPion panelChoixNoir = new ListeSelectionPion(frame, frame.engine.getJoueurNoir(), repertoire);
		choixJ1.add(joueur1, BorderLayout.NORTH);
		choixJ2.add(joueur2, BorderLayout.NORTH);
		choixJ1.add(panelChoixBlanc.scroll, BorderLayout.SOUTH);
		choixJ2.add(panelChoixNoir.scroll, BorderLayout.SOUTH);
		
		panelChoix.add(choixJ1, BorderLayout.WEST);
		panelChoix.add(choixJ2, BorderLayout.EAST);
		
		add(panelTitre, BorderLayout.NORTH);
		add(panelChoix, BorderLayout.CENTER);
	}
}
