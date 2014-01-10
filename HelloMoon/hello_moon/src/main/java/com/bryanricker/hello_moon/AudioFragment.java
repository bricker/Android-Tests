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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_audio, parent, false);

        mPlayButton = (Button)v.findViewById(R.id.hellomoon_audio_play_Button);
        mStopButton = (Button)v.findViewById(R.id.hellomoon_audio_stop_Button);
        mPauseButton = (Button)v.findViewById(R.id.hellomoon_audio_pause_Button);

        mPlayButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                mPlayer.play(AudioFragment.this.getActivity(), R.raw.one_small_step);
                togglePauseButtonText();
                mPauseButton.setVisibility(View.VISIBLE);
            }
        });

        mPauseButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                mPlayer.toggle();
                togglePauseButtonText();
            }
        });

        mStopButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                mPlayer.stop();
                mPauseButton.setVisibility(View.GONE);
            }
        });

        return v;
    }


    private void togglePauseButtonText()
    {
        if (mPlayer.isPlaying())
        {
            mPauseButton.setText(R.string.hellomoon_pause);
        } else {
            mPauseButton.setText(R.string.hellomoon_resume);
        }
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mPlayer.stop();
    }

}
