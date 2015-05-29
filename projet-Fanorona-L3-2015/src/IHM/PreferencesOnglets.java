package IHM;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class PreferencesOnglets extends JPanel {
	int width = 699;
	int height = 549;
    
    int pBw = 10;
    int pBh = 30;
	
	public PreferencesOnglets() {
		super(new BorderLayout());
		
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(width, height));
        ImageIcon icon = new ImageIcon("src/images/iconFano.jpg");
        
        ChoixFond panel1 = new ChoixFond();
        tabbedPane.addTab(" Choix Fond Ecran ", icon, panel1);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        
        ChoixNoms panel2 = new ChoixNoms();
    		// ajout fond
	    ImagePanel imagefond = new ImagePanel(new ImageIcon("src/images/players.jpg").getImage(), width, height);
	    panel2.add(imagefond);
        tabbedPane.addTab(" Choix Noms Joueurs ", icon, panel2);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        
        ChoixCouleur panel3 = new ChoixCouleur();
        tabbedPane.addTab(" Choix Couleurs Pions ", icon, panel3);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
        
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
	    panelBouton.setPreferredSize(new Dimension(pBw, pBh));
        
        //Ajout
        add(tabbedPane, BorderLayout.PAGE_START);
        add(panelBouton, BorderLayout.PAGE_END);
        
        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }
    
    static void createAndShowGUI() {
        JFrame frame = new JFrame(" -- Preferences -- ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Ajouts
        frame.add(new PreferencesOnglets(), BorderLayout.CENTER);
        
        frame.pack();
        frame.setVisible(true);
    }
    
   /* public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run(){ createAndShowGUI(); }
        });
    }*/
}

