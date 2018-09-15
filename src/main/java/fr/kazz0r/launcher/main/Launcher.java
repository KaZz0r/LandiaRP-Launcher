/*    */ package fr.kazz0r.launcher.main;
/*    */

import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.*;
import fr.theshark34.openlauncherlib.util.ProcessLogManager;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.supdate.application.integrated.FileDeleter;
import fr.theshark34.swinger.Swinger;

import java.io.File;
import java.util.Arrays;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ 
/*    */ public class Launcher
/*    */ {
/* 26 */   public static final GameVersion LPRP_VERSION = new GameVersion("1.7.10", GameType.V1_7_10);
	/* 27 */   public static final GameInfos LPRP_INFOS = new GameInfos("LandiaRP", LPRP_VERSION, new GameTweak[]{GameTweak.FORGE});
/* 28 */   public static final File LPRP_DIR = LPRP_INFOS.getGameDir();
/* 29 */   public static final File LPRP_CRASH_FOLDER = new File(LPRP_DIR, "crashes");
/*    */   private static Thread updateThread;
/*    */   
public static void update() throws Exception {

	SUpdate su = new SUpdate("http://62.210.219.74/launcher/", LPRP_DIR);
	su.addApplication(new FileDeleter());

	updateThread = new Thread() {

		private int val;
		private int max;

		@Override
		public void run() {

			while(!this.isInterrupted()) {
				if(BarAPI.getNumberOfFileToDownload() == 0) {

					LauncherFrame.getInstance().getLauncherPanel().setInfoText("Vérification des fichiers");
					continue;

				}

				LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setMaximum(max);
				LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setValue(val);

				LauncherFrame.getInstance().getLauncherPanel().setInfoText("Téléchargement des fichiers..." +
						BarAPI.getNumberOfDownloadedFiles() + "/" + BarAPI.getNumberOfFileToDownload() + " " +
						Swinger.percentage(val, max) + "%");


				val = (int) (BarAPI.getNumberOfTotalDownloadedBytes() / 1000 );
				max = (int) (BarAPI.getNumberOfTotalBytesToDownload() / 1000 );

			}

		}


	};
	updateThread.start();

	su.start();

	updateThread.interrupt();

}
/*    */   
/*    */   public static void launch(String username)
/*    */     throws LaunchException
/*    */   {
/* 73 */     ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(LPRP_INFOS, GameFolder.BASIC, new AuthInfos(username, "sry", "nope"));
/* 74 */     profile.getVmArgs().addAll(Arrays.asList(LauncherFrame.getInstance().getLauncherPanel().getRamSelector().getRamArguments()));
/* 75 */     ExternalLauncher launcher = new ExternalLauncher(profile);
/*    */     
/* 77 */     LauncherFrame.getInstance().setVisible(false);
/* 78 */     Process p = launcher.launch();
/*    */     
/* 80 */     ProcessLogManager manager = new ProcessLogManager(p.getInputStream(), new File(LPRP_DIR, "logs.txt"));
/* 81 */     manager.start();
/*    */     
/* 83 */     System.exit(0);
/*    */   }
/*    */   
/*    */   public static void interruptThread()
/*    */   {
/* 88 */     updateThread.interrupt();
/*    */   }
/*    */ }


/* Location:              C:\Users\pc\Downloads\Exodia_RP.jar!\fr\staliax\exodia\main\Launcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */