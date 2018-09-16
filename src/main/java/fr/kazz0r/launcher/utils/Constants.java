package fr.kazz0r.launcher.utils;

import fr.theshark34.openlauncherlib.minecraft.GameInfos;
import fr.theshark34.openlauncherlib.minecraft.GameTweak;
import fr.theshark34.openlauncherlib.minecraft.GameType;
import fr.theshark34.openlauncherlib.minecraft.GameVersion;

import java.io.File;

public class Constants {
	public static final GameVersion LPRP_VERSION = new GameVersion("1.7.10", GameType.V1_7_10);
	public static final GameInfos LPRP_INFOS = new GameInfos("LandiaRP", LPRP_VERSION, new GameTweak[]{GameTweak.FORGE});
	public static final File LPRP_DIR = LPRP_INFOS.getGameDir();
	public static final File LPRP_CRASH_FOLDER = new File(LPRP_DIR, "crashes");
	public static final String LPRP_SERVER = "http://62.210.219.74/";
}
