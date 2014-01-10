package com.bryanricker.hello_moon;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioPlayer
{

    private MediaPlayer mPlayer;


    public void play(Context c)
    {
        stop();

        mPlayer = MediaPlayer.create(c, R.raw.one_small_step);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            public void onCompletion(MediaPlayer mp)
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
