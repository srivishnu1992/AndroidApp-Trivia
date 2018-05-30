package com.example.srivi.trivia;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by srivi on 18-02-2018.
 *
 */

public class Question implements Serializable{
    int questionIndex;
    String questionText;
    String questionURL;
    ArrayList<String> options;
    int answer;
    Question(int questionIndex, String questionText, String questionURL, ArrayList<String> options, int answer) {
        this.questionIndex = questionIndex;
        this.questionText = questionText;
        this.questionURL = questionURL;
        this.options = options;
        this.answer = answer;
    }
}
