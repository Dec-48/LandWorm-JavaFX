package Manager;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioManager {
    private static AudioManager instance;
    private static double BGMVOLUME = 0.2; 
    private static double EFFECTVOLUME = 2;
    private static MediaPlayer effect;
    private static MediaPlayer bgm;

    private AudioManager() {
    }

    public static void setBGMVolume(double volume) {
        BGMVOLUME = volume;
        if (bgm != null) {
            bgm.setVolume(BGMVOLUME);
        }
    }

    public static void setEffectVolume(double volume) {
        EFFECTVOLUME = volume;
    }

    public static void playEffect(String effectPath) {
        effect = new MediaPlayer(new Media(ClassLoader.getSystemResource(effectPath).toString()));
        effect.setVolume(EFFECTVOLUME);
        effect.play();
    }

    public static void playBGM(String backgroudMusicPath) {
        stopBGM(); 
        bgm = new MediaPlayer(new Media(ClassLoader.getSystemResource(backgroudMusicPath).toString()));
        bgm.setVolume(BGMVOLUME); 
        bgm.setCycleCount(MediaPlayer.INDEFINITE);
        bgm.play();
    }

    public static void stopBGM() { 
        if (bgm != null) {
            bgm.stop();
            bgm = null;
        }
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }
}
