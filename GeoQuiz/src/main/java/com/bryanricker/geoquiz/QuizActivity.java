package com.bryanricker.geoquiz;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;
import android.widget.TextView;
import android.util.Log;

public class QuizActivity extends Activity
{
    // private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";

    private TextView mQuestionTextView;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;

    private TrueFalse[] mQuestionBank = new TrueFalse[]
    {
        new TrueFalse(R.string.question_oceans, true),
        new TrueFalse(R.string.question_mideast, false),
        new TrueFalse(R.string.question_africa, false),
        new TrueFalse(R.string.question_americas, true),
        new TrueFalse(R.string.question_asia, true)
    };

    private int mCurrentIndex = 0;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        // Get references to view objects
        mQuestionTextView   = (TextView)findViewById(R.id.question_text_view);
        mTrueButton         = (Button)findViewById(R.id.true_button);
        mFalseButton        = (Button)findViewById(R.id.false_button);
        mNextButton         = (Button)findViewById(R.id.next_button);
        mPrevButton         = (Button)findViewById(R.id.prev_button);


        // Setup click handlers for True/False buttons
        mTrueButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkAnswer(false);
            }
        });


        // Setup click handlers for navigation
        // We'll share behavior for the Question text and the Next button
        // so that they will always do the same thing.
        View[] views = { mQuestionTextView, mNextButton };
        for (View view : views)
        {
            view.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    shiftQuestion(1);
                }
            });
        }

        mPrevButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                shiftQuestion(-1);
            }
        });


        // If a saved instance was passed in, used its index so the question
        // remains the same between orientation changes.
        if (savedInstanceState != null)
        {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        // Finally, display the initial question.
        updateQuestion();
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }


    @Override
    public void onStart()
    {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }


    @Override
    public void onPause()
    {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }


    @Override
    public void onResume()
    {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }


    @Override
    public void onStop()
    {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // getMenuInflater().inflate(R.menu.activity_quiz, menu);
        return true;
    }



    // Shift the question `numberToShift` positions.
    private void shiftQuestion(int numberToShift)
    {
        int bankLen = mQuestionBank.length;
        mCurrentIndex = (mCurrentIndex + numberToShift + bankLen) % bankLen;
        updateQuestion();
    }

    // Update the text view to display the current question.
    private void updateQuestion()
    {
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }

    // Check the answer and handle user feedback.
    private void checkAnswer(Boolean userPressedTrue)
    {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
        int messageResId = 0;

        if (userPressedTrue == answerIsTrue)
        {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

}
