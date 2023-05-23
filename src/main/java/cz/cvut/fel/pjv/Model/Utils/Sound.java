package cz.cvut.fel.pjv.Model.Utils;

import cz.cvut.fel.pjv.View.ErrorWindow;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;
import static javax.sound.sampled.AudioSystem.getClip;


/**
 * class Sound adds sounds to the game
 * class Sound has several methods
 * I was inspired when creating this class by channel 'RYISnow'. The tutorial showed me how to work with sounds in java.
 * https://youtu.be/nUHh_J2Acy8
 */
public class Sound {
    private static final Logger logger = Logger.getLogger(Sound.class.getName());
    ErrorWindow err;
    Clip clip;
    URL[] soundURL = new URL[25];

    public Sound(){
        err = new ErrorWindow();
        try {
            logger.info("Loading sounds");
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
            soundURL[13] = getClass().getResource("/sound/UI/horn.wav"); // horn
            soundURL[14] = getClass().getResource("/sound/UI/applause.wav"); // applause
            soundURL[15] = getClass().getResource("/sound/entity/towerDestructionSound.wav"); // tower fall
            soundURL[16] = getClass().getResource("/sound/entity/death1.wav"); // death
            soundURL[17] = getClass().getResource("/sound/entity/death2.wav"); // death
            soundURL[18] = getClass().getResource("/sound/entity/death3.wav"); // death
            soundURL[19] = getClass().getResource("/sound/entity/death4.wav"); // death
            soundURL[20] = getClass().getResource("/sound/entity/death5.wav"); // death
        }
        catch (Exception e1) {
            logger.severe("Sound files not found or have the wrong encoding.");
            err.badOpenedSoundFile("Initializing the sounds",7);
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
