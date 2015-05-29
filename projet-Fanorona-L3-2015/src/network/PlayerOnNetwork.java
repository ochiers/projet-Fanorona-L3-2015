package network;

public class PlayerOnNetwork {

	// TODO 1. Etablir la connexion
	// TODO 2. Envoyer la configuration
	// TODO 3. Envoyer un coup valide au moteur distant
	// TODO 4. Envoyer le coup au joueur (moteur -> joueur)
	
	//TODO Système de requêtes : Envoie d'une annulation/refaire

	
	// Comment déterminer celui qui commence ?
	// Le moteur analyse en local les coups tenter par le joueur humain puis l'envoie sur le r�seau. Seul des coups valides sont envoyés.
	// Comment annuler et refaire en réseau ? Requête ? 
	
	/**
	 * Etablie la connexion entre les deux ordinateurs.
	 * Elle prend une adresse ip ainsi qu'un numéro de port pour l'établir.
	 */
	public void etablishConnection()
	{ // Une ip et un port

	}

	/**
	 * Envoie de la configuration de la machine principale vers la deuxi�me.
	 */
	public void sendConfig()
	{

	}

	/**
	 * Méthode permettant de recevoir la configuration partagée entre les 2 ordinateurs.
	 */
	public void receiveConfig()
	{

	}

	/**
	 * Envoie du coup valide du joueur humain.
	 */
	public void sendCoup()
	{

	}

	/**
	 * Réception du coup envoyé sur le réseau.
	 */
	public void receiveCoup()
	{

	}
}
