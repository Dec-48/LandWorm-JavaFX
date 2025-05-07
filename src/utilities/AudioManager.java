package utilities;

import gui.scene.MainMenuState;
import javafx.scene.media.AudioClip;

public class AudioManager {
	private static AudioManager instance;
	private static double BGMVOLUME = 0.2 ; 
	private static double EFFECTVOLUME = 0.8;
	private static AudioClip effect;
	private static AudioClip bgm;

	
	private AudioManager() {
	}
	
	public static void setBGMVolume(double volume) {
		BGMVOLUME = volume;
	}
	
	public static void setEffectVolume(double volume) {
		EFFECTVOLUME = volume;
	}
	
	public static void playEffect(String effectPath) {
		effect = new AudioClip(ClassLoader.getSystemResource(effectPath).toString());
		effect.setVolume(EFFECTVOLUME);
		effect.play();
	}
	
	public static void playBGM(String backgroudMusicPath) {
		bgm = new AudioClip(ClassLoader.getSystemResource(backgroudMusicPath).toString());
		bgm.setVolume(BGMVOLUME);
		bgm.setCycleCount(AudioClip.INDEFINITE);
		bgm.play();
	}
	
	public static void stopBGM() {
		if (bgm != null) {
			bgm.stop();
		}
	}
	
	public static AudioManager getInstance() {
		if (instance == null) {
			instance = new AudioManager();
		}
		return instance;
	}
}
