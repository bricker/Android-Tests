package com.bryanricker.hello_moon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AudioFragment extends Fragment
{

    private AudioPlayer mPlayer = new AudioPlayer();
    private Button mPlayButton;
    private Button mPauseButton;
    private Button mStopButton;
    private boolean mIsPaused = false;
    private boolean mIsPlaying = false;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_audio, parent, false);

        mPlayButton = (Button)v.findViewById(R.id.hellomoon_audio_play_Button);
        mStopButton = (Button)v.findViewById(R.id.hellomoon_audio_stop_Button);
        mPauseButton = (Button)v.findViewById(R.id.hellomoon_audio_pause_Button);

        togglePauseButtonText();
        setPauseButtonIsVisible(mIsPlaying || mIsPaused);

        mPlayButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                mPlayer.play(AudioFragment.this.getActivity(), R.raw.one_small_step);
                mIsPlaying = true;
                mIsPaused = false;

                togglePauseButtonText();
                setPauseButtonIsVisible(true);
            }
        });

        mPauseButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if (mIsPlaying)
                {
                    mPlayer.pause();
                    mIsPlaying = false;
                    mIsPaused = true;
                } else {
                    mPlayer.start();
                    mIsPlaying = true;
                    mIsPaused = false;
                }

                togglePauseButtonText();
            }
        });

        mStopButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                mPlayer.stop();
                mIsPlaying = false;
                mIsPaused = false;

                setPauseButtonIsVisible(false);
            }
        });

        return v;
    }


    private void togglePauseButtonText()
    {
        if (mIsPlaying)
        {
            mPauseButton.setText(R.string.hellomoon_pause);
        } else {
            mPauseButton.setText(R.string.hellomoon_resume);
        }
    }


    private void setPauseButtonIsVisible(boolean isVisible)
    {
        if (isVisible)
        {
            mPauseButton.setVisibility(View.VISIBLE);
        } else {
            mPauseButton.setVisibility(View.GONE);
        }
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mPlayer.stop();
    }

}
