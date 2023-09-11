// org.apache.commons.io

package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

/*
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
 */

//import org.apache.commons.io.IOUtils;


public class QuizActivity extends AppCompatActivity {
    /* class variables */
    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_CHEATER = "cheat_flag";
    private static final String KEY_CHEATS = "number_of_cheats";
    private static final int REQUEST_CODE_CHEAT = 0;
    private static final int MAX_NUMBER_OF_CHEATS = 3;

    /* object members */
    private boolean mIsCheater = false;
    private  int mNumberOfCheats = 0;
    private TextView mCheatInfoTextView;
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private int mCurrentIndex = 0;
    private int mAmountCorrect = 0;
    private int mAmountIncorrect = 0;
    //private Question[] mQuestionBank = new Question[10];

    private String text = "Question?";
    private Question[] mQuestionBank = new Question[]{
            new Question( text, true),
            new Question( text, true)
    };


    /* override of super class */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main); // this is just the xml file w/o the extension

        //String teststring = getResources().getString(R.string.question_australia);
        //Question q = new Question( teststring, true);
        //Question q = new Question( getResources().getString(R.string.question_australia), true);

        //testInternet();

        //fillQuestionBank();

        /* restore the index if the app is not destroyed yet */
        if(savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(KEY_CHEATER, false);
            mNumberOfCheats = savedInstanceState.getInt(KEY_CHEATS, 0);
        }

        /* Question Text View */
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showNextQuestion();
            }
        });
        updateQuestion();

        /* NEXT button */
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showNextQuestion();
                mIsCheater = mQuestionBank[mCurrentIndex].isCheatedOn();
            }
        });

        /* PREV button */
        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showPrevQuestion();
                mIsCheater = mQuestionBank[mCurrentIndex].isCheatedOn();
            }
        });

        /* TRUE Button */
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        /* FALSE Button */
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        /* CHEAT Button */
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                boolean isAnswerTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, isAnswerTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        /* Cheat Info Text View */
        mCheatInfoTextView = (TextView) findViewById(R.id.cheat_info_text_view);
        setCheatInfoText();

    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState){
        super.onSaveInstanceState(saveInstanceState);
        Log.d(TAG, "onSaveInstanceState");
        saveInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        saveInstanceState.putBoolean(KEY_CHEATER, mIsCheater);
        saveInstanceState.putInt(KEY_CHEATS, mNumberOfCheats);
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode == REQUEST_CODE_CHEAT){
            if(data == null){
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
            mQuestionBank[mCurrentIndex].setCheatedOn(mIsCheater);

            mNumberOfCheats++;
            setCheatInfoText();
        }
    }

    /*
    private void testInternet(){
        try {
            URL url = new URL("https://localhost:9443/genesis/Receipt/Receipt.html");
            try {
                url.openConnection();
                InputStream reader = url.openStream();
            }catch(IOException e){
                ;
            }
        }catch(MalformedURLException e){
            ;
        }
    }

    private static JSONObject getJson(URL url) {
        JSONObject jObj = null;
        try{
            String json = IOUtils.toString(url.openStream(), StandardCharsets.UTF_8);
            try{
                jObj = new JSONObject(json);
            }catch(JSONException e){
                // do nothing
            }
        }catch (IOException e){
            // Do nothing
        }
        return jObj;
    }
    private void fillQuestionBank() {
        try {
            URL url = new URL("https://opentdb.com/api.php?amount=10&type=boolean");
            //InputStream in = url.openStream();
            for(int i=0;i<10;i++){
                try {
                    JSONObject jObj = getJson(url);
                    if(jObj != null){
                        String question = jObj.getString("question");
                        String correct_answer = jObj.getString("correct_answer");
                        mQuestionBank[i] = new Question(question, correct_answer=="True" ? true : false);
                    }
                }catch(JSONException e){
                    // do nothing
                }
            }
        }catch(MalformedURLException e){
            // Do nothing
        }
    }
    */
    private void setCheatInfoText(){
        int numberOfCheatsRemaining = MAX_NUMBER_OF_CHEATS-mNumberOfCheats;
        if(numberOfCheatsRemaining>0){
            if(mQuestionBank[mCurrentIndex].isCheatedOn()){
                mCheatInfoTextView.setText("Already cheated on this Question.");
                mCheatButton.setEnabled(false);
            }else{
                mCheatInfoTextView.setText(numberOfCheatsRemaining + " more Cheats allowed.");
                mCheatButton.setEnabled(true);
            }
        }else if(numberOfCheatsRemaining==0) {
            mCheatInfoTextView.setText("All Cheats used.");
            mCheatButton.setEnabled(false);
        }
        else { // numberOfCheatsRemaining<0
            mCheatInfoTextView.setText("DEBUG PLEASE :)");
            mCheatButton.setEnabled(false);
        }
    }

    private void updateQuestion(){
        //Log.d(TAG, "Update question text", new Exception());
        String question = mQuestionBank[mCurrentIndex].getText();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        // Make check and update data
        int messageResId = 0;

        if(mIsCheater){
            messageResId = R.string.judgement_toast;
        }else{
            if(userPressedTrue == mQuestionBank[mCurrentIndex].isAnswerTrue()){
                messageResId = R.string.correct_toast;
                mAmountCorrect++;
            }else{
                messageResId = R.string.incorrect_toast;
                mAmountIncorrect++;
            }
        }
        mQuestionBank[mCurrentIndex].setAlreadyAnswered(true);
        // Enable/disable buttons
        enableDisableTrueFalseButtons();
        // Show correct/incorrect toast
        showCorrectIncorrectOrCheatToast(messageResId);
        // Show toast if user answered all
        showFinaleToast();
    }

    private void enableDisableTrueFalseButtons(){
        if(mQuestionBank[mCurrentIndex].isAlreadyAnswered()){
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        } else{
            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
        }
    }

    private void showCorrectIncorrectOrCheatToast(int messageResId){
        Toast toast = Toast.makeText(QuizActivity.this, messageResId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
    }

    private void showFinaleToast(){
        if(mAmountCorrect+mAmountIncorrect>=mQuestionBank.length){
            double result = mAmountCorrect/(double) mQuestionBank.length*100.;
            Toast toast = Toast.makeText(QuizActivity.this, "!!! DONE !!!\n"+String.format ("%.2f", result)+"% are correct!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private void showNextQuestion(){
        mCurrentIndex = (mCurrentIndex+1) % mQuestionBank.length; // Modulo, trick to start at new pos 0 again!
        checkIndex();
        enableDisableTrueFalseButtons();
        updateQuestion();
        setCheatInfoText();
    }

    private void showPrevQuestion(){
        // mCurrentIndex = (mCurrentIndex == 0) && (mQuestionBank.length-1) || (mCurrentIndex - 1);
        mCurrentIndex = (mCurrentIndex == 0) ? (mQuestionBank.length-1) : (mCurrentIndex - 1);
        checkIndex();
        enableDisableTrueFalseButtons();
        updateQuestion();
        setCheatInfoText();
    }

    private void checkIndex(){
        Question question;
        try{
            question = mQuestionBank[mCurrentIndex];
        }
        catch(ArrayIndexOutOfBoundsException ex){
            Log.e(TAG, "Index out of bounds!", ex);
        }
    }
}