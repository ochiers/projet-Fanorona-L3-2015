package IHM;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import engine.*;

@SuppressWarnings("serial")
public class AireDeDessin extends JComponent {
	public Fenetre			fenetre;
	public ImageIcon		plateau;
	public int				CoordonneesPlateau[];
	public int				tailleJeton;
	public boolean			pionCliquer		= false;
	public Coordonnee		pCourant;
	public Coordonnee		pfinal;
	public Color			halo;
	public Color			haloChoix;
	public Color			comboColor;
	public Color			coupPossible;
	public Color			colorSugg;
	public boolean			doitChoisir		= false;
	public boolean			finPartie		= false;
	public ArrayList<Case>	l1;
	public ArrayList<Case>	l2;
	public ArrayList<Case>	pionPossible;
	public ArrayList<Case>	combo;
	public Case				pionCombo;
	public double			segment;
	public float			etir;
	public double			tailleHalo;
	public int				originePlateauX	= 0;
	public int				originePlateauY	= 0;
	public int				plateauW, plateauH;

	public BufferedImage	imgBlanc		= null, imgNoir = null;
	public String			fichierJoueurBlanc, fichierJoueurNoir;

	public boolean			surbrillance	= false;
	public Coordonnee		pSurbrillance;
	public Image			pauseOverlay;
	public Image			attenteJoueurOverlay;
	public boolean			attenteReseau;
	
	public Coup				suggestion;
	public boolean			enSuggestion;

