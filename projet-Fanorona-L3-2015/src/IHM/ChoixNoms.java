package IHM;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ChoixNoms extends JPanel {
	
	JTextField name1;
	JTextField name2;
	Fenetre fenetre; 
	
	public ChoixNoms(Fenetre f) {

		super(new BorderLayout());	//super constructeur par defaut
		this.setLayout(null);
		fenetre=f;
		int width = 233;
		int height = 57;
				
		JLabel nom = new JLabel(" Choix nom joueurs humains ");
		nom.setForeground(Color.white);
		name1 = new JTextField(fenetre.nameJ1);
		name2 = new JTextField(fenetre.nameJ2);
		
		name1.addActionListener(new ItemAction_name1());
		name2.addActionListener(new ItemAction_name2());
		
			//positions
		nom.setBounds(width, 50, width, height);
		name1.setBounds(width, 150, width, height);
		name2.setBounds(width, 350, width, height);
			//ajouts
		add(nom);
		add(name1);
		add(name2);
	}
	
	class ItemAction_name1 implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			fenetre.frame.repaint();
	    }               

	}
	
	class ItemAction_name2 implements ActionListener{

	    public void actionPerformed(ActionEvent e) {
	      fenetre.setPanelAccueil(new ImagePanel(fenetre.frame, new ImageIcon("src/images/image1.jpg").getImage(), fenetre.frame.WIDTH, fenetre.frame.HEIGHT));
	      fenetre.imageActuelle=new ImageIcon("src/images/image1.jpg");
	      fenetre.frame.repaint();
	    }               

	}
	
 /*   private static void createAndShow() {
		int pw = 699;
		int ph = 549;
		
    		//fenetre
	    JFrame frame = new JFrame("Choix noms joueurs");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	//ajout case texte
	    JPanel tmpPanel = new ChoixNoms();
	    frame.setContentPane(tmpPanel);
	    frame.setSize(new Dimension(pw, ph));
	    frame.setVisible(true);
	}
	*/
//	public static void main(String[] args) {
//	    javax.swing.SwingUtilities.invokeLater(new Runnable() {
//	        public void run() { createAndShow(); }
//	    });
//	}
}
