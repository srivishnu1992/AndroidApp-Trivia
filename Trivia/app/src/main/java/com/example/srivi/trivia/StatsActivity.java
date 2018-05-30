package com.example.srivi.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.srivi.trivia.TriviaActivity.LIST_KEY;
import static com.example.srivi.trivia.TriviaActivity.STAT_KEY;

public class StatsActivity extends AppCompatActivity {

    TextView tvScore;
    int score;
    int percent;
    Button quitButton;
    Button tryButton;
    SeekBar seekBar;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_stats );
        tvScore = (TextView) findViewById( R.id.textView6 );
        score = getIntent().getExtras().getInt( STAT_KEY );
        percent = (score*100)/15;
        seekBar = (SeekBar) findViewById( R.id.seekBar );
        seekBar.setProgress( percent );
        tvScore.setText( percent+"%" );
        list = getIntent().getExtras().getStringArrayList( LIST_KEY );
        quitButton = (Button) findViewById( R.id.button3 );
        tryButton = (Button) findViewById( R.id.button4 );
        quitButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( StatsActivity.this, MainActivity.class );
                startActivity( intent );
            }
        } );

        tryButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( StatsActivity.this, TriviaActivity.class );
                intent.putExtra( MainActivity.TRIV_KEY, list );
                startActivity( intent );
            }
        } );

    }
}
