package com.guralnya.myvocabulary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.guralnya.myvocabulary.ui.main.MainFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech {

    private android.speech.tts.TextToSpeech mTextToSpeech;
    private boolean mIsInit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }

        mTextToSpeech = new android.speech.tts.TextToSpeech(this, this);
    }

    @Override
    public void onInit(int status) {
        if (status == android.speech.tts.TextToSpeech.SUCCESS) {
            Locale locale = new Locale("eng");
            int result = mTextToSpeech.setLanguage(locale);
            if (result == android.speech.tts.TextToSpeech.LANG_MISSING_DATA || result == android.speech.tts.TextToSpeech.LANG_NOT_SUPPORTED) {
                mIsInit = false;
            } else {
                mIsInit = true;
            }
        } else {
            mIsInit = false;
        }
        Log.d(getClass().getName(), "Initialization TTS = " + mIsInit);
    }

    @Override
    public android.speech.tts.TextToSpeech getTextToSpeech() {
        if (mIsInit) {
            return mTextToSpeech;
        }
        return null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTextToSpeech.shutdown();
    }
}
