package projects;

import javax.sound.sampled.*;  //alows working with audio clips
import javax.swing.*;
import java.io.IOException;    // to handle input and output errors

public class soundManager {
    private Clip clip;//an object in javas sound sampled class. Can be played,stopped, and looped
    private boolean isMuted = false;
    private String currentLoopingFile = "pink-panther-6836.wav";
    public void playSound(String soundFile) {
        try {
            // Load audio from resources using URL
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(
                    getClass().getResource(soundFile)  //loads the sound file from the resource folder using its path
            );
            clip = AudioSystem.getClip(); // the object holds the clip to be played
            clip.open(audioIn); // loads the audio file in the memory
            clip.start();  // starts to play the audio
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();  // exceptional handling maybe if the file isnt found or something like that handles that
        }
    }


    // Method to stop the currently playing sound
    public void stopSound() {
        if (clip != null && clip.isRunning()) { // null ensure yuo have a valid audio loaded and isplaying makes sure that it is playing
            clip.stop(); // if condition is valid stop playing the audio
        }
    }

    // Method to loop the sound (useful for background music)
    public void loopSound(String soundFile) {
        try {
            java.net.URL soundURL = getClass().getResource(soundFile);
            if (soundURL == null) {// First checks if the resource exists
                System.err.println("Could not find audio file: " + soundFile);
                return;
            }
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            currentLoopingFile = soundFile;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    public void ButtonClicked() {
        if (isMuted) return;
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(
                    getClass().getResource("buttonClick.wav") //button clicking sound
            );
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Click sound could not be played.");
        } catch (NullPointerException e) {
            System.err.println("Sound file not found: /sounds/click.wav");
        }
    }
    public void toggleMute(GradientButton muteButton, Icon muteIcon, Icon unmuteIcon) {
        isMuted = !isMuted; // Flip the mute state

        if (isMuted) {
            stopSound();
            muteButton.setIcon(muteIcon); // Show muted icon
        } else {

            muteButton.setIcon(unmuteIcon);// Show unmuted icon
            if (currentLoopingFile != null) {
                loopSound(currentLoopingFile); // Resume music if any
            }
        }
    }
}
