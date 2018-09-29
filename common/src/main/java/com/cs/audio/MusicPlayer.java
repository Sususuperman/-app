package com.cs.audio;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by weifei on 16/6/16.
 */
public class MusicPlayer {

    static MediaPlayer mediaPlayer;
    MediaPlayer.OnCompletionListener completionListener;
    public MusicPlayer(Context context, MediaPlayer.OnCompletionListener completionListener)  {
        mediaPlayer = null;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        this.completionListener =completionListener;
        mediaPlayer.setOnCompletionListener(completionListener);
    }

    public void setFileDescriptor(String path) throws  RuntimeException{
        try {
            if(mediaPlayer.isPlaying()) {
                stop();
            }
            reset();
            mediaPlayer.setDataSource(path);
            play();
        } catch (Exception ex) {
            ex.printStackTrace();
            mediaPlayer.reset();
            throw new RuntimeException("Couldn't load music, uh oh!");
        }
    }

    public void play() {
        try {
            synchronized (this) {
                mediaPlayer.prepare();
                mediaPlayer.start();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void resume(){
        mediaPlayer.start();
    }
    public void reset() {
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }
    private void stop(){
        mediaPlayer.stop();
    }

    public int getDuration() {
        if(mediaPlayer!=null){
            return  mediaPlayer.getDuration();
        }
        return 0;
    }
    public int getCurrentPosition() {
        if(mediaPlayer!=null){
            return  mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    public void switchTracks() {
        mediaPlayer.seekTo(0);
        mediaPlayer.pause();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }

    public void setLooping(boolean isLooping) {
        mediaPlayer.setLooping(isLooping);
    }

    public void setVolume(float volumeLeft, float volumeRight) {
        mediaPlayer.setVolume(volumeLeft, volumeRight);
    }

    public void dispose() {
        if (mediaPlayer.isPlaying()) {
            stop();
        }
        mediaPlayer.release();
    }

    public void seekTo(int position) {
        mediaPlayer.seekTo(position);
    }


}