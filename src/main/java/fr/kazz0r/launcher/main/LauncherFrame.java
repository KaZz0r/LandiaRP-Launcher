 package fr.kazz0r.launcher.main;
 import javax.swing.JFrame;

 
 import fr.theshark34.openlauncherlib.LanguageManager;
 import fr.theshark34.openlauncherlib.util.CrashReporter;
 import fr.theshark34.swinger.Swinger;
 import fr.theshark34.swinger.util.WindowMover;
 
@SuppressWarnings("serial")
public class LauncherFrame extends JFrame {
			   
   private static LauncherFrame instance;
   private static CrashReporter crashReporter;
   private LauncherPanel launcherPanel;
   git
   public LauncherFrame()
   {
     setTitle("La Pause RolePlay");
     setSize(1075, 715);
     setDefaultCloseOperation(3);
     setLocationRelativeTo(null);
     setUndecorated(true);
     setIconImage(Swinger.getResource("icone1.png"));
     setContentPane(this.launcherPanel = new LauncherPanel());
     
     WindowMover mover = new WindowMover(this);
     addMouseListener(mover);
     addMouseMotionListener(mover);
     
     setVisible(true);
   }
   
   public static void main(String[] args)
   {
     LanguageManager.setLang(LanguageManager.FRENCH);
     
     Swinger.setSystemLookNFeel();
     Swinger.setResourcePath("/src/java/launcher/ressources/");
     Launcher.LPRP_CRASH_FOLDER.mkdirs();
     crashReporter = new CrashReporter("LPRP Launcher", Launcher.LPRP_CRASH_FOLDER);
     
     instance = new LauncherFrame();
   }
   
   public static LauncherFrame getInstance()
   {
     return instance;
   }
   
   public static CrashReporter getCrashReporter()
   {
     return crashReporter;
   }
   
   public LauncherPanel getLauncherPanel()
   {
     return this.launcherPanel;
   }
 }