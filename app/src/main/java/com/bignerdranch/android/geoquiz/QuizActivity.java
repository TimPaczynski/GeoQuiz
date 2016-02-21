package com.bignerdranch.android.geoquiz;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;

import android.view.MenuItem;

import android.view.View;

import android.view.WindowManager;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;


public class QuizActivity extends AppCompatActivity {


    private static final String CHEATER = "cheater";
    private static final String Q1 = "question1";
    private static final String Q2 = "question2";
    private static final String Q3 = "question3";
    private static final String Q4 = "question4";
    private static final String Q5 = "question5";

    private boolean question_was_cheated_on1;
    private boolean question_was_cheated_on2;
    private boolean question_was_cheated_on3;
    private boolean question_was_cheated_on4;
    private boolean question_was_cheated_on5;


    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "key_index";
    private static final int REQUEST_CODE_CHEAT = 0;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private Button mNextButton;
    private Button mPreviousButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_africa, false, false),
            new Question(R.string.question_mideast, false, false),
            new Question(R.string.question_americas, true, false),
            new Question(R.string.question_oceans, true, false),
            new Question(R.string.question_asia, true, false),
    };
    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;
        if (mIsCheater || mQuestionBank[mCurrentIndex].isDidCheat()){
            messageResId =R.string.judgement_toast;
        }
        else{
            if(userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            }
            else{
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(bundle) called");
        setContentView(R.layout.activity_quiz);


        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();

            }
        });


        mTrueButton = (Button) findViewById(R.id.true_button);

        mTrueButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                checkAnswer(true);
            }

        });


        mFalseButton = (Button) findViewById(R.id.false_button);

        mFalseButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //code to start activity
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });


        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex +1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }
        });

        mPreviousButton = (Button) findViewById(R.id.previous_buttton);
        mPreviousButton.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + mQuestionBank.length -1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();

            }
        });

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }
        if (savedInstanceState != null) {
            mIsCheater = savedInstanceState.getBoolean(CHEATER, false);
            question_was_cheated_on1 = savedInstanceState.getBoolean(Q1, false);
            question_was_cheated_on2 = savedInstanceState.getBoolean(Q2, false);
            question_was_cheated_on3 = savedInstanceState.getBoolean(Q3, false);
            question_was_cheated_on4 = savedInstanceState.getBoolean(Q4, false);
            question_was_cheated_on5 = savedInstanceState.getBoolean(Q5, false);
            mQuestionBank[0].setDidCheat(question_was_cheated_on1);
            mQuestionBank[1].setDidCheat(question_was_cheated_on2);
            mQuestionBank[2].setDidCheat(question_was_cheated_on3);
            mQuestionBank[3].setDidCheat(question_was_cheated_on4);
            mQuestionBank[4].setDidCheat(question_was_cheated_on5);
        }
            updateQuestion();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        if (resultCode != Activity.RESULT_OK){
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT){
            if (data == null){
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
            if (mIsCheater){
                mQuestionBank[mCurrentIndex].setDidCheat(true);
            }

        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstancceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(CHEATER ,mIsCheater);
        savedInstanceState.putBoolean(Q1, mQuestionBank[0].isDidCheat());
        savedInstanceState.putBoolean(Q2, mQuestionBank[1].isDidCheat());
        savedInstanceState.putBoolean(Q3, mQuestionBank[2].isDidCheat());
        savedInstanceState.putBoolean(Q4, mQuestionBank[3].isDidCheat());
        savedInstanceState.putBoolean(Q5, mQuestionBank[4].isDidCheat());
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }



    // @Override

//    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu;this adds items to the action bar if it is present.

  //      getMenuInflater().inflate(R.menu.menu_quiz, menu);

    //    return true;

    }


  //  @Override

//    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will

        // automatically handle clicks on the Home/Up button, so long

        // as you specify a parent activity in AndroidManifest.xml.

    //    int id = item.getItemId();

      //  if (id == R.id.action_settings) {

        //    return true;

       // }

        // return super.onOptionsItemSelected(item);

    //}


// }



