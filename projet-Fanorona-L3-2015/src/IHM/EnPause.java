package IHM;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class EnPause extends JPanel {
	String chaine;
	Boolean bool;
	
	public EnPause(String s){
		
		JPanel panelPause = new JPanel();
		panelPause.setOpaque(true);
		panelPause.setForeground(new Color(0, 0, 0, 128));
		
		JLabel etat = new JLabel(s);
		panelPause.add(etat);
		
		panelPause.setSize(Fenetre.getPanelAccueil().getSize());
		panelPause.setOpaque(false);
		panelPause.setVisible(false);
	}
}
