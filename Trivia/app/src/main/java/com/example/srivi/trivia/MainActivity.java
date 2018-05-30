package com.example.srivi.trivia;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetDataAsync.IData{

    Button exitButton;
    Button startButton;
    TextView tvImage;
    ProgressBar progressBar;
    ImageView imageView;
    ArrayList<String> questions;
    ArrayList<Question> questionArrayList;
    static String TRIV_KEY="QUESTIONS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        questionArrayList = new ArrayList<>(  );
        exitButton = (Button) findViewById( R.id.btnExit );
        startButton = (Button) findViewById( R.id.btnStart );
        tvImage = (TextView) findViewById( R.id.tvImage );
        progressBar = (ProgressBar) findViewById( R.id.progressBar );
        exitButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        } );
        startButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,TriviaActivity.class);
                intent.putExtra(TRIV_KEY,questionArrayList);
                startActivity( intent );
            }
        } );
        /*
        if(isConnected())
            Toast.makeText( this, "Connected", Toast.LENGTH_SHORT );
        else
            Toast.makeText( this, "Not Connected", Toast.LENGTH_SHORT ); */
        new GetDataAsync(MainActivity.this).execute( "http://dev.theappsdr.com/apis/trivia_json/trivia_text.php" );
    }
    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
            return true;
        return false;
    }

    @Override
    public void handleData(ArrayList<String> data) {
        this.questions = data;
        imageView = (ImageView) findViewById( R.id.ivMain );
        imageView.setVisibility( View.VISIBLE );
        tvImage.setText( "Trivia Ready" );
        startButton.setEnabled( true );
        progressBar.setVisibility( View.INVISIBLE );
        for(int i=0;i<questions.size();i++) {
            String[] temp = questions.get( i ).split( ";" );
            int questionIndex = Integer.parseInt( temp[0] );
            String questionText = temp[1];
            String questionURL = temp[2];
            ArrayList<String> options = new ArrayList<>(  );
            for(int j=3;j<temp.length-1;j++)
                options.add( temp[j] );
            int answer = Integer.parseInt( temp[temp.length-1] );
            Question questionObject = new Question(questionIndex, questionText, questionURL, options, answer);
            questionArrayList.add( questionObject );
        }
    }
}
