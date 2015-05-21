package engine;

/**
 * Represente des coordonnees matricielles
 * 
 * @author soulierc
 *
 */
public class Coordonnee {

	public final int	ligne;
	public final int	colonne;

	/**
	 * Cree une coordonnee [ligne][colonne]
	 * 
	 * @param ligne
	 * @param colonne
	 */
	public Coordonnee(int ligne, int colonne)
	{

		this.ligne = ligne;
		this.colonne = colonne;

	}

	/**
	 * Renvoie la composante ligne
	 * 
	 * @return un entier etant la ligne
	 */
	public int getLigne()
	{
		return this.ligne;
	}

	/**
	 * Renvoie la composante colonne
	 * 
	 * @return un entier etant la colonne
	 */
	public int getColonne()
	{

		return this.colonne;
	}

}
