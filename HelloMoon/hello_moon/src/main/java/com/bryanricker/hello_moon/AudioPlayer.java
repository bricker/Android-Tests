package com.bryanricker.hello_moon;

import android.content.Context;

public class AudioPlayer
{

    private android.media.MediaPlayer mPlayer;


    public void play(Context c, int mediaId)
    {
        stop();

        mPlayer = android.media.MediaPlayer.create(c, mediaId);
        mPlayer.setOnCompletionListener(new android.media.MediaPlayer.OnCompletionListener()
        {
            public void onCompletion(android.media.MediaPlayer mp)
            {
                stop();
            }
        });

        mPlayer.start();
    }


    public void toggle()
    {
        if (mPlayer.isPlaying())
        {
            mPlayer.pause();
        } else {
            mPlayer.start();
        }
    }


    public boolean isPlaying()
    {
        return mPlayer.isPlaying();
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
