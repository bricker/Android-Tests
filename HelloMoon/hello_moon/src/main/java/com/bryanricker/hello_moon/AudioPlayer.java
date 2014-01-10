package com.bryanricker.hello_moon;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioPlayer
{

    private MediaPlayer mPlayer;


    public void play(Context c, int mediaId)
    {
        stop();

        mPlayer = MediaPlayer.create(c, mediaId);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            public void onCompletion(MediaPlayer mp)
            {
                stop();
            }
        });

        mPlayer.start();
    }


    public void pause()
    {
        if (mPlayer != null) mPlayer.pause();
    }


    public void start()
    {
        if (mPlayer != null) mPlayer.start();
    }


    public void stop()
    {
        if (mPlayer != null)
        {
            mPlayer.release();
            mPlayer = null;
        }
    }

}
