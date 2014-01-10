package com.bryanricker.hello_moon;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

public class VideoFragment extends Fragment
{

    private Button mPlayButton;
    private Button mPauseButton;
    private Button mStopButton;
    private VideoView mVideo;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_video, parent, false);

        mPlayButton = (Button)v.findViewById(R.id.hellomoon_video_play_Button);
        mStopButton = (Button)v.findViewById(R.id.hellomoon_video_stop_Button);
        mPauseButton = (Button)v.findViewById(R.id.hellomoon_video_pause_Button);
        mVideo = (VideoView)v.findViewById(R.id.hellomoon_video_VideoView);

        mVideo.setVideoURI(Uri.parse(
            "android.resource://com.bryanricker.hello_moon/raw/apollo_17_stroll"));

        mVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            public void onCompletion(MediaPlayer mp)
            {
                mVideo.stopPlayback();
            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                mVideo.start();
                togglePauseButtonText();
                mPauseButton.setVisibility(View.VISIBLE);
            }
        });

        mPauseButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if (mVideo.isPlaying())
                {
                    mVideo.pause();
                } else {
                    mVideo.resume();
                }

                togglePauseButtonText();
            }
        });

        mStopButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                mVideo.stopPlayback();
                mPauseButton.setVisibility(View.GONE);
            }
        });

        return v;
    }


    private void togglePauseButtonText()
    {
        if (mVideo.isPlaying())
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
        mVideo.stopPlayback();
    }

}
