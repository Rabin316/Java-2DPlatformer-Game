package ui;

import static utils.Constants.UI.PauseButton.*;
import static utils.Constants.UI.VolumeButtons.*;
import Main.Game;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import GameStates.Gamestate;

public class AudioOptions {
    private VolumeButton volumeButton;
    private SoundButton musicButton, sfxButton;
    private Game game;

    public AudioOptions(Game game) {
        createSoundButtons();
        createVolumeButton();
        this.game = game;
    }

    private void createVolumeButton() {
        // TODO Auto-generated method stub
        int vX = (int) (309 * Game.SCALE);
        int vY = (int) (278 * Game.SCALE);
        volumeButton = new VolumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    private void createSoundButtons() {
        int soundX = (int) (450 * Game.SCALE);
        int musicY = (int) (140 * Game.SCALE);
        int sfxY = (int) (186 * Game.SCALE);
        musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
    }

    public void update() {
        musicButton.update();
        sfxButton.update();
        volumeButton.update();
    }

    public void draw(Graphics g) {
        musicButton.draw(g);
        sfxButton.draw(g);

        volumeButton.draw(g);
    }

    public void mouseDragged(MouseEvent e) {

        if (volumeButton.isMousePressed()) {
            float valueBefore = volumeButton.getFloatValue();
            volumeButton.changeX(e.getX());
            float valueafter = volumeButton.getFloatValue();
            if (valueBefore != valueafter)
                game.getAudioPlayer().setVolume(valueafter);
        }
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, musicButton))
            musicButton.setMousePressed(true);
        else if (isIn(e, sfxButton))
            sfxButton.setMousePressed(true);
        else if (isIn(e, volumeButton))
            volumeButton.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, musicButton)) {
            if (musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
                game.getAudioPlayer().toggleSongMute();
            }

        } else if (isIn(e, sfxButton)) {
            if (sfxButton.isMousePressed()) {
                sfxButton.setMuted(!sfxButton.isMuted());
                game.getAudioPlayer().toggleEffectMute();
            }
        }

        musicButton.resetBools();
        sfxButton.resetBools();
        volumeButton.resetBools();

    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        volumeButton.setMouseOver(false);

        if (isIn(e, musicButton))
            musicButton.setMouseOver(true);
        else if (isIn(e, sfxButton))
            sfxButton.setMouseOver(true);
        else if (isIn(e, volumeButton))
            volumeButton.setMouseOver(true);

    }

    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}
