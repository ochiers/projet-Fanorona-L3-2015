/*
	 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
	 *
	 * Redistribution and use in source and binary forms, with or without
	 * modification, are permitted provided that the following conditions
	 * are met:
	 *
	 *   - Redistributions of source code must retain the above copyright
	 *     notice, this list of conditions and the following disclaimer.
	 *
	 *   - Redistributions in binary form must reproduce the above copyright
	 *     notice, this list of conditions and the following disclaimer in the
	 *     documentation and/or other materials provided with the distribution.
	 *
	 *   - Neither the name of Oracle or the names of its
	 *     contributors may be used to endorse or promote products derived
	 *     from this software without specific prior written permission.
	 *
	 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
	 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
	 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
	 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
	 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
	 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
	 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
	 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
	 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
	 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
	 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
	 * 
	 * Base recuperee pour ce code.
*/ 
package IHM;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class ChoixCouleur extends JPanel {

	    protected JColorChooser fenChoix1;
	    protected JColorChooser fenChoix2;
	    protected JLabel titre;

	    public ChoixCouleur() {
	        super(new BorderLayout());	//super constructeur par defaut

	        	//mettre titre dans fenetre
	        titre = new JLabel(" Choissir les couleurs des pions ", JLabel.CENTER);
	        titre.setForeground(Color.red);
	        titre.setBackground(Color.white);
	        titre.setOpaque(true);
	        titre.setFont(new Font("SansSerif", Font.BOLD, 24));
	        titre.setPreferredSize(new Dimension(100, 65));
	        	//creation panel conteneur
	        JPanel panelTitre = new JPanel(new BorderLayout());
	        //JButton bcc = new JButton(" VALIDER ");
	        //	bcc.addActionListener(this);	        
	        panelTitre.add(titre, BorderLayout.CENTER);
	        //panelTitre.add(bcc);
	        panelTitre.setBorder(BorderFactory.createTitledBorder("titre"));
	        	//mettre le colorchooser en place pour joueur 1
	        fenChoix1 = new JColorChooser(titre.getForeground());
	        fenChoix1.setBorder(BorderFactory.createTitledBorder(" Pions Joueur 1 "));
	        fenChoix1.setPreviewPanel(new JPanel());
	        	//mettre le colorchooser en place pour joueur 2
	        fenChoix2 = new JColorChooser(titre.getForeground());
	        fenChoix2.setBorder(BorderFactory.createTitledBorder(" Pions Joueur 2 "));
	        fenChoix2.setPreviewPanel(new JPanel());
	        	// ajouts
	        add(panelTitre, BorderLayout.NORTH);
	        add(fenChoix1, BorderLayout.CENTER);
	        add(fenChoix2, BorderLayout.PAGE_END);
	    }

	    private static void createAndShow() {
	        	//fenetre
	        JFrame frame = new JFrame("Choix couleurs pions");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        	//ajout colorchoosen
	        JComponent panelChoix = new ChoixCouleur();
	        panelChoix.setOpaque(true); 
	        frame.setContentPane(panelChoix);

	        frame.pack();
	        frame.setVisible(true);
	    }

	    public static void main(String[] args) {
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() { createAndShow(); }
	        });
	    }
}

