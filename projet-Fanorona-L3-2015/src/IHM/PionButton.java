package IHM;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PionButton extends JButton {

	private static final long	serialVersionUID	= 7393526125348622611L;
	public Fenetre				frame;
	public JButton				button;
	public String				fichierPion;

	public PionButton(String fichier)
	{
		this.button = new JButton(new ImageIcon(fichier));
		this.fichierPion = fichier;
	}

}

class selectionPion implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e)
	{

	}

}
