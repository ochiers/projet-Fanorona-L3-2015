package IHM;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

import engine.Pion;

@SuppressWarnings("serial")
public class DessinPion extends JComponent {

	Fenetre	fenetre;
	Pion	pionCouleur;
	int		tailleJeton;
	String	fichierJoueur;

	public DessinPion(Fenetre f, int taille, Pion p)
	{
		this.fenetre = f;
		this.tailleJeton = taille;
		this.pionCouleur = p;
		this.fichierJoueur = (this.pionCouleur == Pion.Blanc) ? this.fenetre.fichierJoueurBlanc : this.fenetre.fichierJoueurNoir;
	}

	public void paintComponent(Graphics g)
	{
		Graphics2D drawable = (Graphics2D) g;
		try
		{
			dessinJeton(drawable);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void dessinJeton(Graphics2D drawable) throws IOException
	{

		double tailleX = this.getWidth();
		double tailleY = this.getHeight();

		BufferedImage img = null;

		if (this.pionCouleur == Pion.Blanc && (!this.fichierJoueur.equals(this.fenetre.fichierJoueurBlanc) || img == null))
		{
			this.fichierJoueur = this.fenetre.fichierJoueurBlanc;
			System.out.println(this.fichierJoueur);
			img = ImageIO.read(new File(this.fichierJoueur));
		} else if (this.pionCouleur == Pion.Noir && (!this.fichierJoueur.equals(this.fenetre.fichierJoueurNoir) || img == null))
		{
			this.fichierJoueur = this.fenetre.fichierJoueurNoir;
			System.out.println(this.fichierJoueur);
			img = ImageIO.read(new File(this.fichierJoueur));
		}

		double imgX = img.getWidth();
		double imgY = img.getHeight();

		double ratioX = tailleX / imgX;
		double ratioY = tailleY / imgY;

		double ratio = Math.min(ratioX, ratioY);

		double positionX = tailleX - (imgX * ratio);
		double positionY = tailleY - (imgY * ratio);

		drawable.drawImage(img, (int) positionX / 2, (int) positionY / 2, (int) (imgX * ratio), (int) (imgY * ratio), null);
	}
}
