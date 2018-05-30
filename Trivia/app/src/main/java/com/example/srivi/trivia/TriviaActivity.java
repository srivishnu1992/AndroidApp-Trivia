package com.example.srivi.trivia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity {

    TextView tvQuestionNo;
    TextView tvTimer;
    TextView tvQuestionText;
    ArrayList<Question> questionArrayList;
    Button btnQuit;
    String qNumber;
    String questionURL;
    ArrayList<String> options;
    int answer;
    ImageView imageView;
    GetImageAsync.IImage iImage;
    Bitmap image;
    ProgressBar progressBar;
    Question firstQuestion;
    int index;
    Button btnNext;
    RadioGroup radioGroup;
    int score;
    static String STAT_KEY = "STATISTICS";
    static String LIST_KEY = "LIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_trivia2 );
        tvQuestionNo = (TextView) findViewById( R.id.tvQuestionNo );
        tvTimer = (TextView) findViewById( R.id.tvTimer );
        tvQuestionText = (TextView) findViewById( R.id.tvQuestion );
        questionArrayList = (ArrayList<Question>) getIntent().getExtras().getSerializable( MainActivity.TRIV_KEY );
        imageView = (ImageView) findViewById( R.id.imageView2 );
        progressBar = (ProgressBar) findViewById( R.id.progressBar2 );

        index = 0;
        //Log.d( "demo", String.valueOf( questionArrayList.size() ));
        questionExecute();

        CountDownTimer countDownTimer = new CountDownTimer( 120000, 1000 ) {

            @Override
            public void onTick(long l) {
                tvTimer.setText( "Time Left: "+(l/1000)+" Seconds" );
            }

            @Override
            public void onFinish() {
                nextActivity();
            }
        };
        countDownTimer.start();

        btnQuit = (Button) findViewById( R.id.btnQuit );
        btnQuit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );

        btnNext = (Button) findViewById( R.id.btnNext );

        btnNext.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answer == radioGroup.getCheckedRadioButtonId())
                    score++;
                index++;
                radioGroup.removeAllViews();
                questionExecute();

            }
        } );

    }
    public void handleData(Bitmap image) {
        this.image = image;
        imageView.setImageBitmap( image );
        progressBar.setVisibility( View.INVISIBLE );
    }
    public void questionExecute() {
        if(index<questionArrayList.size()) {
            radioGroup = (RadioGroup) findViewById( R.id.radioGroup );
            radioGroup.clearCheck();
            progressBar.setVisibility( View.VISIBLE );
            firstQuestion = questionArrayList.get( index );
            tvQuestionText.setText( firstQuestion.questionText );
            qNumber = "Q" + (index + 1);
            tvQuestionNo.setText( qNumber );
            answer = firstQuestion.answer;
            questionURL = firstQuestion.questionURL;
            options = firstQuestion.options;
            new GetImageAsync( TriviaActivity.this ).execute( questionURL );
            radioGroup.setOrientation( LinearLayout.VERTICAL );
            for(int i=0;i<options.size();i++) {
                RadioButton radioButton = new RadioButton( this );
                radioButton.setId( i );
                radioButton.setText( options.get( i ) );
                radioGroup.addView( radioButton );
                radioGroup.setVisibility( View.VISIBLE );
            }
        }
        else {
            nextActivity();
        }
    }
    public void nextActivity() {
        Intent intent = new Intent( TriviaActivity.this, StatsActivity.class );
        intent.putExtra( STAT_KEY, score );
        intent.putExtra( LIST_KEY, questionArrayList );
        startActivity( intent );
    }
}
