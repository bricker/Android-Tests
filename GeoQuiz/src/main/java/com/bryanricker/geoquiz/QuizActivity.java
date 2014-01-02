package com.bryanricker.geoquiz;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;
import android.widget.TextView;
import android.util.Log;
import android.content.Intent;
import android.app.ActionBar;
import android.annotation.TargetApi;
import android.os.Build;


public class QuizActivity extends Activity
{
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_CHEATED_QUESTIONS = "cheatedQuestions";

    private boolean[] mCheatedQuestions = new boolean[]
    {
        false,
        false,
        false,
        false,
        false
    };

    private TextView mQuestionTextView;
    private TextView mBuildVersionTextView;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private Button mCheatButton;
    private boolean mIsCheater = false;

    private TrueFalse[] mQuestionBank = new TrueFalse[]
    {
        new TrueFalse(R.string.question_oceans, true),
        new TrueFalse(R.string.question_mideast, false),
        new TrueFalse(R.string.question_africa, false),
        new TrueFalse(R.string.question_americas, true),
        new TrueFalse(R.string.question_asia, true)
    };

    private int mCurrentIndex = 0;


    @TargetApi(11)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            ActionBar actionBar = getActionBar();
            actionBar.setSubtitle("Bodies of Water");
        }

        // Get references to view objects
        mQuestionTextView   = (TextView)findViewById(R.id.question_text_view);
        mTrueButton         = (Button)findViewById(R.id.true_button);
        mFalseButton        = (Button)findViewById(R.id.false_button);
        mNextButton         = (Button)findViewById(R.id.next_button);
        mPrevButton         = (Button)findViewById(R.id.prev_button);
        mCheatButton        = (Button)findViewById(R.id.cheat_button);

        mBuildVersionTextView =
            (TextView)findViewById(R.id.build_version_text_view);

        mBuildVersionTextView.setText(
            getString(R.string.api_level) + " " + Build.VERSION.SDK_INT);


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


        mCheatButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(QuizActivity.this, CheatActivity.class);

                boolean answerIsTrue =
                    mQuestionBank[mCurrentIndex].isTrueQuestion();

                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                startActivityForResult(i, 0);
            }
        });

        // If a saved instance was passed in, used its index so the question
        // remains the same between orientation changes.
        if (savedInstanceState != null)
        {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mCheatedQuestions = savedInstanceState.getBooleanArray(
                KEY_CHEATED_QUESTIONS);
        }


        // Finally, display the initial question.
        updateQuestion();
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBooleanArray(
            KEY_CHEATED_QUESTIONS, mCheatedQuestions);
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
    protected void onActivityResult(
    int requestCode, int resultCode, Intent data)
    {
        if (data == null)
        {
            return;
        }

        setIsCheater(
            data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false));
    }


    private boolean isCheater()
    {
        return mCheatedQuestions[mCurrentIndex];
    }


    private void setIsCheater(boolean didCheat)
    {
        // If the user hasn't cheated on this question before,
        // and they did cheat this time, then set the cheat status
        // for this question to true.
        // If they already cheated, or they didn't cheat this time,
        // then there's no need to do anything.
        if (!mCheatedQuestions[mCurrentIndex] && didCheat)
        {
            mCheatedQuestions[mCurrentIndex] = true;
        }
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

        if (isCheater())
        {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue)
            {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

}
