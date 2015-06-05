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

public class ListeSelectionPion extends JPanel {

	private static final long				serialVersionUID	= -7495913551644993237L;
	private final Map<String, ImageIcon>	imageMap;
	public JScrollPane						scroll;
	public File								repertoire;
	public int								tailleX				= 50;
	public int								tailleY				= 50;

	public ListeSelectionPion(File rep)
	{
		this.repertoire = rep;
		String[] listePionsPossibles = this.repertoire.list();
		System.out.println(this.repertoire);
		imageMap = createImageMap(listePionsPossibles);
		JList<String> list = new JList<String>(listePionsPossibles);
		list.setCellRenderer(new ListRenderer());

		scroll = new JScrollPane(list);
		scroll.setPreferredSize(new Dimension(300, 400));
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

	private Map<String, ImageIcon> createImageMap(String[] list)
	{
		Map<String, ImageIcon> map = new HashMap<>();
		for (String s : list)
		{
			map.put(s, new ImageIcon(new ImageIcon(this.repertoire + "\\" + s).getImage().getScaledInstance(tailleX, tailleY, Image.SCALE_DEFAULT)));
		}
		System.out.println(map);
		return map;
	}
}