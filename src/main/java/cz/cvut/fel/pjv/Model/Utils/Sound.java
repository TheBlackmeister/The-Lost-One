package cz.cvut.fel.pjv.Model.Utils;

import cz.cvut.fel.pjv.View.ErrorWindow;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;
import static javax.sound.sampled.AudioSystem.getClip;


/**
 * class Sound adds sounds to the game
 * class Sound has several methods
 */
public class Sound {
    ErrorWindow err;
    Clip clip;
    URL[] soundURL = new URL[25];

    public Sound(){
        err = new ErrorWindow();
        try {
            soundURL[0] = getClass().getResource("/sound/gun/pistolShot1.wav"); // pistol sound 1
            soundURL[1] = getClass().getResource("/sound/gun/pistolShot2.wav"); // pistol sound 2
            soundURL[2] = getClass().getResource("/sound/gun/pistolShot3.wav"); // pistol sound 3
            soundURL[3] = getClass().getResource("/sound/gun/MGShot1.wav"); // MGShot1
            soundURL[4] = getClass().getResource("/sound/gun/MGShot2.wav"); // MGShot2
            soundURL[5] = getClass().getResource("/sound/footstep/footstep1.wav"); // footstep
            soundURL[6] = getClass().getResource("/sound/footstep/footstep2.wav"); // footstep
            soundURL[7] = getClass().getResource("/sound/footstep/footstep3.wav"); // footstep
            soundURL[8] = getClass().getResource("/sound/footstep/footstep4.wav"); // footstep
            soundURL[9] = getClass().getResource("/sound/footstep/footstep5.wav"); // footstep
            soundURL[10] = getClass().getResource("/sound/gun/rocketLaunch.wav"); // rocket launch
            soundURL[11] = getClass().getResource("/sound/gun/explosion.wav"); // rocket explosion
            soundURL[12] = getClass().getResource("/sound/UI/click.wav"); // UI click

        }
        catch (Exception e1) {
            err.IOExceptionErrorHandler("Initializing the sounds",7);
            throw new RuntimeException(e1);
        }
    }

    public void setFile(int index){
        try{
            AudioInputStream ais = getAudioInputStream(soundURL[index]);
            clip = getClip();
            clip.open(ais);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            err.IOExceptionErrorHandler(String.valueOf(soundURL[index]),7);
            throw new RuntimeException(e);
        } catch (Exception e1){
            err.IOExceptionErrorHandler("Sound " + index,7);
            throw new RuntimeException(e1);
        }

    }

    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}
