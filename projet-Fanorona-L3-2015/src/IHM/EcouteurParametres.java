package IHM;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import engine.*;
import AI.*;

public class EcouteurParametres implements ActionListener{
	Parametres parametre;
	
	public EcouteurParametres(Parametres p){
		parametre=p;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==parametre.r1b1){
			parametre.saveMode=1;
		}else if(e.getSource()==parametre.r1b2){
			parametre.saveMode=2;
		}else if(e.getSource()==parametre.r1b3){
			parametre.saveMode=3;
		}else if(e.getSource()==parametre.r2b1){
			parametre.savelvlPC1=1;
		}else if(e.getSource()==parametre.r2b2){
			parametre.savelvlPC1=2;
		}else if(e.getSource()==parametre.r2b3){
			parametre.savelvlPC1=3;
		}else if(e.getSource()==parametre.r3b1){
			parametre.savelvlPC2=1;
		}else if(e.getSource()==parametre.r3b2){
			parametre.savelvlPC2=2;
		}else if(e.getSource()==parametre.r3b3){
			parametre.savelvlPC2=3;
		}
		else if(e.getSource()==parametre.accepter){
			parametre.fenetre.mode=parametre.saveMode;
			parametre.fenetre.lvlPC1=parametre.savelvlPC1;
			parametre.fenetre.lvlPC2=parametre.savelvlPC2;
			parametre.fenetre.frame2.setVisible(false);
		}
		else if(e.getSource()==parametre.annuler){
			if(parametre.fenetre.mode==1){
				parametre.r1b1.setSelected(true);
			}else if(parametre.fenetre.mode==2){
				parametre.r1b2.setSelected(true);
			}else if(parametre.fenetre.mode==3){
				parametre.r1b3.setSelected(true);
			}
			
			if(parametre.fenetre.lvlPC1==1){
				parametre.r2b1.setSelected(true);
			}else if(parametre.fenetre.lvlPC1==2){
				parametre.r2b2.setSelected(true);
			}else if(parametre.fenetre.lvlPC1==3){
				parametre.r2b3.setSelected(true);
			}
			if(parametre.fenetre.lvlPC2==1){
				parametre.r3b1.setSelected(true);
			}else if(parametre.fenetre.lvlPC1==2){
				parametre.r3b2.setSelected(true);
			}else if(parametre.fenetre.lvlPC2==3){
				parametre.r3b3.setSelected(true);
			}
			parametre.fenetre.frame2.setVisible(false);
		}
		

	}

}
