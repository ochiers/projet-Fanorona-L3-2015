package engine;

/**
 * Represente des coordonnees matricielles
 * 
 * @author soulierc
 *
 */
public class Coordonnee {

	public int	ligne;
	public int	colonne;

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

	public boolean equals(Object o){
		
		if(o != null && o instanceof Coordonnee)
		{
			Coordonnee c = (Coordonnee) o;
			return c.ligne == this.ligne && c.colonne == this.colonne;
		}
		return false;
		
	}
	
}
