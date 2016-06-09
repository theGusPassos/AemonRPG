

package com.ingame;

import java.io.IOException;


public class Launcher
{
	
	//Feito para poder rodar o jogo com dois cliques no .jar exportado
	 public static void main(String[] args) {
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	    			try {
						new StartMenu();
					} catch (IOException e) {
						e.printStackTrace();
					}		

	            }
	        });
	    }
	
}
