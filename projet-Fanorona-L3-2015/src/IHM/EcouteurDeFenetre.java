package IHM;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import engine.EngineServices;

public class EcouteurDeFenetre implements WindowListener {

	public EngineServices	moteur;

	public EcouteurDeFenetre(EngineServices e) {
		moteur = e;
	}

	@Override
	public void windowActivated(WindowEvent e) {

	}

	@Override
	public void windowClosed(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {
		moteur.quitter();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {

	}

	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	@Override
	public void windowIconified(WindowEvent e) {

	}

	@Override
	public void windowOpened(WindowEvent e) {

	}

}
