package IHM;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class ChoixCouleur extends JPanel {

		ActionListener okListener, cancelListener, resetListener;
	
	    protected JColorChooser fenChoix1;
	    protected JColorChooser fenChoix2;
	    protected JLabel titre;
	    
	    int f1w = 700;
	    int f1h = 225;

		public ChoixCouleur() {
	        super(new BorderLayout());	//super constructeur par defaut

	        	//mettre titre dans fenetre
	        titre = new JLabel(" Choissir les couleurs des pions ", JLabel.CENTER);
	        titre.setForeground(Color.red);
	        titre.setBackground(Color.white);
	        titre.setOpaque(true);
	        titre.setFont(new Font("SansSerif", Font.BOLD, 24));
	        titre.setPreferredSize(new Dimension(100, 65));
	        	//creation panel conteneur
	        JPanel panelTitre = new JPanel(new BorderLayout());	        
	        panelTitre.add(titre, BorderLayout.CENTER);
	        	//mettre le colorchooser en place pour joueur 1
	        fenChoix1 = new JColorChooser(Color.black);
	        fenChoix1.setBorder(BorderFactory.createTitledBorder(" Pions Joueur 1 "));
	        fenChoix1.setPreferredSize(new Dimension(f1w, f1h));
	        fenChoix1.setPreviewPanel(new JPanel());
	        	//mettre le colorchooser en place pour joueur 2
	        fenChoix2 = new JColorChooser(Color.white);
	        fenChoix2.setBorder(BorderFactory.createTitledBorder(" Pions Joueur 2 "));
	        fenChoix2.setPreferredSize(new Dimension(f1w, f1h));
	        fenChoix2.setPreviewPanel(new JPanel());
	        	// ajouts
	        add(panelTitre, BorderLayout.NORTH);
	        add(fenChoix1, BorderLayout.CENTER);
	        add(fenChoix2, BorderLayout.SOUTH);
	    }

	    private static void createAndShow() {
	        	//fenetre
	        JFrame frame = new JFrame("Choix couleurs pions");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        	//ajout colorchoosen
	        JComponent panelChoix = new ChoixCouleur();
	        panelChoix.setOpaque(true); 
	        frame.setContentPane(panelChoix);

	        frame.pack();
	        frame.setVisible(true);
	    }

	    /*public static void main(String[] args) {
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() { createAndShow(); }
	        });
	    }*/
}

