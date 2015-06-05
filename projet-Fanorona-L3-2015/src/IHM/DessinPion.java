package IHM;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class DessinPion extends JComponent {

	Fenetre	fenetre;
	Color	pionCouleur;
	int		tailleJeton;

	public DessinPion(Fenetre f, Color c, int taille)
	{
		fenetre = f;
		pionCouleur = c;
		tailleJeton = taille;
	}

	public void paintComponent(Graphics g)
	{
		Graphics2D drawable = (Graphics2D) g;
		try
		{
			dessinJeton(drawable, pionCouleur);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void dessinJeton(Graphics2D drawable, Color c) throws IOException
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

		BufferedImage imgBlanc = ImageIO.read(new File("./Ressources/pionBlanc.png"));
		BufferedImage imgNoir = ImageIO.read(new File("./Ressources/pionNoir.png"));
		System.out.println(imgBlanc.getWidth());

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

		System.out.println("ratioXBlanc : " + ratioXBlanc + "ratioYBlanc : " + ratioYBlanc);

		if (pionCouleur == Color.white)
			drawable.drawImage(imgBlanc, (int) positionBlancX / 2, (int) positionBlancY / 2, (int) (imgBlancX * ratioBlanc), (int) (imgBlancY * ratioBlanc), null);
		else
			drawable.drawImage(imgNoir, (int) positionNoirX / 2, (int) positionNoirY / 2, (int) (imgNoirX * ratioNoir), (int) (imgNoirY * ratioNoir), null);
	}
}
