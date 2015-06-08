package network;

/**
 * Classe definissant les differentes requetes pouvant transiter sur le reseau
 * @author soulierc
 *
 */
public class RequestType
{

	public static final int EnvoiCoup = 100;
	public static final int Annuler = 101;
	public static final int Refaire = 102;
	public static final int Recommencer = 103;
	public static final int FinDuTour = 104;
	public static final int NouvellePartie = 105;
	public static final int Quitter = 106;
	public static final int EnvoiCase = 107;
	public static final int ReponseOUI = 108;
	public static final int ReponseNON = 109;
	public static final int DemanderConfirmationAnnuler = 110;
	public static final int DemanderConfirmationRefaire = 111;
	public static final int	DemanderConfirmationRecommencer	= 112;
	public static final int	EnvoiNom	= 113;
}
