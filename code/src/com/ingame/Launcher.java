/*
 * 
 * by: Gustavo Passos
 * 
 * 06/2016
 * 
 */


package com.ingame;

import java.io.IOException;


public class Launcher
{
	
	
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
