/*    */ package fr.kazz0r.launcher.main;
/*    */

import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.minecraft.GameFolder;
import fr.theshark34.openlauncherlib.minecraft.MinecraftLauncher;
import fr.theshark34.openlauncherlib.util.ProcessLogManager;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.supdate.application.integrated.FileDeleter;
import fr.theshark34.swinger.Swinger;

import java.io.File;
import java.util.Arrays;

import static fr.kazz0r.launcher.utils.Constants.*;

public class Launcher {
	private static Thread updateThread;

	public static void update() throws Exception {

		SUpdate su = new SUpdate(LPRP_SERVER + "launcher/", LPRP_DIR);
		su.addApplication(new FileDeleter());

		updateThread = new Thread() {

			private int val;
			private int max;

			@Override
			public void run() {

				while (!this.isInterrupted()) {
					if (BarAPI.getNumberOfFileToDownload() == 0) {

						LauncherFrame.getInstance().getLauncherPanel().setInfoText("Vérification des fichiers");
						continue;

					}

					LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setMaximum(max);
					LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setValue(val);

					LauncherFrame.getInstance().getLauncherPanel().setInfoText("Téléchargement des fichiers..." +
							BarAPI.getNumberOfDownloadedFiles() + "/" + BarAPI.getNumberOfFileToDownload() + " " +
							Swinger.percentage(val, max) + "%");


					val = (int) (BarAPI.getNumberOfTotalDownloadedBytes() / 1000);
					max = (int) (BarAPI.getNumberOfTotalBytesToDownload() / 1000);
				}

			}
		};
		updateThread.start();

		su.start();

		updateThread.interrupt();

	}

	public static void launch(String username) throws LaunchException {
		ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(LPRP_INFOS, GameFolder.BASIC, new AuthInfos(username, "sry", "nope"));
		profile.getVmArgs().addAll(Arrays.asList(LauncherFrame.getInstance().getLauncherPanel().getRamSelector().getRamArguments()));
		ExternalLauncher launcher = new ExternalLauncher(profile);

		LauncherFrame.getInstance().setVisible(false);
		Process p = launcher.launch();

		ProcessLogManager manager = new ProcessLogManager(p.getInputStream(), new File(LPRP_DIR, "logs.txt"));
		manager.start();

		System.exit(0);
	}

	public static void interruptThread() {
		updateThread.interrupt();
	}
}

