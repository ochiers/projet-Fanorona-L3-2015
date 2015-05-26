package IHM;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcouteurPreferences implements ActionListener{
		Preferences pref;
		String message;
		
		public EcouteurPreferences(Preferences p,String s){
			pref=p;
			message=s;
		}
		
		public void actionPerformed(ActionEvent e) {
			switch (message){
			case " ... ":
				//TODO
				break;
			case " .... ":
				//TODO
				break;
			case " ..... ":
				//TODO
				break;
			case " .. ":
				//TODO
				break;
			default:
				System.out.println("Erreur bouton switch");
				break;
			}
		}

}
