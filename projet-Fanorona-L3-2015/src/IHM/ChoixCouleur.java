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

	    @SuppressWarnings("static-access")
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
	        	//creation panel central
	        JPanel panelCentre = new JPanel(new GridLayout(3,1));
	        	//mettre le colorchooser en place pour joueur 1
	        fenChoix1 = new JColorChooser(Color.black);
	        fenChoix1.setBorder(BorderFactory.createTitledBorder(" Pions Joueur 1 "));
	        fenChoix1.setPreviewPanel(new JPanel());
	        panelCentre.add(fenChoix1);
	        	//mettre le colorchooser en place pour joueur 2
	        fenChoix2 = new JColorChooser(Color.white);
	        fenChoix2.setBorder(BorderFactory.createTitledBorder(" Pions Joueur 2 "));
	        fenChoix2.setPreviewPanel(new JPanel());
	        panelCentre.add(fenChoix2);
	        	//creation panel boutons
	        JPanel panelBouton = new JPanel(new GridLayout(1,3));
	        	//mettre 3 boutons
		    JButton ok = new JButton(" VALIDER ");
	        	ok.addActionListener(new okListener());
	        JButton annuler = new JButton(" ANNULER ");
	        	annuler.addActionListener(new cancelListener());
		    JButton reset = new JButton(" REMETTRE PAR DEFAUT ");
	        	reset.addActionListener(new resetListener());
	        panelBouton.add(ok);
	        panelBouton.add(annuler);
	        panelBouton.add(reset);
	        	// ajouts
	        add(panelTitre, BorderLayout.NORTH);
	        add(panelCentre, BorderLayout.CENTER);
	        add(panelBouton, BorderLayout.SOUTH);
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

