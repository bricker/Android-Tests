package com.bryanricker.geoquiz;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;


public class QuizActivity extends Activity
{

    private Button mTrueButton;
    private Button mFalseButton;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Get reference to True button
        // Setup click handler
        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText
                (
                    QuizActivity.this,
                    R.string.incorrect_toast,
                    Toast.LENGTH_SHORT
                ).show();
            }
        });


        // Get reference to False button
        // Setup click handler
        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText
                (
                    QuizActivity.this,
                    R.string.correct_toast,
                    Toast.LENGTH_SHORT
                ).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // getMenuInflater().inflate(R.menu.activity_quiz, menu);
        return true;
    }

}
