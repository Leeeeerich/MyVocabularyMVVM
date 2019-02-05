package com.guralnya.myvocabulary;

import android.content.Context;

public interface TextToSpeech extends android.speech.tts.TextToSpeech.OnInitListener {
    android.speech.tts.TextToSpeech getTextToSpeech();
}
