/*     */ package fr.kazz0r.launcher.main;
/*     */ import java.awt.Color;
/*     */ import java.awt.Desktop;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;

		  import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
		  import javax.swing.SwingConstants;

/*     */ 
/*     */ import fr.theshark34.openlauncherlib.LaunchException;
/*     */ import fr.theshark34.openlauncherlib.util.Saver;
/*     */ import fr.theshark34.openlauncherlib.util.ramselector.OptionFrame;
/*     */ import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;
/*     */ import fr.theshark34.swinger.Swinger;
		  import fr.theshark34.swinger.colored.SColoredBar;
/*     */ import fr.theshark34.swinger.event.SwingerEvent;
/*     */ import fr.theshark34.swinger.event.SwingerEventListener;
/*     */ import fr.theshark34.swinger.textured.STexturedButton;
/*     */ import fr.theshark34.swinger.textured.STexturedProgressBar;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
			@SuppressWarnings("serial")
			public class LauncherPanel extends JPanel implements SwingerEventListener {
				
/*  33 */   private Image background = Swinger.getResource("background.png");
/*     */   
/*  35 */   private Saver saver = new Saver(new File(Launcher.LPRP_DIR, "launcher.properties"));
/*  36 */   private JTextField usernameField = new JTextField(this.saver.get("username"));
			private STexturedButton playButton = new STexturedButton(Swinger.getResource("jouer.png"));
			private STexturedButton quitButton = new STexturedButton(Swinger.getResource("quitter.png"));
			private STexturedButton hideButton = new STexturedButton(Swinger.getResource("reduire.png"));
/*  40 */   private STexturedButton optionsButton = new STexturedButton(Swinger.getResource("option.png"));
/*  41 */   private SColoredBar progressBar = new SColoredBar(new Color(57, 57, 57), new Color(236, 46, 46));
			private JLabel InfosLabel = new JLabel("Clique sur jouer !",SwingConstants.CENTER);
/*  42 */   private RamSelector ramSelector = new RamSelector(new File(Launcher.LPRP_DIR, "ram.txt"));
/*     */  
/*     */   
/*     */ 
/*     */ 
/*     */   public LauncherPanel()
/*     */   {
/*  50 */     setLayout(null);
/*  51 */     setBackground(Swinger.TRANSPARENT);
/*     */     
			  usernameField.setForeground(Color.WHITE);
			  usernameField.setFont(usernameField.getFont().deriveFont(20F));
			  usernameField.setCaretColor(Color.WHITE);
			  usernameField.setBorder(null);
			  usernameField.setOpaque(false);
			  usernameField.setBounds(20, 365, 496, 35);//X, Y, Z, Taille
			  this.add(usernameField);

			  playButton.setBounds(149, 540);
			  playButton.addEventListener(this);
			  this.add(playButton);

			  quitButton.setBounds(1015, 20);
			  quitButton.addEventListener(this);
			  this.add(quitButton);

			  hideButton.setBounds(873, 40);
			  hideButton.addEventListener(this);
			  this.add(hideButton);
/*     */     
/*  68 */     this.optionsButton.setBounds(950, 20);
/*  69 */     this.optionsButton.addEventListener(this);
/*  70 */     add(this.optionsButton);
/*     */     
			  progressBar.setBounds(33, 655, 450, 35);
			  this.add(progressBar);
/*     */     
			  InfosLabel.setFont(usernameField.getFont());
			  InfosLabel.setForeground(Color.WHITE);
			  InfosLabel.setBounds(33, 615, 450, 40);
			  this.add(InfosLabel);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onEvent(SwingerEvent event)
/*     */   {
/*  96 */     if (event.getSource() == this.playButton)
/*     */     {
/*  98 */       setFieldsEnabled(false);
/*  99 */       if (this.usernameField.getText().replaceAll(" ", "").length() == 0)
/*     */       {
/* 101 */         JOptionPane.showMessageDialog(this, "Erreur, veuillez entrer un pseudo et un mot de passe valides.", "Erreur", 0);
/* 102 */         setFieldsEnabled(true);
/* 103 */         return;
/*     */       }
/* 105 */       Thread t = new Thread()
/*     */       {
/*     */         public void run()
/*     */         {
/* 109 */           LauncherPanel.this.saver.set("username", LauncherPanel.this.usernameField.getText());
/* 110 */           LauncherPanel.this.ramSelector.save();
/*     */           try
/*     */           {
/* 113 */             Launcher.update();
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 117 */             Launcher.interruptThread();
/* 118 */             LauncherFrame.getCrashReporter().catchError(e, "Erreur, impossible de mettre a jour le jeu.");
/*     */           }
/*     */           try
/*     */           {
/* 122 */             Launcher.launch(LauncherPanel.this.usernameField.getText());
/*     */           }
/*     */           catch (LaunchException e)
/*     */           {
/* 126 */             LauncherFrame.getCrashReporter().catchError(e, "Erreur, impossible de lancer le jeu");
/*     */           }
/*     */         }
/* 129 */       };
/* 130 */       t.start();
/*     */     }
/* 132 */     else if (event.getSource() == this.quitButton)
/*     */     {
/* 134 */       System.exit(0);
/*     */     }
/* 136 */     else if (event.getSource() == this.hideButton)
/*     */     {
/* 138 */       LauncherFrame.getInstance().setState(1);
/*     */ 
/*     */ 
/*     */     }
/* 164 */     else if (event.getSource() == this.optionsButton)
/*     */     {

/*     */     }
/*     */   }
/*     */   
/*     */   public void paintComponent(Graphics graphics)
/*     */   {
/* 177 */     super.paintComponent(graphics);
/*     */     
/* 179 */     Swinger.drawFullsizedImage(graphics, this, this.background);
/*     */   }
/*     */   
/*     */ 
/*     */   private void setFieldsEnabled(boolean enabled)
/*     */   {
/* 185 */     this.usernameField.setEnabled(enabled);
/* 186 */     this.playButton.setEnabled(enabled);
/* 187 */     this.optionsButton.setEnabled(false);
/*     */   }
/*     */   
/*     */   public RamSelector getRamSelector()
/*     */   {
/* 192 */     return this.ramSelector;
/*     */   }
/*     */   
			public SColoredBar getProgressBar() {

				return progressBar;

			}
/*     */   
/*     */   public void setInfoText(String text) {}
/*     */ }