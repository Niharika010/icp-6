package com.stacktips.speechtotext;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView mVoiceInputTv;
    private ImageButton mSpeakBtn;
    SpeechRecognizer speechRecognizer;
    Speaker speaker;
    RecognitionListener recognitionListener;
    Bundle args;
    @Override
    protected void onStart() {
        speaker.allow(true);
        speaker.speak("Hello");
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        args = new Bundle();
        speaker = new Speaker(getApplicationContext());
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
        recognitionListener = new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> result = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                int a = result.size();
                if (a>0)
                {
                    if (result.get(0).equalsIgnoreCase("Hello"))
                    {
                        speaker.allow(true);
                        speaker.speak("What is your Name");
                        speechRecognizer.stopListening();
                    }
                    else if (result.get(0).toLowerCase().startsWith("my name"))
                    {
                        Toast.makeText(getApplicationContext(),result.get(0),Toast.LENGTH_LONG).show();
                        String name = result.get(0).toLowerCase().substring(9);
                        speaker.allow(true);
                        speaker.speak("Hi"+name+ "I am your personal assistant");
                        speechRecognizer.stopListening();
                        mVoiceInputTv.setText("Hi"+name+ "I am your personal assistant");
                    }
                    else if (result.get(0).toLowerCase().endsWith("well"))
                    {
                        Toast.makeText(getApplicationContext(),result.get(0),Toast.LENGTH_LONG).show();
                        //String name = result.get(0).toLowerCase().substring(9);
                        speaker.allow(true);
                        speaker.speak("I can understand. Please tell your symptoms in short.");
                        speechRecognizer.stopListening();
                        //mVoiceInputTv.setText("Hi"+name+ "I am your personal assistant");
                    }
                    else if (result.get(0).toLowerCase().startsWith("what time"))
                    {
                        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm");
                        //dd/MM/yyyy
                        Date now = new Date();
                        String[] strDate = sdfDate.format(now).split(":");
                        if(strDate[1].contains("00"))strDate[1] = "o'clock";
                        Toast.makeText(getApplicationContext(),result.get(0),Toast.LENGTH_LONG).show();
                        //String name = result.get(0).toLowerCase().substring(9);
                        speaker.allow(true);
                        speaker.speak("The time is"+sdfDate.format(now));
                        speechRecognizer.stopListening();
                        //mVoiceInputTv.setText("Hi"+name+ "I am your personal assistant");
                    }
                    else if (result.get(0).toLowerCase().startsWith("what medicine"))
                    {
                        Toast.makeText(getApplicationContext(),result.get(0),Toast.LENGTH_LONG).show();
                        //String name = result.get(0).toLowerCase().substring(9);
                        speaker.allow(true);
                        speaker.speak("I think you have fever. Please take this medicine.");
                        speechRecognizer.stopListening();
                        //mVoiceInputTv.setText("Hi"+name+ "I am your personal assistant");
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Couldn't figure out...",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {
                ArrayList<String> result = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                Toast.makeText(getApplicationContext(),result.get(0),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        };
        speechRecognizer.setRecognitionListener(recognitionListener);
        mVoiceInputTv = (TextView) findViewById(R.id.voiceInput);
        mSpeakBtn = (ImageButton) findViewById(R.id.btnSpeak);
        mSpeakBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        speechRecognizer.startListening(intent);
    }
}
