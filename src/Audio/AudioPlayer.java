package Audio;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.BooleanControl.Type;

public class AudioPlayer {
    public static int MENU_1 = 0;
    public static int LEVEL_1 = 1;
    public static int LEVEL_2 = 2;

    public static int DIE = 0;
    public static int JUMP = 1;
    public static int GAMEOVER = 2;
    public static int LVL_COMPLETED = 3;
    public static int ATTACK_ONE = 4;
    public static int ATTACK_TWO = 5;
    public static int ATTACK_THREE = 6;

    private Clip[] songs, effects;
    private int currentSongId;
    private float volume = 0.7f;
    private boolean songMute, effectMute;
    private Random rand = new Random();

    public AudioPlayer() {
        loadSongs();
        loadEffects();
        playSong(MENU_1);
    }

    private void loadSongs() {
        String[] names = { "hodmenu", "HODlvl1", "level2" };
        songs = new Clip[names.length];
        for (int i = 0; i < songs.length; i++) {
            songs[i] = getClip(names[i]);
        }
    }

    private void loadEffects() {
        String[] effectnames = { "die", "jump", "gameover", "lvlcompleted", "attack1", "attack2", "attack3" };
        effects = new Clip[effectnames.length];
        for (int i = 0; i < effectnames.length; i++) {
            effects[i] = getClip(effectnames[i]);
        }
        updateEffectsVolume();
    }

    private Clip getClip(String name) {
        URL url = getClass().getResource("/audios/" + name + ".wav"); // Leading slash indicates absolute path;
        if (url == null) {
            System.out.println("Resource folder not found for: " + name);
            return null; // Exit early if the resource is not found
        }
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(url);
            Clip c = AudioSystem.getClip();
            c.open(audio);
            return c; // Return the clip after opening it
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setVolume(float volume) {
        this.volume = volume;
        updateSongVolume();
        updateEffectsVolume();
    }

    public void stopSong() {
        if (songs[currentSongId].isActive()) {
            songs[currentSongId].stop();
        }
    }

    public void setLevelSong(int lvlIndex) {
        if (lvlIndex % 2 == 0)
            playSong(LEVEL_1);
        else
            playSong(LEVEL_2);
    }

    public void lvlcompleted() {
        stopSong();
        playEffect(LVL_COMPLETED);
    }

    public void playAttackSound() {
        int start = 4;
        start += rand.nextInt(3);
        playEffect(start);

    }

    public void playEffect(int effect) {
        effects[effect].setMicrosecondPosition(0);
        effects[effect].start();
    }

    public void playSong(int song) {
        stopSong();

        currentSongId = song;
        updateSongVolume();
        songs[currentSongId].setMicrosecondPosition(0);
        songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void toggleSongMute() {
        this.songMute = !songMute;
        for (Clip c : songs) {
            BooleanControl booleancontrol = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleancontrol.setValue(songMute);
        }
    }

    public void toggleEffectMute() {
        this.effectMute = !effectMute;
        for (Clip c : effects) {
            BooleanControl booleancontrol = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleancontrol.setValue(effectMute);
        }
        if (!effectMute)
            playEffect(JUMP);
    }

    // These methods `updateSongVolume()` and `updateEffectsVolume()` are
    // responsible for adjusting the
    // volume of the currently playing song and effects in the `AudioPlayer` class.
    private void updateSongVolume() {
        if (songs[currentSongId] != null) {
            FloatControl gainControl = (FloatControl) songs[currentSongId].getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }

    private void updateEffectsVolume() {
        for (Clip c : effects) {
            if (c != null) {
                FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
                float range = gainControl.getMaximum() - gainControl.getMinimum();
                float gain = (range * volume) + gainControl.getMinimum();
                gainControl.setValue(gain);
            }
        }
    }

}
