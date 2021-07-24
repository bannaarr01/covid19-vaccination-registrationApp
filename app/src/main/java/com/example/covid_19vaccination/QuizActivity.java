package com.example.covid_19vaccination;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {
    Button mYesButton, mNoButton;
    int mIndex, mQuestion, mTotal;
    ProgressBar mProgressBar;
    //final int PROGRESS_BAR_INCREMENT = 20; //100/5
    private TextView mQuestionTextView, mTotalTextView;       //nameTV;
    private String name;
    private YesNo[] mQuestionPool = new YesNo[]{
            new YesNo(R.string.question_1, true),
            new YesNo(R.string.question_2, true),
            new YesNo(R.string.question_3, true),
            new YesNo(R.string.question_4, true),
            new YesNo(R.string.question_5, true),
    };

    //Ensure Progress Bar is full
    final int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.00 / mQuestionPool.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.lightStatusBarColor, this.getTheme()));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.appColor));

        }

        setContentView(R.layout.activity_quiz);

        mYesButton = findViewById(R.id.yes_button);
        mNoButton = findViewById(R.id.no_button);
        mQuestionTextView = findViewById(R.id.question_text_view);
        mTotalTextView = findViewById(R.id.total);
        mProgressBar = findViewById(R.id.progress_bar);

        //Fetches d First and mIndex at d moment is 0
        mQuestion = mQuestionPool[mIndex].getQuestionID();
        mQuestionTextView.setText(mQuestion);

        mYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                updateQuestion();
            }
        });
        mNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        Bundle b = getIntent().getExtras();
        name = b.getString("Name");

        this.setTitle(name + " Consent");
    }

    private void updateQuestion() {
        //new value = 0+1
        mIndex = (mIndex + 1) % mQuestionPool.length;
        if (mIndex == 0) {
            //current state of d app(context)
            // AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("GREAT !\n");
            alert.setCancelable(false);
            alert.setMessage("You can now Proceed with " + name.toUpperCase() + " Vaccine Registration");
            alert.setPositiveButton("Proceed >>", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    //Intent intent = new Intent(getApplicationContext(), ConfirmationActivity.class);
                    intent.putExtra("Name", name);
                    startActivity(intent);
                    finish();//So that you would not return to quiz directly

                }
            });
            alert.show();
        }
        mQuestion = mQuestionPool[mIndex].getQuestionID();
        mQuestionTextView.setText(mQuestion);
        mProgressBar.incrementSecondaryProgressBy(PROGRESS_BAR_INCREMENT);
        mTotalTextView.setText(mTotal + "/" + mQuestionPool.length);
    }

    private void checkAnswer(boolean userSelection) {
        //Updating the question number
        mTotal = mTotal + 1;
        boolean correctAnswer = mQuestionPool[mIndex].isAnswer();
        if (userSelection == correctAnswer) {
            Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
            //mTotal = mTotal + 1;
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("SORRY !\n");
            alert.setCancelable(false);
            alert.setMessage("You cannot proceed to register for " + name.toUpperCase() + " Vaccine without choosing YES to this question");
            alert.setPositiveButton("Exit Consent", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish(); //Remove d activity from list
                    //And Return User back to DashBoard
                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                    startActivity(intent);
                }
            });
            alert.show();
        }
    }//end of check answer
}//end of Quiz Activity