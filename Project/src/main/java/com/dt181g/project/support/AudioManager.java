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

public enum AudioManager {
    INSTANCE;
    private final Map<String, Clip> soundEffects = new HashMap<>();
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private Clip silence = this.loadSound(AppConfigProject.PATH_TO_SOUNDS + AppConfigProject.SOUND_EFFECT_SILENCE);

    public void keepAudioAlive() {
        this.scheduler.scheduleAtFixedRate(() -> {
            this.playSound(silence);
        }, 0, 5, TimeUnit.SECONDS);
    }

    private Clip loadSound(String soundFile) {
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

    public void playSound(String soundEffect) {
        if (!this.soundEffects.containsKey(soundEffect)) {
            this.soundEffects.put(soundEffect, loadSound(soundEffect));
        }
        this.playSound(soundEffects.get(soundEffect));
    }

    private void playSound(Clip clip) {
        clip.setFramePosition(0);
        clip.start();
    }

    public void shutdownAudio() {
        soundEffects.values().forEach(Clip::close);
        soundEffects.clear();
    }
}
