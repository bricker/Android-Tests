package com.bryanricker.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CheatActivity extends Activity
{
    public static final String EXTRA_ANSWER_IS_TRUE =
        "com.bryanricker.geoquiz.answer_is_true";

    public static final String EXTRA_ANSWER_SHOWN =
        "com.bryanricker.geoquiz.answer_shown";

    public static final String KEY_ANSWER_SHOWN = "answerShown";

    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswer;
    private boolean mIsAnswerShown;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue =
            getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView     = (TextView)findViewById(R.id.answerTextView);
        mShowAnswer         = (Button)findViewById(R.id.showAnswerButton);

        if (savedInstanceState != null)
        {
            mIsAnswerShown =
                savedInstanceState.getBoolean(KEY_ANSWER_SHOWN, false);

            showAnswer();
        } else {
            mIsAnswerShown = false;
        }

        setAnswerShowResult();

        mShowAnswer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showAnswer();
                mIsAnswerShown = true;
                setAnswerShowResult();
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_ANSWER_SHOWN, mIsAnswerShown);
    }


    private void setAnswerShowResult()
    {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, mIsAnswerShown);
        setResult(RESULT_OK, data);
    }


    private void showAnswer()
    {
        if (mAnswerIsTrue)
        {
            mAnswerTextView.setText(R.string.true_button);
        } else {
            mAnswerTextView.setText(R.string.false_button);
        }
    }
}
