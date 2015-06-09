package IHM;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import engine.*;

public class EcouteurDeSouris implements MouseListener, MouseMotionListener {
	public AireDeDessin	aire;

	public EcouteurDeSouris(AireDeDessin a) {

		aire = a;
	}

	public void mouseClicked(MouseEvent e) {
		if (!aire.fenetre.engine.getJoueurCourant().aiPlayer && !aire.fenetre.engine.getCurrentGame().isPaused()) {
			int buttonDown = e.getButton();
			if (buttonDown == MouseEvent.BUTTON1) {// Bouton GAUCHE enfonce
				aire.pfinal = new Coordonnee(-1, -1);
				aire.pfinal = position(e.getX(), e.getY());
				if (aire.pfinal.colonne != -1 && aire.pfinal.ligne != -1) {
					if (aire.pionCliquer) {
						if (aire.fenetre.engine.getJoueurCourant() instanceof HumanPlayer) {
							((HumanPlayer) aire.fenetre.engine.getJoueurCourant()).setCoup(aire.pCourant, aire.pfinal);
							aire.pionCliquer = false;
							aire.surbrillance = false;
							aire.enSuggestion = false;
						}
					} else {
						if (aire.doitChoisir) {
							if (aire.estUnChoix(aire.pfinal)) {
								((HumanPlayer) aire.fenetre.engine.getJoueurCourant()).setDirectionMultiPrise(aire.pfinal);
								aire.doitChoisir = false;
							}
						} else {
							if (aire.estJouable(aire.pfinal) || (aire.fenetre.engine.getCurrentGame().enCombo && aire.pionCombo.position.ligne == aire.pfinal.ligne && aire.pionCombo.position.colonne == aire.pfinal.colonne)) {
								aire.pCourant.colonne = aire.pfinal.colonne;
								aire.pCourant.ligne = aire.pfinal.ligne;
								aire.pionCliquer = true;
							}
						}
					}
					aire.repaint();
				} else {
					aire.pionCliquer = false;
					aire.repaint();
				}
			} else if (buttonDown == MouseEvent.BUTTON2) {// Bouton du MILIEU enfonce
			} else if (buttonDown == MouseEvent.BUTTON3) {// Bouton DROIT enfonce
				aire.pionCliquer = false;
				aire.enSuggestion = false;
				aire.repaint();
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {

		if (!aire.fenetre.engine.getJoueurCourant().aiPlayer && !aire.fenetre.engine.getCurrentGame().isPaused()) {
			switch (e.getButton()) {
				case MouseEvent.BUTTON1: // Bouton Gauche Enfonce
					aire.pfinal = position(e.getX(), e.getY());
					if (aire.pfinal.colonne != -1 && aire.pfinal.ligne != -1 && !aire.pionCliquer) {
						if (aire.doitChoisir && aire.estUnChoix(aire.pfinal)) {
							((HumanPlayer) aire.fenetre.engine.getCurrentGame().joueurCourant).setDirectionMultiPrise(aire.pfinal);
							aire.doitChoisir = false;
						} else if (aire.estJouable(aire.pfinal) || (aire.fenetre.engine.getCurrentGame().enCombo && aire.pionCombo.position.ligne == aire.pfinal.ligne && aire.pionCombo.position.colonne == aire.pfinal.colonne)) {
							aire.pCourant.colonne = aire.pfinal.colonne;
							aire.pCourant.ligne = aire.pfinal.ligne;
							aire.pionCliquer = true;
						}
					}
					break;
				case MouseEvent.BUTTON2: // Molette Enfoncee
					break;
				case MouseEvent.BUTTON3: // Bouton Droit Enfonce
					aire.pionCliquer = false;
					aire.enSuggestion = false;
					break;
			}
			aire.repaint();
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (!aire.fenetre.engine.getJoueurCourant().aiPlayer && !aire.fenetre.engine.getCurrentGame().isPaused()) {
			int buttonDown = e.getButton();
			if (buttonDown == MouseEvent.BUTTON1) {// Bouton GAUCHE enfonce
				aire.pfinal = new Coordonnee(-1, -1);
				aire.pfinal = position(e.getX(), e.getY());
				if (aire.pfinal.colonne != -1 && aire.pfinal.ligne != -1) {
					if (aire.pionCliquer && aire.fenetre.engine.getJoueurCourant() instanceof HumanPlayer) {
						((HumanPlayer) aire.fenetre.engine.getJoueurCourant()).setCoup(aire.pCourant, aire.pfinal);
						aire.pionCliquer = false;
						aire.surbrillance = false;
						aire.enSuggestion = false;
					}
					aire.repaint();
				} else {
					aire.pionCliquer = false;
					aire.surbrillance = false;
					aire.repaint();
				}
			} else if (buttonDown == MouseEvent.BUTTON2) {// Bouton du MILIEU enfonce
			} else if (buttonDown == MouseEvent.BUTTON3) {// Bouton DROIT enfonce
				aire.pionCliquer = false;
				aire.enSuggestion = false;
				aire.repaint();
			}
		}
	}

	public void mouseDragged(MouseEvent e) {
		deplacement(e);

	}

	public void mouseMoved(MouseEvent e) {
		deplacement(e);

	}

	public int sqr(int a) {
		return a * a;
	}

	public int distance(Coordonnee p1, Coordonnee p2) {
		return (int) Math.sqrt(sqr(p2.colonne - p1.colonne) + sqr(p2.ligne - p1.ligne));
	}

	public Coordonnee position(int x, int y) {
		Coordonnee p = new Coordonnee(-1, -1);
		p.colonne = x - aire.originePlateauX - ((int) (aire.CoordonneesPlateau[0] * aire.etir - aire.segment));
		p.ligne = y - aire.originePlateauY - ((int) (aire.CoordonneesPlateau[1] * aire.etir - aire.segment));
		int nbCoteLargeur = p.colonne / (int) aire.segment;
		int nbCoteHauteur = p.ligne / (int) aire.segment;

		// haut gauche
		Coordonnee p1 = new Coordonnee((int) (nbCoteHauteur * aire.segment), (int) (nbCoteLargeur * aire.segment));
		// haut droit
		Coordonnee p2 = new Coordonnee((int) (nbCoteHauteur * aire.segment), (int) ((nbCoteLargeur + 1) * aire.segment));
		// bas gauche
		Coordonnee p3 = new Coordonnee((int) ((nbCoteHauteur + 1) * aire.segment), (int) (nbCoteLargeur * aire.segment));
		// bas droit
		Coordonnee p4 = new Coordonnee((int) ((nbCoteHauteur + 1) * aire.segment), (int) ((nbCoteLargeur + 1) * aire.segment));
		Coordonnee pfinal = new Coordonnee(-1, -1);

		if (distance(p, p1) <= (aire.tailleJeton / 2)) {
			pfinal.colonne = nbCoteLargeur - 1;
			pfinal.ligne = nbCoteHauteur - 1;
		} else if (distance(p, p2) <= (aire.tailleJeton / 2)) {
			pfinal.colonne = nbCoteLargeur;
			pfinal.ligne = nbCoteHauteur - 1;
		} else if (distance(p, p3) <= (aire.tailleJeton / 2)) {
			pfinal.colonne = nbCoteLargeur - 1;
			pfinal.ligne = nbCoteHauteur;
		} else if (distance(p, p4) <= (aire.tailleJeton / 2)) {
			pfinal.colonne = nbCoteLargeur;
			pfinal.ligne = nbCoteHauteur;
		}
		return pfinal;

	}

	public void deplacement(MouseEvent e) {
		if (aire.pionCliquer) {
			Coordonnee p = position(e.getX(), e.getY());
			if (!(aire.surbrillance && aire.pSurbrillance.ligne == p.ligne && aire.pSurbrillance.colonne == p.colonne))

			{
				if (p.ligne >= 0 && p.colonne >= 0) {

					ArrayList<Case> emplacementPossible1 = aire.fenetre.engine.getCurrentGame().coupsPossiblesPourUnPion(aire.fenetre.engine.getPlateau()[aire.pfinal.ligne][aire.pfinal.colonne]);
					ArrayList<Case> emplacementPossible = aire.fenetre.engine.getCurrentGame().coupsPourPriseParUnPion(emplacementPossible1, aire.fenetre.engine.getPlateau()[aire.pfinal.ligne][aire.pfinal.colonne]);
					if (aire.combo != null) {
						emplacementPossible = aire.coupReel(emplacementPossible, aire.combo);
					}
					if (emplacementPossible != null) {
						if (emplacementPossible.size() != 0) {
							for (int i = 0; i < emplacementPossible.size(); i++) {
								if (emplacementPossible.get(i).position.ligne == p.ligne && emplacementPossible.get(i).position.colonne == p.colonne) {
									aire.surbrillance = true;
									aire.pSurbrillance = p;
									aire.repaint();
								}
							}
						} else {
							for (int i = 0; i < emplacementPossible1.size(); i++) {
								if (emplacementPossible1.get(i).position.ligne == p.ligne && emplacementPossible1.get(i).position.colonne == p.colonne) {
									aire.surbrillance = true;
									aire.pSurbrillance = p;
									aire.repaint();
								}
							}
						}
					}

				} else {
					if (aire.surbrillance) {
						aire.surbrillance = false;
						aire.repaint();
					}
				}
			}
		}
	}
}
