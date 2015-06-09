package IHM;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import engine.Player;

public class ListeSelectionPion extends JPanel {

	private static final long				serialVersionUID	= -7495913551644993237L;
	private final Map<String, ImageIcon>	imageMap;
	public JScrollPane						scroll;
	public File								repertoire;
	public int								tailleX				= 50;
	public int								tailleY				= 50;
	public Fenetre							frame;
	public Player							jCourant;
	public JList<String>					list;

	public ListeSelectionPion(Fenetre frame, Player p, File rep)
	{
		this.frame = frame;
		this.jCourant = p;
		this.repertoire = rep;
		String[] listePionsPossibles = this.repertoire.list();
		imageMap = createImageMap(listePionsPossibles);
		list = new JList<String>(listePionsPossibles);
		list.setCellRenderer(new ListRenderer());
		list.addListSelectionListener(new listListener(this));
		scroll = new JScrollPane(list);
		scroll.setPreferredSize(new Dimension(300, 400));
	}

	private Map<String, ImageIcon> createImageMap(String[] list)
	{
		Map<String, ImageIcon> map = new HashMap<>();
		for (String s : list)
			map.put(s, new ImageIcon(new ImageIcon(this.repertoire + File.separator + s).getImage().getScaledInstance(tailleX, tailleY, Image.SCALE_DEFAULT)));
		return map;
	}

	public class ListRenderer extends DefaultListCellRenderer {

		private static final long	serialVersionUID	= -2663811554847903500L;

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
		{
			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			label.setIcon(imageMap.get((String) value));
			return label;
		}
	}

	public class listListener implements ListSelectionListener {

		public ListeSelectionPion	listPion;
		public String				ancienJ1	= "./Ressources/Pions/pionBlanc.png".replaceAll("/", File.separator);
		public String				ancienJ2	= "./Ressources/Pions/pionNoir.png".replaceAll("/", File.separator);

		public listListener(ListeSelectionPion l)
		{
			this.listPion = l;
		}

		@Override
		public void valueChanged(ListSelectionEvent e)
		{
			String fichierSelectionne = listPion.repertoire + "/" + this.listPion.list.getSelectedValue();
			fichierSelectionne.replace("/", File.separator);
			if (fichierSelectionne != null && jCourant == frame.engine.getJoueurBlanc() && !fichierSelectionne.equals(frame.fichierJoueurNoir))
			{
				frame.fichierJoueurBlanc = fichierSelectionne;
				ancienJ1 = fichierSelectionne;
			} else if (fichierSelectionne != null && jCourant == frame.engine.getJoueurNoir() && !fichierSelectionne.equals(frame.fichierJoueurBlanc))
			{
				frame.fichierJoueurNoir = fichierSelectionne;
				ancienJ2 = fichierSelectionne;
			} else if (fichierSelectionne != null && jCourant == frame.engine.getJoueurBlanc() && fichierSelectionne.equals(frame.fichierJoueurNoir))
			{
				this.listPion.list.clearSelection();
				System.out.println(ancienJ1);
				frame.fichierJoueurBlanc = ancienJ1;
			} else if (fichierSelectionne != null && jCourant == frame.engine.getJoueurNoir() && fichierSelectionne.equals(frame.fichierJoueurBlanc))
			{
				System.out.println(ancienJ2);
				this.listPion.list.clearSelection();
				frame.fichierJoueurNoir = ancienJ2;
			}
		}
	}
}