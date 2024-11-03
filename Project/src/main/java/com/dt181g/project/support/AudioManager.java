package com.dt181g.project.support;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Singleton class for managing audio playback within the application.
 * This class handles loading, playing, and managing sound effects, ensuring
 * that audio resources are efficiently used and maintained.
 *
 * <p>
 * The audio manager uses a singleton pattern to provide a single instance
 * for sound management throughout the application lifecycle. It supports
 * loading sound effects on demand and keeps audio active to prevent
 * playback issues.
 * </p>
 *
 * @author Joel Lansgren
 */
public enum AudioManager {
    INSTANCE;
    private final Map<String, Clip> soundEffects = new HashMap<>();
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private Clip silence = this.loadSound(AppConfigProject.PATH_TO_SOUNDS + AppConfigProject.SOUND_EFFECT_SILENCE);

    /**
     * Keeps the audio system alive by periodically playing a silent sound.
     * This is useful for preventing issues with sound playback in some environments.
     */
    public void keepAudioAlive() {
        this.scheduler.scheduleAtFixedRate(() -> {
            this.playSound(silence);
        }, 0, 5, TimeUnit.SECONDS);
    }

    /**
     * Loads a sound file and returns a Clip object representing the audio.
     *
     * @param soundFile the path to the sound file to be loaded
     * @return a Clip object containing the loaded sound, or null if an error occurs
     */
    private Clip loadSound(String soundFile) {
        // The BufferedInputStream is essential to ensure that sound files can be
        // properly accessed and loaded from within a JAR file
        try (InputStream is = new BufferedInputStream(getClass().getResourceAsStream(soundFile))) {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(is);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            return clip;
        } catch (Exception e) {
            DebugLogger.INSTANCE.logException(e);
        }
        return null;
    }

    /**
     * Plays a specified sound effect. If the sound has not been loaded yet,
     * it will be loaded before playback.
     *
     * @param soundEffect the key identifying the sound effect to be played
     */
    public void playSound(String soundEffect) {
        if (!this.soundEffects.containsKey(soundEffect)) {
            this.soundEffects.put(soundEffect, loadSound(soundEffect));
        }
        this.playSound(soundEffects.get(soundEffect));
    }

    /**
     * Plays a given Clip object from the beginning.
     *
     * @param clip the Clip object representing the sound to be played
     */
    private void playSound(Clip clip) {
        clip.setFramePosition(0);
        clip.start();
    }

    /**
     * Cleans up audio resources by closing all loaded sound clips and clearing the sound effects map.
     * <p>
     * This method should be invoked every time a game is shut down to ensure that only relevant
     * audio resources are stored in memory. This helps prevent memory leaks and ensures that audio
     * clips are not lingering in the system after they are no longer needed.
     * </p>
     */
    public void shutdownAudio() {
        soundEffects.values().forEach(Clip::close);
        soundEffects.clear();
    }
}
