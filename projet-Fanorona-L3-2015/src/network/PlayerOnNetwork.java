package network;

public class PlayerOnNetwork {

	// TODO 1. Etablir la connexion
	// TODO 2. Envoyer la configuration
	// TODO 3. Envoyer un coup valide au moteur distant
	// TODO 4. Envoyer le coup au joueur (moteur -> joueur)
	
	//TODO Syst�me de requ�tes : Envoie d'une annulation/refaire

	
	// Comment d�terminer celui qui commence ?
	// Le moteur analyse en local les coups tenter par le joueur humain puis l'envoie sur le r�seau. Seul des coups valides sont envoy�s.
	// Comment annuler et refaire en r�seau ? Requ�te ? 
	
	/**
	 * Etablie la connexion entre les deux ordinateurs.
	 * Elle prend une adresse ip ainsi qu'un num�ro de port pour l'�tablir.
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
	 * M�thode permettant de recevoir la configuration partag�e entre les 2 ordinateurs.
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
	 * R�ception du coup envoy� sur le r�seau.
	 */
	public void receiveCoup()
	{

	}
}
