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
	String	fichierJoueurBlanc;
	String	fichierJoueurNoir;

	public DessinPion(Fenetre f, int taille, Pion p)
	{
		this.fenetre = f;
		this.tailleJeton = taille;
		this.pionCouleur = p;
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
		// drawable.setPaint(c);
		// drawable.fillOval(tailleJeton/2, tailleJeton/2, tailleJeton,
		// tailleJeton);
		// drawable.setPaint(Color.black);
		// drawable.drawOval(tailleJeton/2, tailleJeton/2, tailleJeton,
		// tailleJeton);

		double tailleX = this.getWidth();
		double tailleY = this.getHeight();
		System.out.println("tailleX :" + tailleX + "tailleY : " + tailleY);

		BufferedImage imgBlanc = null;
		BufferedImage imgNoir = null;

		if (imgBlanc == null || !this.fichierJoueurBlanc.equals(this.fenetre.fichierJoueurBlanc))
		{
			imgBlanc = ImageIO.read(new File(this.fenetre.fichierJoueurBlanc));
			this.fichierJoueurBlanc = fenetre.fichierJoueurBlanc;
		}
		if (imgNoir == null || !this.fichierJoueurNoir.equals(this.fenetre.fichierJoueurNoir))
		{
			imgNoir = ImageIO.read(new File(this.fenetre.fichierJoueurNoir));
			this.fichierJoueurNoir = fenetre.fichierJoueurNoir;
		}

		double imgBlancX = imgBlanc.getWidth();
		double imgBlancY = imgBlanc.getHeight();
		double imgNoirX = imgNoir.getWidth();
		double imgNoirY = imgNoir.getHeight();

		double ratioXBlanc = tailleX / imgBlancX;
		double ratioYBlanc = tailleY / imgBlancY;

		double ratioXNoir = tailleX / imgNoirX;
		double ratioYNoir = tailleY / imgNoirY;

		double ratioBlanc = Math.min(ratioXBlanc, ratioYBlanc);
		double ratioNoir = Math.min(ratioXNoir, ratioYNoir);

		double positionBlancX = tailleX - (imgBlancX * ratioBlanc);
		double positionBlancY = tailleY - (imgBlancY * ratioBlanc);
		double positionNoirX = tailleX - (imgNoirX * ratioNoir);
		double positionNoirY = tailleY - (imgNoirY * ratioNoir);

		if (pionCouleur == Pion.Blanc)
			drawable.drawImage(imgBlanc, (int) positionBlancX / 2, (int) positionBlancY / 2, (int) (imgBlancX * ratioBlanc), (int) (imgBlancY * ratioBlanc), null);
		else
			drawable.drawImage(imgNoir, (int) positionNoirX / 2, (int) positionNoirY / 2, (int) (imgNoirX * ratioNoir), (int) (imgNoirY * ratioNoir), null);
	}
}
