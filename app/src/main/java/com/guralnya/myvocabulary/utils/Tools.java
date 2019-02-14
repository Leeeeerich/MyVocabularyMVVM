package com.guralnya.myvocabulary.utils;

import android.content.Context;
import android.media.AudioManager;
import android.widget.Toast;

import static android.content.Context.AUDIO_SERVICE;

public class Tools {

    public static int getVolumeLevel(Context context){
        AudioManager am = (AudioManager) context.getSystemService(AUDIO_SERVICE);
        if(am == null) return -1;
        return am.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    public static void toast(Context context, String text){
        Toast toast = Toast.makeText(context,text, Toast.LENGTH_SHORT);
        toast.show();
    }

}
