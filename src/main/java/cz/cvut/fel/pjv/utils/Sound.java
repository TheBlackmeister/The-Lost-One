package cz.cvut.fel.pjv.utils;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;

import static javax.sound.sampled.AudioSystem.*;


/**
 * class Sound adds cool NPC sound to this game
 * class Sound has several methods
 */
public class Sound {
    Clip clip;
    URL[] soundURL = new URL[25];

    public Sound(){
        soundURL[0] = getClass().getResource("/sound/music/CobblestoneVillage.wav");
    }

    public void setFile(int index){
        try{
            AudioInputStream ais = getAudioInputStream(soundURL[index]);
            clip = getClip();
            clip.open(ais);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
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
