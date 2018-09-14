package fr.kazz0r.launcher.main;

import javax.swing.JFrame;

public class LauncherOption {
  public static void main(String[] args){
    JFrame fenetre = new JFrame();
    
    fenetre.setTitle("Options");
    fenetre.setSize(500,250);
    fenetre.setLocationRelativeTo(null);
    fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    fenetre.setVisible(true);
  }  
}