 	public AireDeDessin(Fenetre f)
	{
		this.fenetre = f;
		this.fichierJoueurBlanc = fenetre.fichierJoueurBlanc;
		this.fichierJoueurNoir = fenetre.fichierJoueurNoir;
		this.halo = Color.green;
		this.haloChoix = Color.yellow;
		this.comboColor = Color.orange;
		this.coupPossible = Color.cyan;
		this.colorSugg = Color.red;
		this.setPreferredSize(new Dimension((int) (10 * segment), (int) (6 * segment)));
		this.pCourant = new Coordonnee(-1, -1);
		this.plateau = new ImageIcon("Ressources/images/Fano9x5.jpg");
		this.CoordonneesPlateau = new int[4];
		this.CoordonneesPlateau[0] = 80;
		this.CoordonneesPlateau[1] = 72;
		this.CoordonneesPlateau[2] = 484;
		this.CoordonneesPlateau[3] = 272;
		this.segment = 0;
		this.tailleJeton = 1;
		this.etir = 1;
		this.tailleHalo = 1.5;
//		pfinal=new Coordonnee(-1,-1);
		try
		{
			this.pauseOverlay = ImageIO.read(new File("./Ressources/overlayPause.png".replace("/", File.separator)));
			this.attenteJoueurOverlay = ImageIO.read(new File("./Ressources/overlayAttenteJoueur.png".replace("/", File.separator)));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g)
	{
		Graphics2D drawable = (Graphics2D) g;
		fenetre.fw = fenetre.frame.getWidth();
		fenetre.fh = fenetre.frame.getHeight();
		int width = this.getSize().width;
		int height = this.getSize().height;
		plateauW = plateau.getImage().getWidth(null);
		plateauH = plateau.getImage().getHeight(null);
		float etirW = width / (float) plateauW;
		float etirH = height / (float) plateauH;
		etir = etirW < etirH ? etirW : etirH;
		segment = etir * (CoordonneesPlateau[2] - CoordonneesPlateau[0]) / 8.0;
		tailleJeton = (int) (segment / 1.75);

		Fenetre.setPanelAccueil(new ImagePanel(fenetre.frame, fenetre.imageActuelle.getImage(), ImageObserver.WIDTH, ImageObserver.HEIGHT));
		drawable.drawImage(plateau.getImage(), originePlateauX, originePlateauY, (int) (etir * plateauW), (int) (etir * plateauH), null);

		majScore();
		majAQuiLeTour();
		majBouton();
		majNomJoueurs();

		if (fenetre.engine.getJoueurCourant().aiPlayer || Tools.getTypeOfPlayer(fenetre.engine.getJoueurCourant()) == PlayerType.Reseau || finPartie){
			
			if (combo != null && combo.size() > 0)
				cheminCombo(drawable);
			dessinGrilleJeton(drawable, originePlateauX, originePlateauY, (int) (etir * plateauW), (int) (etir * plateauH), etir);
		}else
		{
			if (!pionCliquer && doitChoisir)
				choixManger(drawable);// halo jaune
			if(enSuggestion)
				haloSuggestion(drawable);
			if (!fenetre.engine.enCombo() ){
				if(!pionCliquer && !doitChoisir){
					pionJouable(drawable);// halo vert
					if(enSuggestion)
						haloSuggestion(drawable);
				}
			}else
			{
				if (!pionCliquer)
					pionJouableCombo(drawable);
				if (combo != null && combo.size() > 0)
					cheminCombo(drawable);
			}
			dessinGrilleJeton(drawable, originePlateauX, originePlateauY, (int) (etir * plateauW), (int) (etir * plateauH), etir);
			if (pionCliquer)
			{
				halo(drawable, pCourant, Color.cyan);
				emplacementPossible(drawable);
				if (surbrillance)
					pionSurbrillance(drawable, pSurbrillance, Color.white);
			}
		}

		centrerPlateau(width, height, (int) (etir * plateauW), (int) (etir * plateauH));

		if (this.attenteReseau)
		{
			g.drawImage(attenteJoueurOverlay, 0, 0, width, height, null);
		} else if (fenetre.engine.isGamePaused())
			g.drawImage(pauseOverlay, 0, 0, width, height, null);

	}

	public void halo(Graphics2D drawable, Coordonnee p, Color c)
	{
		int red = c.getRed();
		int green = c.getGreen();
		int blue = c.getBlue();
		int alpha = c.getAlpha();
		int newTaille = (int) (tailleJeton * tailleHalo);
		double diff = alpha / (newTaille - tailleJeton);
		drawable.setPaint(new Color(red, green, blue, alpha));
		for (int i = tailleJeton + 2; i < newTaille; i++)
		{
			alpha = (int) (255 - (i - tailleJeton) * diff);
			drawable.setPaint(new Color(red, green, blue, alpha));
			drawable.drawOval((int) (CoordonneesPlateau[0] * etir + p.colonne * segment - i / 2 + originePlateauX), (int) (CoordonneesPlateau[1] * etir + p.ligne * segment - i / 2 + originePlateauY), (int) i, (int) i);
		}
		drawable.setPaint(Color.black);
	}

	public void pionSurbrillance(Graphics2D drawable, Coordonnee p, Color c)
	{
		int red = c.getRed();
		int green = c.getGreen();
		int blue = c.getBlue();
		int alpha = 128;
		drawable.setPaint(new Color(red, green, blue, alpha));
		drawable.fillOval((int) (CoordonneesPlateau[0] * etir + p.colonne * segment - tailleJeton / 2 + originePlateauX), (int) (CoordonneesPlateau[1] * etir + p.ligne * segment - tailleJeton / 2 + originePlateauY), (int) tailleJeton, (int) tailleJeton);
		drawable.setPaint(Color.black);
	}

	public void haloSuggestion(Graphics2D drawable){
		System.out.println("haloSuggestion");
		halo(drawable,suggestion.depart,colorSugg);
		halo(drawable,suggestion.arrivee,colorSugg);
		
	}
	
	private void centrerPlateau(int width, int height, int pw, int ph)
	{
		if (width >= height)
		{
			originePlateauX = (width / 2) - (pw / 2);
			originePlateauY = (height / 2) - (ph / 2);
		} else
		{
			originePlateauX = 0;
			originePlateauY = (height / 2) - (ph / 2);
		}
	}

	public void majScore()
	{
		fenetre.scoreInt1.setText("" + fenetre.engine.getNombrePionsBlancs());
		fenetre.scoreInt2.setText("" + fenetre.engine.getNombrePionsNoirs());
	}

	public void majAQuiLeTour()
	{
		if (fenetre.engine.getJoueurCourant().name.equals(fenetre.engine.getJoueurBlanc().name))
		{
			fenetre.tour1.setVisible(true);
			fenetre.tour2.setVisible(false);
		} else
		{
			fenetre.tour2.setVisible(true);
			fenetre.tour1.setVisible(false);
		}
	}

	public void majBouton()
	{
		if (fenetre.engine.peutAnnuler())
			fenetre.annuler.setEnabled(true);
		else
			if (fenetre.annuler != null)
				fenetre.annuler.setEnabled(false);
		if (fenetre.engine.peutRefaire())
			fenetre.refaire.setEnabled(true);
		else
			if (fenetre.refaire != null)
				fenetre.refaire.setEnabled(false);
		if (!fenetre.engine.getCurrentGame().joueurCourant.aiPlayer)
		{
			if (fenetre.engine.getCurrentGame().enCombo)
				fenetre.finTour.setEnabled(true);
			else
				if (fenetre.finTour != null)
					fenetre.finTour.setEnabled(false);
		} else
			if (fenetre.finTour != null)
				fenetre.finTour.setEnabled(false);
		if (fenetre.engine.getCurrentGame().isPaused())
			fenetre.stopper.setText(" Reprendre ");
		else
			if (fenetre.stopper != null)
				fenetre.stopper.setText(" Pause ");
		if(pionCliquer || fenetre.engine.getJoueurCourant().aiPlayer || Tools.getTypeOfPlayer(fenetre.engine.getJoueurCourant()) == PlayerType.Reseau || finPartie)
			if (fenetre.suggestion != null)
				fenetre.suggestion.setEnabled(false);
		else
			if (fenetre.suggestion != null)
				fenetre.suggestion.setEnabled(true);
	}

	public void majNomJoueurs()
	{
		fenetre.engine.getJoueurBlanc().name = fenetre.nameJ1;
		fenetre.engine.getJoueurNoir().name = fenetre.nameJ2;
		String level = fenetre.engine.getJoueurBlanc().getNiveau();
		if (level.equals("Humain"))
		{
			fenetre.idj1.setText(fenetre.engine.getJoueurBlanc().name);
			fenetre.levelj1.setText(" Bonne Chance ! ");
			fenetre.levelj1.setVisible(false);
		} else if (level.equals("IA Facile"))
		{
			fenetre.idj1.setText(" Ordinateur ");
			fenetre.levelj1.setText(" Facile ");
			fenetre.levelj1.setVisible(true);
		} else if (level.equals("IA Moyenne"))
		{
			fenetre.idj1.setText(" Ordinateur ");
			fenetre.levelj1.setText(" Moyen ");
			fenetre.levelj1.setVisible(true);
		} else if (level.equals("IA Difficile"))
		{
			fenetre.idj1.setText(" Ordinateur ");
			fenetre.levelj1.setText(" Difficile ");
			fenetre.levelj1.setVisible(true);
		}

		level = fenetre.engine.getJoueurNoir().getNiveau();
		if (level.equals("Humain"))
		{
			fenetre.idj2.setText(fenetre.engine.getJoueurNoir().name);
			fenetre.levelj2.setText(" Bonne Chance ! ");
			fenetre.levelj2.setVisible(false);
		} else if (level.equals("IA Facile"))
		{
			fenetre.idj2.setText(" Ordinateur ");
			fenetre.levelj2.setText(" Facile ");
			fenetre.levelj2.setVisible(true);
		} else if (level.equals("IA Moyenne"))
		{
			fenetre.idj2.setText(" Ordinateur ");
			fenetre.levelj2.setText(" Moyen ");
			fenetre.levelj2.setVisible(true);
		} else if (level.equals("IA Difficile"))
		{
			fenetre.idj2.setText(" Ordinateur ");
			fenetre.levelj2.setText(" Difficile ");
			fenetre.levelj2.setVisible(true);
		}
	}

	public ArrayList<Case> coupReel(ArrayList<Case> c1, ArrayList<Case> c2)
	{
		ArrayList<Case> c3 = new ArrayList<Case>();
		boolean b = false;
		int i = 0;
		int j = 0;
		while (i < c1.size())
		{
			b = false;
			j = 0;
			while (j < c2.size() && !b)
			{
				if (c1.get(i) == c2.get(j))
					b = true;
				j++;
			}
			if (!b)
				c3.add(c1.get(i));
			i++;
		}

		return c3;
	}

	public void emplacementPossible(Graphics2D drawable)
	{
		if(pfinal.ligne >= 0 && pfinal.colonne >=0){
			ArrayList<Case> emplacementPossible1 = fenetre.engine.getCurrentGame().coupsPossiblesPourUnPion(fenetre.engine.getCurrentGame().matricePlateau[pfinal.ligne][pfinal.colonne]);
			ArrayList<Case> emplacementPossible = fenetre.engine.getCurrentGame().coupsPourPriseParUnPion(emplacementPossible1, fenetre.engine.getCurrentGame().matricePlateau[pfinal.ligne][pfinal.colonne]);
			if (combo != null)
			{
				emplacementPossible = coupReel(emplacementPossible, combo);
			}
			if (emplacementPossible != null)
			{
				if (emplacementPossible.size() != 0)
				{
					for (int i = 0; i < emplacementPossible.size(); i++)
					{
						halo(drawable, emplacementPossible.get(i).position, coupPossible);
					}
				} else
				{
					for (int i = 0; i < emplacementPossible1.size(); i++)
					{
						halo(drawable, emplacementPossible1.get(i).position, coupPossible);
					}
				}
			}
		}
	}

	public void pionJouable(Graphics2D drawable)
	{
		if (pionPossible != null)
			for (int i = 0; i < pionPossible.size(); i++)
				halo(drawable, pionPossible.get(i).position, halo);
	}

	public void pionJouableCombo(Graphics2D drawable)
	{
		if (pionCombo != null)
			halo(drawable, pionCombo.position, halo);
	}

	public void cheminCombo(Graphics2D drawable)
	{
		drawable.setPaint(comboColor);
		Point pointCour = null, pointPrec = null;
		Stroke s = drawable.getStroke();
		Composite c = drawable.getComposite();
		drawable.setStroke(new BasicStroke(5));

		for (int i = 0; i < combo.size(); i++)
		{
			System.out.println("-----"+combo.get(i).position.ligne+" "+combo.get(i).position.colonne);
			pointCour = new Point((int) (CoordonneesPlateau[0] * etir + combo.get(i).position.colonne * segment - tailleJeton / 4 + originePlateauX), (int) (CoordonneesPlateau[1] * etir + combo.get(i).position.ligne * segment - tailleJeton / 4 + originePlateauY));
			drawable.fillOval(pointCour.x, pointCour.y, tailleJeton / 2, tailleJeton / 2);
			if (i >= 1)
			{
				drawable.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
				drawable.drawLine(pointPrec.x + (tailleJeton / 4), pointPrec.y + (tailleJeton / 4), pointCour.x + (tailleJeton / 4), pointCour.y + (tailleJeton / 4));
				drawable.setComposite(c);
			}
			pointPrec = pointCour;
		}
//		pointCour = new Point((int) (CoordonneesPlateau[0] * etir + pCourant.colonne * segment - tailleJeton / 4 + originePlateauX), (int) (CoordonneesPlateau[1] * etir + pCourant.ligne * segment - tailleJeton / 4 + originePlateauY));
		drawable.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
//		drawable.drawLine(pointPrec.x + (tailleJeton / 4), pointPrec.y + (tailleJeton / 4), pointCour.x + (tailleJeton / 4), pointCour.y + (tailleJeton / 4));
		
		pointPrec = new Point((int) (CoordonneesPlateau[0] * etir + pionCombo.position.colonne * segment - tailleJeton / 4 + originePlateauX), (int) (CoordonneesPlateau[1] * etir + pionCombo.position.ligne * segment - tailleJeton / 4 + originePlateauY));
		drawable.drawLine(pointCour.x + (tailleJeton / 4), pointCour.y + (tailleJeton / 4), pointPrec.x + (tailleJeton / 4), pointPrec.y + (tailleJeton / 4));
		
		drawable.setStroke(s);
		drawable.setComposite(c);
		drawable.setPaint(Color.black);
	}

	public void dessinGrilleJeton(Graphics2D drawable, int originePlateauX, int originePlateauY, int plateauW, int plateauH, float etir)
	{
		Case[][] matrice = fenetre.engine.getPlateau();
		for (int i = 0; i < matrice.length; i++)
			for (int j = 0; j < matrice[i].length; j++)
			{
				int startX = (int) (CoordonneesPlateau[0] * etir + j * segment) + originePlateauX;
				int startY = (int) (CoordonneesPlateau[1] * etir + i * segment) + originePlateauY;
				dessinJeton(drawable, matrice[i][j].pion, startX, startY);
			}
	}

	public void dessinJeton(Graphics2D drawable, Pion pion, int x, int y)
	{
		if (pion != null)
		{
			if (!this.fichierJoueurBlanc.equals(this.fenetre.fichierJoueurBlanc) || imgBlanc == null)
			{
				this.fichierJoueurBlanc = this.fenetre.fichierJoueurBlanc;
				try
				{
					imgBlanc = ImageIO.read(new File(this.fichierJoueurBlanc));
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			} else if (!this.fichierJoueurNoir.equals(this.fenetre.fichierJoueurNoir) || imgNoir == null)
			{
				this.fichierJoueurNoir = this.fenetre.fichierJoueurNoir;
				try
				{
					imgNoir = ImageIO.read(new File(this.fichierJoueurNoir));
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}

			if (pion == Pion.Blanc)
				drawable.drawImage(imgBlanc, x - tailleJeton / 2, y - tailleJeton / 2, tailleJeton, tailleJeton, null);
			else if (pion == Pion.Noir)
				drawable.drawImage(imgNoir, x - tailleJeton / 2, y - tailleJeton / 2, tailleJeton, tailleJeton, null);
		}

	}

	public void choixManger(Graphics2D drawable)
	{
		for (int i = 0; i < l1.size(); i++)
		{
			// haloChoixManger(drawable,l1.get(i).position);
			halo(drawable, l1.get(i).position, haloChoix);
		}
		for (int i = 0; i < l2.size(); i++)
		{
			// haloChoixManger(drawable,l2.get(i).position);
			halo(drawable, l2.get(i).position, haloChoix);
		}
	}

	public boolean estUnChoix(Coordonnee c)
	{
		boolean choix = false;
		int i = 0;
		while (i < l1.size() && !choix)
		{
			if (l1.get(i).position.ligne == c.ligne && l1.get(i).position.colonne == c.colonne)
				choix = true;
			i++;
		}
		i = 0;
		while (i < l2.size() && !choix)
		{
			if (l2.get(i).position.ligne == c.ligne && l2.get(i).position.colonne == c.colonne)
				choix = true;
			i++;
		}
		return choix;
	}

	public boolean estJouable(Coordonnee c)
	{
		boolean choix = false;
		if (fenetre.engine.getCurrentGame().enCombo)
		{
			return pionCombo.position.ligne == c.ligne && pionCombo.position.colonne == c.colonne;
		} else
		{
			int i = 0;
			while (i < pionPossible.size() && !choix)
			{
				if (pionPossible.get(i).position.ligne == c.ligne && pionPossible.get(i).position.colonne == c.colonne)
					choix = true;
				i++;
			}
		}
		return choix;
	}

	public void drawArrow(Graphics2D g, int x, int y, int largeur, int hauteur)
	{
		g.setPaint(Color.red);
		largeur = largeur / 3;
		hauteur = hauteur / 3;

		g.fillRect(x - largeur / 2, y, largeur, 2 * hauteur);

		int abcisses[] = new int[] { x - largeur / 2, x - largeur / 2 + (3 * largeur), x - largeur / 2 + (largeur * 3 / 2) };
		int ordonnes[] = new int[] { y + (2 * hauteur), y + (2 * hauteur), y + (3 * hauteur) };

		g.fillPolygon(abcisses, ordonnes, 3);
		g.setPaint(Color.black);

	}

	public Coordonnee positionGrille(Coordonnee c)
	{
		Coordonnee p = new Coordonnee(-1, -1);
		p.ligne = CoordonneesPlateau[1] + (int) (c.ligne * segment) + originePlateauX;
		p.colonne = CoordonneesPlateau[0] + (int) (c.colonne * segment) + originePlateauY;
		return p;
	}

}
