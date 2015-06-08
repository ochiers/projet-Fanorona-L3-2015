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

		JPanel panelTitre = new JPanel(new BorderLayout());
		panelTitre.add(titre, BorderLayout.CENTER);

		JPanel panelChoix = new JPanel(new BorderLayout());
		ListeSelectionPion panelChoixBlanc = new ListeSelectionPion(frame, frame.engine.getJoueurBlanc(), repertoire);
		ListeSelectionPion panelChoixNoir = new ListeSelectionPion(frame, frame.engine.getJoueurNoir(), repertoire);
		panelChoix.add(panelChoixBlanc.scroll, BorderLayout.WEST);
		panelChoix.add(panelChoixNoir.scroll, BorderLayout.EAST);

		add(panelTitre, BorderLayout.NORTH);
		add(panelChoix, BorderLayout.CENTER);
	}
}
