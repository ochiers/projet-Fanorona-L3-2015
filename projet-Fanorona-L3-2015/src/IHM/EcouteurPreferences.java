package IHM;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcouteurPreferences implements ActionListener{
		Fenetre fenetre;
		String message;
		
		public EcouteurPreferences(Fenetre f,String s){
			fenetre=f;
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
